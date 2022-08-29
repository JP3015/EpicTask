package br.com.fiap.epictaskapi.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

public class UserService {

    @Autowired
    private UserRepository repository;

    public void save(User user){
        repository.save(user);
    }
    
}
