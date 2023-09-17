package net.hostelHub.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentData {
    private Long id;
    private String domain;
    private String status;
    private String reference;
    private String receipt_number;
    private BigDecimal amount;
    private String message;
    private String gateway_response;
    private String paid_at;
    private String created_at;
    private String channel;
    private String currency;
    private String ip_address;
    private String metadata;
    private Log log;
    private int fees;
    private String fees_split;

    private Authorization authorization;
    private Customer customer;

    private String plan;
    private Map<String, Object> split;
    private String order_id;
    private String paidAt;
    private String createdAt;
    private int requested_amount;
    private String pos_transaction_data;
    private String source;
    private String fees_breakdown;
    private String transaction_date;
    private Map<String, Object> plan_object;
    private Map<String, Object> subaccount;

}
