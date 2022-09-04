package me.saransh.runtimeentity.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document
public class Entity extends Core{
    private Attribute[] attributes;
//    private Relationship[] relationships;
//    private Entity entity;
    private List<Entity> entityList;
}
