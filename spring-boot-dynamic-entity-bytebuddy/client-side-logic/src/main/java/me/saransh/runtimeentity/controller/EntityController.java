package me.saransh.runtimeentity.controller;

import me.saransh.runtimeentity.models.Entity;
import me.saransh.runtimeentity.repository.MyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class EntityController {

    @Autowired
    private MyRepository repository;
    @GetMapping("/entities")
    public List<Entity> getModels(){
        return repository.findAll();
    }

    @GetMapping("/entity/{namespace}")
    public ResponseEntity<Entity> getModels(@PathVariable String namespace){
        Optional<Entity> retriedEntity =  repository.findEntityByNamespace(namespace);
//        Optional<Entity> debug = repository.findEntityByNamespace("com.namespace");
        if(!retriedEntity.isEmpty())

            return ResponseEntity.ok(retriedEntity.get());
        else  throw new RuntimeException("Entity not found");
    }

    @PostMapping("/entity")
    public String registerEntity(@RequestBody Entity entity){
        Entity saved = repository.save(entity);
//        URI uri = URI.create("http://localhost:8081/db/EntityDB/entity/%22" + saved.getIdentifier() + "%22" );
        return "Created your Entity";
    }


}
