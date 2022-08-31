package com.saransh.repository;


import com.saransh.model.EntityTemplate;

import java.util.List;

public interface IEntityDaoTemplate {
    List<EntityTemplate> findByNameContainingIgnoreCase(String name);
    Iterable<? extends EntityTemplate> findAll();
    Iterable<? extends EntityTemplate> findByNamespace(String namespace);
}