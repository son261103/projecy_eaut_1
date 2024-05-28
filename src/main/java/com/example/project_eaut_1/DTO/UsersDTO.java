package com.example.project_eaut_1.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private String phone;
    private String address;
    private Integer roleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
