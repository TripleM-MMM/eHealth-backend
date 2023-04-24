package com.mstrzezon.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserOutDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String keycloakUserId;
}
