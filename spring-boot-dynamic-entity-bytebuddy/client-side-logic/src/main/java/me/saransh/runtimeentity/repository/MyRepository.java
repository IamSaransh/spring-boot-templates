package me.saransh.runtimeentity.repository;

import me.saransh.runtimeentity.models.Entity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface MyRepository extends MongoRepository<Entity, String> {
    Optional<Entity> findEntityByIdentifier(UUID identifier);
    Optional<Entity> findEntityByNamespace(String namespace);
}
