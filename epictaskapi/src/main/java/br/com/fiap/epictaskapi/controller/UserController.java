package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    @Cacheable("user")
    public Page<User> index(@PageableDefault(size = 5) Pageable pageable){
        return service.listAll(pageable);
    }


    @PostMapping
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<User> create(@RequestBody @Valid User user){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.save(user)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<User> show(@PathVariable Long id){
        var option = service.get(id);
        var user = option.get();
        user.setPassword(null);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(service.save(user)
        );
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<User> optional = service.get(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        service.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    @CacheEvict(value = "user", allEntries = true)
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid User newUser){
        Optional<User> optional = service.get(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var user = optional.get();
        BeanUtils.copyProperties(newUser, user);
        user.setId(id);
        service.save(user);

        return ResponseEntity.ok(user);
    }
    
}
