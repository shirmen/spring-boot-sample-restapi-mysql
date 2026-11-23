package com.example.springboot.restapi_mysql.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.springboot.restapi_mysql.TaskDtos.TaskRequest;
import com.example.springboot.restapi_mysql.TaskDtos.TaskResponse;
import com.example.springboot.restapi_mysql.entity.Task;
import com.example.springboot.restapi_mysql.repository.TaskRepository;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    // READ ALL
    @GetMapping
    public List<TaskResponse> getAll() {
        return repository.findAll().stream()
                .map(TaskResponse::from)
                .toList();
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse create(@Valid @RequestBody TaskRequest request) {
        Task saved = repository.save(new Task(request.title(), request.completed()));
        return TaskResponse.from(saved);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable Long id, @Valid @RequestBody TaskRequest request) {
        return repository.findById(id)
                .map(task -> {
                    task.setTitle(request.title());
                    task.setCompleted(request.completed());
                    return ResponseEntity.ok(TaskResponse.from(repository.save(task)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}