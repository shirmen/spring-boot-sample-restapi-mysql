package com.example.springboot.restapi_mysql.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot.restapi_mysql.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
