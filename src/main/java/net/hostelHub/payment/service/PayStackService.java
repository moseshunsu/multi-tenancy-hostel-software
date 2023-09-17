package net.hostelHub.payment.service;

import net.hostelHub.exception.PaymentException;
import net.hostelHub.payment.dto.*;

public interface PayStackService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception;
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference) throws PaymentException;
}