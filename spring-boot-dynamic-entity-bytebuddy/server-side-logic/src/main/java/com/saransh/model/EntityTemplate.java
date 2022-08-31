package com.saransh.model;


import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
public class EntityTemplate  {

    protected UUID identifier;
    protected String name;
    protected String namespace;

}
