package com.saransh.config;

import com.saransh.model.EntityTemplate;
import com.saransh.model.client.Attribute;
import com.saransh.model.client.ClientSideModel;
import com.saransh.model.client.EntityList;
import com.saransh.repository.IEntityDaoTemplate;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.description.type.TypeDescription.Generic;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.DynamicType.Loaded;
import net.bytebuddy.dynamic.DynamicType.Unloaded;
import net.bytebuddy.dynamic.TargetType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.implementation.auxiliary.AuxiliaryType.DEFAULT_TYPE_MODIFIER;

@Slf4j
class DynamicClassGenerator {



    @SneakyThrows
    public Optional<Class<?>> createJpaEntity(String entityClassName) {
        if (classFileExists(entityClassName)) {
            log.info("The Entity class " + entityClassName + " already exists, not creating a new one");
            return Optional.empty();
        }
        log.info("Creating new Entity class: {}...", entityClassName);
        //fetching the model from clientside same as : http://localhost:8080/entity/com.namespace
        ClientSideModel clientSideModel = getClientEntityByNameSpace("com.namespace"); //= proxy.getConfigByEntity("com.namespace");
        //for all the internal entities create a .class file and add them to  internalLoadedClassList list
        ArrayList<EntityList> clientSideModelEntityList = clientSideModel.getEntityList();
        List<Class<?>> internalLoadedClassList = new ArrayList<>();
        //for each of the defines internal entities load a class and add to list with their respective attributes
        // O(internalEntities * maxAttributesPerEntity
        for(EntityList entityList: clientSideModelEntityList){
            DynamicType.Builder<Object> internalEntityClassBuilder = new ByteBuddy().subclass(Object.class)
                    .name(entityList.getName())
                    .modifiers(DEFAULT_TYPE_MODIFIER);
            for(Attribute attribute: entityList.getAttributes()){
                String fieldName = attribute.getName();
                log.info(fieldName);
                Class<?> clazz = Class.forName(getClassName(attribute.getType()));
                internalEntityClassBuilder = internalEntityClassBuilder.defineField(fieldName, clazz, Visibility.PUBLIC);
            }
            Unloaded<?> unloadedInternalListClass =  internalEntityClassBuilder.make();
            Class<?> aClass = saveGeneratedClassAsFile(unloadedInternalListClass);
            internalLoadedClassList.add(aClass);
        }

        DynamicType.Builder<EntityTemplate> entityTemplateBuilder = new ByteBuddy()
                .subclass(EntityTemplate.class)
                .name(entityClassName)
                .modifiers(DEFAULT_TYPE_MODIFIER);
        for (Attribute attribute : clientSideModel.attributes) {
            String fieldName = attribute.getName();
            log.info(fieldName);
            Class<?> clazz = Class.forName(getClassName(attribute.getType()));
            entityTemplateBuilder = entityTemplateBuilder.defineField(fieldName, clazz, Visibility.PUBLIC);
        }

        for(Class<?> clazz: internalLoadedClassList){
            entityTemplateBuilder = entityTemplateBuilder.defineField(clazz.getName().toString(),
                    TypeDescription.Generic.Builder.parameterizedType(
                            List.class, clazz).build(),
                    Visibility.PRIVATE);
        }
        Unloaded<?> generatedClass = entityTemplateBuilder
                .annotateType(AnnotationDescription.Builder.ofType(Document.class).build()).make();
        return Optional.of(saveGeneratedClassAsFile(generatedClass));
    }


    public Optional<Class<?>> createMongoRepository(Class<?> entityClass, String repositoryClassName) {
        if (classFileExists(repositoryClassName)) {
            log.info("The Repository class " + repositoryClassName + " already exists, not creating a new one");
            return Optional.empty();
        }
        log.info("Creating new Repo class: {}...", repositoryClassName);
        Generic crudRepo = Generic.Builder.parameterizedType(MongoRepository.class, entityClass, String.class).build();
        Unloaded<?> generatedClass = new ByteBuddy().makeInterface(crudRepo).implement(IEntityDaoTemplate.class)
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

    private Class<?> saveGeneratedClassAsFile(Unloaded<?> unloadedClass ) {

        Loaded<?> loadedClass = unloadedClass.load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION);
        try {
            loadedClass.saveIn(new File("server-side-logic/target/classes"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loadedClass.getLoaded();
    }

    private String getClassName(String type) {
        switch (type) {
            case "String":
                return "java.lang.String";
            case "Long":
                return "java.lang.Long";
            case "Integer":
                return "java.lang.Integer";
            case "Boolean":
                return "java.lang.Boolean";
            default:
                return null;
        }
    }

    //** dummy proxy replacement for now
    private ClientSideModel getClientEntityByNameSpace(String namespace) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:8080/entity/" + namespace;
        URI uri = new URI(baseUrl);

        ResponseEntity<ClientSideModel> result = restTemplate.getForEntity(uri, ClientSideModel.class);
        log.info(result.getBody().getClass().toString());
//        log.info(result.getBody().toString());
        return result.getBody();
    }
}

//        entityTemplateBuilder = entityTemplateBuilder.defineField("listA",
//                TypeDescription.Generic.Builder.parameterizedType(
//                        List.class, TargetType.class).build(),
//                Visibility.PRIVATE);
