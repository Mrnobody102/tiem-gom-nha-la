package com.example.tiemgomnhala.repository;

import com.example.tiemgomnhala.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Query("{$and: [{'deleteFlg' : 'PRESERVED'}, {'status' : 'ACTIVE'}, {'email': ?0}]}")
    Optional<User> findByEmail(String email);
}
