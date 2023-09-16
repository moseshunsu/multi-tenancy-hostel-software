package net.hostelHub.payment.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanDto {
    private String name;
    private String interval;
    private Integer amount;
}
