package org.nutrihealthplan.dietapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private  String username;
    private List<String> roles;
    private String jwtToken;

}
