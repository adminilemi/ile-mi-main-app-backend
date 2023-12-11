package com.thistlestechnology.ilemimainapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentRegisterResponse {
    private String token;
    private String agentId;
    private String message;


}
