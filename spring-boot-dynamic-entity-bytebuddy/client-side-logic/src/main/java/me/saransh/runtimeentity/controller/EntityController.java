package me.saransh.runtimeentity.controller;

import me.saransh.runtimeentity.models.Entity;
import me.saransh.runtimeentity.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    private MyRepository repository;
    @GetMapping
    public List<Entity> getModels(){
        return repository.findAll();
    }
}
