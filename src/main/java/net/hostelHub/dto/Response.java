package net.hostelHub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String responseCode;
    private String responseMessage;
    private Data data;

}
