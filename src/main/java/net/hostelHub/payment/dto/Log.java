package net.hostelHub.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Log {
    private String start_time;
    private String time_spent;
    private String attempts;
    private String authentication;
    private String errors;
    private String success;
    private String mobile;
    private String[] input;
    private History[] history;
}
