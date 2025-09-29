package com.university.scooterrental.service.scooter;

import com.university.scooterrental.dto.scooter.ScooterDto;
import com.university.scooterrental.mapper.ScooterMapper;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.repository.ScooterRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScooterService {
  private final ScooterRepository scooterRepository;
  private final ScooterMapper scooterMapper;

  @Transactional(readOnly = true)
  public List<ScooterDto> findAll() {
    return scooterRepository.findAll().stream()
            .map(scooterMapper::toDto)
            .toList();
  }

  @Transactional(readOnly = true)
  public Optional<ScooterDto> findById(Long id) {
    return scooterRepository.findById(id).map(scooterMapper::toDto);
  }

  @Transactional
  public ScooterDto save(Scooter scooter) {
    return scooterMapper.toDto(scooterRepository.save(scooter));
  }

  @Transactional
  public ScooterDto update(Long id, Scooter scooterDetails) {
    Scooter scooter = scooterRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Scooter not found with id " + id));

    scooter.setModel(scooterDetails.getModel());
    scooter.setTariff(scooterDetails.getTariff());
    scooter.setQuantity(scooterDetails.getQuantity());
    scooter.setBatteryLevel(scooterDetails.getBatteryLevel());
    scooter.setStatus(scooterDetails.getStatus());

    return scooterMapper.toDto(scooterRepository.save(scooter));
  }

  @Transactional
  public void delete(Long id) {
    if (!scooterRepository.existsById(id)) {
      throw new RuntimeException("Scooter not found with id " + id);
    }
    scooterRepository.deleteById(id);
  }
}
