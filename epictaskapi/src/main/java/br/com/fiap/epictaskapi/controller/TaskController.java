package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

import br.com.fiap.epictaskapi.model.Task;
import br.com.fiap.epictaskapi.service.TaskService;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService service;
    
    @GetMapping
    @Cacheable("task")
    public Page<Task> index(@PageableDefault(size = 5) Pageable pageable){
        return service.listAll(pageable);
    }

    @PostMapping
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Task> create(@RequestBody @Valid Task task){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(service.save(task)
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> show(@PathVariable Long id){
        return ResponseEntity.of(service.get(id));
    }

    @DeleteMapping("{id}")
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<Task> optional = service.get(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        service.remove(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("{id}")
    @CacheEvict(value = "task", allEntries = true)
    public ResponseEntity<Task> update(@PathVariable Long id, @RequestBody @Valid Task newTask){
        Optional<Task> optional = service.get(id);
        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var task = optional.get();
        BeanUtils.copyProperties(newTask, task);
        task.setId(id);
        service.save(task);

        return ResponseEntity.ok(task);
    }
}
