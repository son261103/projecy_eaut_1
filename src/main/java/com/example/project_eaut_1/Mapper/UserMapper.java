package com.example.project_eaut_1.Mapper;


import com.example.project_eaut_1.DTO.UsersDTO;
import com.example.project_eaut_1.Entity.Role;
import com.example.project_eaut_1.Entity.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper implements EntityMapper<Users , UsersDTO>{

    @Override
    public Users toEntity(UsersDTO dto) {
        return Users
                .builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .role(Role.builder().build())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    @Override
    public UsersDTO toDto(Users entity) {
        return UsersDTO
                .builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .roleId(entity.getRole().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }

    @Override
    public List<Users> toEntity(List<UsersDTO> Dto) {
        return List.of();
    }

    @Override
    public List<UsersDTO> toDto(List<Users> entity) {
        return List.of();
    }
}
