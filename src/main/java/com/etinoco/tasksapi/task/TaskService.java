package com.etinoco.tasksapi.task;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Iterable<Task> list() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public Task create(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> update(Task task) {
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        if (foundTask.isPresent()) {
            Task updatedTask = foundTask.get();
            updatedTask.setTitle(task.getTitle());
            updatedTask.setComment(task.getComment());
            updatedTask.setComplete(task.getComplete());
            updatedTask.setDate(task.getDate());

            taskRepository.save(updatedTask);
            return Optional.of(updatedTask);
        } else {
            return Optional.empty();
        }
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}