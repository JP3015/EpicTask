package br.com.fiap.epictaskapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictaskapi.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
    
}
