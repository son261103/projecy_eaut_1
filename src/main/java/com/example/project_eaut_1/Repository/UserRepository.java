package com.example.project_eaut_1.Repository;

import com.example.project_eaut_1.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
    Users findByUsername(String username);

}
