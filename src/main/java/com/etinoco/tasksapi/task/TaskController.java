package com.etinoco.tasksapi.task;

import java.util.Map;
import java.util.HashMap;

import com.etinoco.tasksapi.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@RestController
@RequestMapping("api/tasks")
public class TaskController {
  @Autowired
  private TaskService taskService;

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Iterable<Task>> list(){
    Iterable<Task> tasks = taskService.list();
    return createHashPlural(tasks);
  }

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Task> read(@PathVariable Long id) {
    Task task = taskService
      .findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("No task with that ID"));
    return createHashSingular(task);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Task> create(@Validated @RequestBody Task task) {
    Task createdTask = taskService.create(task);
    return createHashSingular(createdTask);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, Task> update(@RequestBody Task task, @PathVariable Long id) {
    Task updatedTask = taskService
      .update(task)
      .orElseThrow(() -> new ResourceNotFoundException("No task with that ID"));

    return createHashSingular(updatedTask);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable Long id) {
    taskService.deleteById(id);
  }

  private Map<String, Task> createHashSingular(Task task){
    Map<String, Task> response = new HashMap<String, Task>();
    response.put("task", task);

    return response;
  }

  private Map<String, Iterable<Task>> createHashPlural(Iterable<Task> tasks){
    Map<String, Iterable<Task>> response = new HashMap<String, Iterable<Task>>();
    response.put("tasks", tasks);

    return response;
  }
}