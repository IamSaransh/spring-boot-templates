package com.saransh.controller;

import com.saransh.model.EntityTemplate;
import com.saransh.repository.IEntityDaoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/hehe")
public class EntityContoller {
    private final IEntityDaoTemplate entityDao;

    public EntityContoller(IEntityDaoTemplate bookDao) {
        this.entityDao = bookDao;
    }

    @GetMapping
    public Iterable<? extends EntityTemplate> getBooks() {
        return entityDao.findAll();
    }
}
