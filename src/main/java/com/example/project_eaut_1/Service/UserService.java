package com.example.project_eaut_1.Service;

import com.example.project_eaut_1.DTO.UsersDTO;
import com.example.project_eaut_1.Entity.Role;
import com.example.project_eaut_1.Entity.Users;
import com.example.project_eaut_1.Mapper.UserMapper;
import com.example.project_eaut_1.Repository.RoleRepository;
import com.example.project_eaut_1.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Không tìm thấy người dùng với tên đăng nhập: " + username);
        }
        return (UserDetails) user;
    }

    public Users findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UsersDTO saveUser(UsersDTO userDTO) {
        Users users = userMapper.toEntity(userDTO);
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            throw new RuntimeException("Không tìm thấy vai trò mặc định");
        }
        // Cập nhật ID của vai trò tùy thuộc vào logic của ứng dụng
        users.setRole(role);
        userRepository.save(users);
        return userMapper.toDto(users);
    }

    public UsersDTO saveAdmin(UsersDTO userDTO) {
        Users users = userMapper.toEntity(userDTO);
        users.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        users.setCreatedAt(LocalDateTime.now());
        users.setUpdatedAt(LocalDateTime.now());
        Role role = roleRepository.findByName("ADMIN");
        if (role == null) {
            throw new RuntimeException("Không tìm thấy vai trò ADMIN");
        }
        users.setRole(role);
        userRepository.save(users);
        return userMapper.toDto(users);
    }

    public String getUserRole(String username) {
        Users user = userRepository.findByUsername(username);
        if (user != null && user.getRole() != null) {
            return user.getRole().getName();
        }
        return null;
    }

    public boolean authenticate(String username, String password) {
        Users user = userRepository.findByUsername(username);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }
}
