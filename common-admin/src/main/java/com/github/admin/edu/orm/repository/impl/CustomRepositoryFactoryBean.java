package com.github.admin.edu.orm.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Created by 王建 on 2017/6/9.
 */
public class CustomRepositoryFactoryBean<T extends JpaRepository<S, ID>, S, ID extends Serializable>
        extends JpaRepositoryFactoryBean<T, S, ID> {

    public CustomRepositoryFactoryBean(Class<? extends T> repositoryInterface) {
        super(repositoryInterface);
    }

    public RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {

        return new CustomRepositoryFactory(entityManager);
    }

    private static class CustomRepositoryFactory extends JpaRepositoryFactory {

        public CustomRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
        }

        /*public <T, ID extends Serializable> SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information,
                                                                                          EntityManager entityManager) {
            return new CustomRepositoryImpl<T, ID >((Class<T>) information.getDomainType(), entityManager);
        }
        protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            JpaEntityInformation<?, ?> entityInformation = this.getEntityInformation(information.getDomainType());
            return (SimpleJpaRepository)this.getTargetRepositoryViaReflection(information, new Object[]{entityInformation, entityManager});
        }*/

        public SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information, EntityManager entityManager) {
            return new CustomRepositoryImpl(information.getDomainType(), entityManager);
        }

        public Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return CustomRepositoryImpl.class;
        }
    }

}
