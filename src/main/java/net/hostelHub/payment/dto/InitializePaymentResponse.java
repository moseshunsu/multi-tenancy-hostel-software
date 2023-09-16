package net.hostelHub.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentResponse {
    private Boolean status;
    private String message;
    private net.hostelHub.payment.dto.Data data;
}
