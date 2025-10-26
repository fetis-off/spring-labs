package com.university.scooterrental.service.rental;

import com.university.scooterrental.dto.rental.RentalRequestDto;
import com.university.scooterrental.dto.rental.RentalResponseDto;
import com.university.scooterrental.exception.RentalException;
import com.university.scooterrental.exception.RentalNotFoundException;
import com.university.scooterrental.exception.ScooterNotFoundException;
import com.university.scooterrental.mapper.RentalMapper;
import com.university.scooterrental.model.rental.Rental;
import com.university.scooterrental.model.rental.RentalStatus;
import com.university.scooterrental.model.scooter.Scooter;
import com.university.scooterrental.model.scooter.ScooterStatus;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.RentalRepository;
import com.university.scooterrental.repository.ScooterRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.university.scooterrental.service.payment.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class RentalService {
    private final RentalRepository rentalRepository;
    private final ScooterRepository scooterRepository;
    private final RentalMapper rentalMapper;
    private final PaymentService paymentService;

    public RentalService(RentalRepository rentalRepository,
                         ScooterRepository scooterRepository,
                         RentalMapper rentalMapper,
                         PaymentService paymentService) {
        this.rentalRepository = rentalRepository;
        this.scooterRepository = scooterRepository;
        this.rentalMapper = rentalMapper;
        this.paymentService = paymentService;
    }

    @Transactional(readOnly = true)
    public List<RentalResponseDto> findAll() {
        return rentalRepository.findAll()
                .stream()
                .map(rentalMapper::toRentalResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public RentalResponseDto findById(Long id) {
        return rentalRepository.findById(id)
                .map(rentalMapper::toRentalResponseDto)
                .orElseThrow(() -> new RentalNotFoundException("Could not find rental with id " + id));
    }

    @Transactional
    public RentalResponseDto save(User user, RentalRequestDto rentalRequestDto) {
        Scooter scooter = scooterRepository.findById(rentalRequestDto.getScooterId())
                .orElseThrow(
                        () -> new ScooterNotFoundException("Scooter with id "
                                + rentalRequestDto.getScooterId()
                                + " not found")
                );
        validateScooterAvailability(scooter);

        Rental rental = prepareRental(user, scooter);

        scooter.setStatus(ScooterStatus.INACTIVE);

        return rentalMapper.toRentalResponseDto(rentalRepository.save(rental));
    }

    public void delete(Long id) {
        if (!rentalRepository.existsById(id)) {
            throw new RentalNotFoundException("Could not find rental with id " + id);
        }
        rentalRepository.deleteById(id);
    }

    @Transactional
    public RentalResponseDto endRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(
                        () -> new RentalNotFoundException("Could not find rental with id " + rentalId)
                );

        if (rental.getEndTime() != null) {
            throw new RentalException("Rental with id: " + rentalId + " already ended");
        }
        rental.setEndTime(LocalDateTime.now());

        Scooter scooter = rental.getScooter();
        scooter.setStatus(ScooterStatus.ACTIVE);
        scooterRepository.save(scooter);

        BigDecimal price = calculatePrice(rental.getStartTime(), rental.getEndTime(), scooter.getTariff());
        rental.setPrice(price);

        try {
            paymentService.processPayment(rental);
            rental.setRentalStatus(RentalStatus.PAYMENT_COMPLETED);
            log.info("Rental {} ended and paid successfully", rentalId);
        } catch (Exception e) {
            rental.setRentalStatus(RentalStatus.PAYMENT_FAILED);
            log.warn("Rental {} ended but payment failed: insufficient funds. User: {}",
                    rentalId, rental.getUser().getId());
        }

        return rentalMapper.toRentalResponseDto(rentalRepository.save(rental));
    }

    private BigDecimal calculatePrice(LocalDateTime start, LocalDateTime end, BigDecimal tariff) {
        long days = ChronoUnit.DAYS.between(start, end);
        if (days <= 0) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        return tariff.multiply(BigDecimal.valueOf(days));
    }

    private void validateScooterAvailability(Scooter scooter) {
        if (scooter.getStatus() == ScooterStatus.INACTIVE) {
            throw new RentalException("Unfortunately, scooter status is INACTIVE so you can't rent it. "
                    + "Please try again later or choose another one.");
        }
    }

    private Rental prepareRental(User user, Scooter scooter) {
        Rental rental = new Rental();
        rental.setScooter(scooter);
        rental.setRentalStatus(RentalStatus.ACTIVE);
        rental.setUser(user);
        rental.setStartTime(LocalDateTime.now());

        return rental;
    }
}
