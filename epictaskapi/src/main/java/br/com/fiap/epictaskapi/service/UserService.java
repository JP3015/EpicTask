package br.com.fiap.epictaskapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.dto.UserDto;
import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User save(User user){
        return repository.save(user);
    }

    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public UserDto get(Long id) {
        Optional<User> findById = repository.findById(id);
        if (findById.isEmpty()) return null;
        return findById.get().toDto();
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }

}