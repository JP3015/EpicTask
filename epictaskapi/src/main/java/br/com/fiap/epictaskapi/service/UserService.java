package br.com.fiap.epictaskapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;


    public Page<User> listAll(Pageable pageable){
        return repository.findAll(pageable);
    }

    public User save(User user){
        return repository.save(user);
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public void remove(Long id) {
        repository.deleteById(id);
    }
    
}