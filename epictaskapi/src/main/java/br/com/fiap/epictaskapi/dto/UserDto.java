package br.com.fiap.epictaskapi.dto;

import br.com.fiap.epictaskapi.model.User;

public record UserDto(Long id, String name, String email) {

    public User toUser(){
        var user = new User();
        user.setId(id);
        user.setEmail(email);
        user.setName(name);
        return user;
    }

}