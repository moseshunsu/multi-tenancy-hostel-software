package net.hostelHub.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String customer_code;
    private String phone;
    private String metadata;
    private String risk_action;
    private String international_format_phone;
}