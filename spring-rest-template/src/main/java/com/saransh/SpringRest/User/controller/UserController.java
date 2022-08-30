package com.saransh.SpringRest.User.controller;

import com.saransh.SpringRest.User.dao.UserDaoService;
import com.saransh.SpringRest.User.exception.UserNotFoundException;
import com.saransh.SpringRest.User.model.User;
import com.saransh.SpringRest.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/v2/users")
    public List<User> retrieveAllUsersv2(){
        return repository.findAll();
    }

    @GetMapping("/v2/users/{id}")
    public EntityModel<User> retrieveUsersByIdv2(@PathVariable int id){
        Optional<User> foundUser = Optional.of(repository.getById((long) id));
        if(foundUser.isEmpty())
            throw new UserNotFoundException("User Not Found!");
        EntityModel<User> model = EntityModel.of(foundUser.get());
        WebMvcLinkBuilder linkToUsers = WebMvcLinkBuilder.
                linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsersv2());
        model.add(linkToUsers.withRel("all-users"));
        return model;
    }

    @PostMapping("/v2/users")
    public ResponseEntity<Object> createUserv2(@Valid @RequestBody User user){
        User savedUser = repository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/v2/users/{id}")
    public void deleteUsev2r(@PathVariable int id){
        User toDeleteUser = repository.getById((long) id);
        if(toDeleteUser==null)
            throw new UserNotFoundException("User not found in DB. Delete Failed!");
        repository.delete(toDeleteUser);
    }


}
