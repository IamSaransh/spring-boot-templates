package com.saransh.config;

import com.saransh.repository.IEntityDaoTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Optional;



@Configuration
public class DynamicJpaBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(DynamicJpaBeanFactoryPostProcessor.class);

    private static final String ENTITY_CLASS_NAME = "Entity";
    private static final String REPO_CLASS_NAME = "EntityMongoRepository";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        DynamicClassGenerator dynamicClassGenerator = new DynamicClassGenerator();
        String packageName = IEntityDaoTemplate.class.getPackageName();
        Optional<Class<?>> entityClass = dynamicClassGenerator.createJpaEntity(packageName + "."
                + ENTITY_CLASS_NAME);

        if (entityClass.isEmpty()) {
            return;
        }

        Optional<Class<?>> repoClass =
                dynamicClassGenerator.createMongoRepository(entityClass.get(),
                        packageName + "." + REPO_CLASS_NAME);

        if (repoClass.isEmpty()) {
            return;
        }

        LOG.info("Created the Entity class {} and Repository class {} successfully", ENTITY_CLASS_NAME,
                REPO_CLASS_NAME);

        registerJpaRepositoryFactoryBean(repoClass.get(), (DefaultListableBeanFactory) beanFactory);
    }

    /**
     * Registers a {@link SimpleMongoClientDatabaseFactory} similar to below:
     *
     * <pre>
     * &#64;Bean
     * public JpaRepositoryFactoryBean<BookDao, Book, Integer> bookRepository() {
     *     return new JpaRepositoryFactoryBean<>(BookDao.class);
     * }
     * </pre>
     *
     * Since the generic arguments are not necessary, these are ignored.
     *
     * @param jpaRepositoryClass
     * @param defaultListableBeanFactory
     */
    private void registerJpaRepositoryFactoryBean(Class<?> jpaRepositoryClass,
                                                  DefaultListableBeanFactory defaultListableBeanFactory) {
        String beanName = jpaRepositoryClass.getSimpleName();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
                .rootBeanDefinition(SimpleMongoClientDatabaseFactory.class).addConstructorArgValue(jpaRepositoryClass);

        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        LOG.info("Registered the {} bean for {} successfully", MongoClientFactoryBean.class.getSimpleName(),
                beanName);
    }

}

