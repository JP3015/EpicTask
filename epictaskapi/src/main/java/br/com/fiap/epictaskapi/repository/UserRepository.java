package br.com.fiap.epictaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictaskapi.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
