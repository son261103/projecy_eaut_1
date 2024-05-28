package com.example.project_eaut_1.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class RolesDTO {
    private Integer id;
    private String name;
    private Set<UsersDTO> users;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
