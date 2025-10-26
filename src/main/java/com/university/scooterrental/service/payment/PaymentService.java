package com.university.scooterrental.service.payment;

import com.university.scooterrental.exception.PaymentException;
import com.university.scooterrental.model.payment.Payment;
import com.university.scooterrental.model.payment.PaymentStatus;
import com.university.scooterrental.model.rental.Rental;
import com.university.scooterrental.model.user.User;
import com.university.scooterrental.repository.PaymentRepository;
import com.university.scooterrental.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    @Transactional
    public void processPayment(Rental rental) {
        User user = rental.getUser();
        BigDecimal amountToPay = rental.getPrice();

        validatePaymentAmount(amountToPay);

        if (user.getBalance().compareTo(amountToPay) < 0) {
            handleInsufficientFunds(user, rental, amountToPay);
        }

        processSuccessfulPayment(user, rental, amountToPay);
    }

    private void validatePaymentAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentException("Invalid payment amount: " + amount);
        }
    }

    private void handleInsufficientFunds(User user, Rental rental, BigDecimal amountToPay) {
        Payment payment = preparePayment(user, rental, amountToPay, PaymentStatus.FAILED);
        paymentRepository.save(payment);

        log.warn("Payment failed for user {}: insufficient funds. Balance: {}, Required: {}",
                user.getId(), user.getBalance(), amountToPay);

        throw new PaymentException("Payment failed. User have not enough balance to pay. "
                + "Please top up your balance and try again. "
                + "Your balance: " + user.getBalance()
                + " Amount to pay: " + amountToPay);
    }

    private void processSuccessfulPayment(User user, Rental rental, BigDecimal amountToPay) {
        user.setBalance(user.getBalance().subtract(amountToPay));
        userRepository.save(user);

        Payment payment = preparePayment(user, rental, amountToPay, PaymentStatus.SUCCESS);
        Payment savedPayment = paymentRepository.save(payment);

        log.info("Payment processed successfully: paymentId={}, userId={}, amount={}",
                savedPayment.getId(), user.getId(), amountToPay);
    }

    private Payment preparePayment(User user, Rental rental, BigDecimal amount, PaymentStatus status) {
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setRental(rental);
        payment.setAmount(amount);
        payment.setStatus(status);
        payment.setCreatedAt(LocalDateTime.now());

        return payment;
    }
}
