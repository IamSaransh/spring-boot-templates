package com.saransh.config;

import com.saransh.model.EntityTemplate;
import com.saransh.repository.IEntityDaoTemplate;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

class DynamicClassGenerator {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicClassGenerator.class);

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     *
     * <pre>
     * &#64;Entity
     * &#64;Table(name = "book")
     * public class Book extends BookTemplateImpl {
     *
     * }
     * </pre>
     *
     * @param entityClassName
     * @description: hehe boi
     */
    public Optional<Class<?>> createJpaEntity(String entityClassName) {
        if (classFileExists(entityClassName)) {
            LOG.info("The Entity class " + entityClassName + " already exists, not creating a new one");
            return Optional.empty();
        }

        LOG.info("Creating new Entity class: {}...", entityClassName);

        Unloaded<?> generatedClass = new ByteBuddy().subclass(EntityTemplate.class)
                .annotateType(AnnotationDescription.Builder.ofType(Document.class).build())
                .name(entityClassName).make();
        return Optional.of(saveGeneratedClassAsFile(generatedClass));
    }

    /***
     * Creates the below class dynamically and loads it into the ClassLoader as
     * well as saves the .class file on the disk:
     *
     * <pre>
     * public interface BookDao extends BookDaoTemplate, CrudRepository&lt;Book, Integer&gt; {
     *
     *     &#64;Override
     *     &#64;Transactional
     *     &#64;Modifying
     *     &#64;Query("update Book set author.id = :authorId where id = :bookId")
     *     int updateAuthor(int bookId, int authorId);
     *
     * }
     * </pre>
     *
     * @param repositoryClassName
     * @param entityClass
     */
    public Optional<Class<?>> createMongoRepository(Class<?> entityClass, String repositoryClassName) {
        if (classFileExists(repositoryClassName)) {
            LOG.info("The Repository class " + repositoryClassName + " already exists, not creating a new one");
            return Optional.empty();
        }

        LOG.info("Creating new Repo class: {}...", repositoryClassName);

        Generic crudRepo = Generic.Builder.parameterizedType(MongoRepository.class, entityClass, String.class).build();

        Unloaded<?> generatedClass = new ByteBuddy().makeInterface(crudRepo).implement(IEntityDaoTemplate.class)
                .method(ElementMatchers.named("updateAuthor")).withoutCode()
                .annotateMethod(AnnotationDescription.Builder.ofType(Transactional.class).build())
                .name(repositoryClassName).make();

        return Optional.of(saveGeneratedClassAsFile(generatedClass));
    }

    private boolean classFileExists(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    private Class<?> saveGeneratedClassAsFile(Unloaded<?> unloadedClass) {

        Loaded<?> loadedClass = unloadedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);

        try {
            loadedClass.saveIn(new File("server-side-logic/target/classes"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return loadedClass.getLoaded();

    }

}
