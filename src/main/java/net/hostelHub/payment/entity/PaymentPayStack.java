package net.hostelHub.payment.entity;

import jakarta.persistence.*;
import lombok.*;
import net.hostelHub.payment.utils.PricingPlanType;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "paystack_payment")
public class PaymentPayStack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String reference;
    private BigDecimal amount;
    private String gatewayResponse;
    private String paidAt;
    private String createdAt;
    private String channel;
    private String currency;
    private String ipAddress;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private PricingPlanType pricingPlanType = PricingPlanType.BASIC;

    @CreationTimestamp
    private Date createdOn;
}