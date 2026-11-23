package com.example.springboot.restapi_mysql;

import com.example.springboot.restapi_mysql.entity.Task;

import jakarta.validation.constraints.NotBlank;

public class TaskDtos {
  
  public record TaskRequest(
      @NotBlank(message = "Title is required") String title,
      boolean completed
  ) {}

  public record TaskResponse(
      Long id,
      String title,
      boolean completed
  ) {
      public static TaskResponse from(Task task) {
          return new TaskResponse(task.getId(), task.getTitle(), task.isCompleted());
      }
  }
}
