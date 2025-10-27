package com.university.scooterrental.service.scooter;

import com.university.scooterrental.dto.scooter.CreateScooterRequestDto;
import com.university.scooterrental.dto.scooter.ScooterResponseDto;
import com.university.scooterrental.exception.ScooterAlreadyExistsException;
import com.university.scooterrental.exception.ScooterNotFoundException;
import com.university.scooterrental.mapper.ScooterMapper;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.repository.ScooterRepository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScooterService {
    private final ScooterRepository scooterRepository;
    private final ScooterMapper scooterMapper;

    public ScooterService(ScooterRepository scooterRepository, ScooterMapper scooterMapper) {
        this.scooterRepository = scooterRepository;
        this.scooterMapper = scooterMapper;
    }

    @Transactional(readOnly = true)
    public List<ScooterResponseDto> findAll() {
        return scooterRepository.findAll().stream()
                .map(scooterMapper::toScooterResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ScooterResponseDto findById(Long id) {
        Scooter scooter = scooterRepository.findById(id)
                .orElseThrow(() -> new ScooterNotFoundException("Could not find scooter with id: " + id));
        return scooterMapper.toScooterResponseDto(scooter);
    }

    @Transactional
    public ScooterResponseDto save(CreateScooterRequestDto requestDto) {
        if (scooterRepository.existsByModel(requestDto.getModel())) {
            throw new ScooterAlreadyExistsException("Scooter with model "
                    + requestDto.getModel() + " already exists");
        }
        Scooter scooter = scooterMapper.toScooter(requestDto);
        scooterRepository.save(scooter);
        return scooterMapper.toScooterResponseDto(scooter);
    }

    @Transactional
    public void update(Long id, CreateScooterRequestDto requestDto) {
        Scooter existingScooter = scooterRepository.findById(id)
                .orElseThrow(() -> new ScooterNotFoundException("Could not find scooter with id: " + id));
        scooterMapper.update(existingScooter, requestDto);
    }

    @Transactional
    public void delete(Long id) {
        if (!scooterRepository.existsById(id)) {
            throw new ScooterNotFoundException("Could not find scooter with id: " + id);
        }
        scooterRepository.deleteById(id);
    }
}
