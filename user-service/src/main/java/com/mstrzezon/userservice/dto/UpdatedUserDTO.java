package com.mstrzezon.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedUserDTO {
    private String firstName;
    private String lastName;
    private String email;
}
