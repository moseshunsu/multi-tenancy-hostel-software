package net.hostelHub.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
//import net.hostelHub.dto.Data;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@lombok.Data
public class CreatePlanResponse {

    private Boolean status;
    private String message;
    private Data data;

}
