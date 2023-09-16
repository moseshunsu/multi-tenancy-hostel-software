package net.hostelHub.payment.repository;

import net.hostelHub.payment.entity.PaymentPayStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayStackPaymentRepository extends JpaRepository<PaymentPayStack, Long> {
}
