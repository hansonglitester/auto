package com.hsl.cn.utils.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

/**
 * 第二个数据源，jpa的相关配置
 */
@Configuration
//1、实体扫描
//2、实体管理ref
//3、事务管理
@EnableJpaRepositories(
        basePackages = "com.hsl.cn.respority.dev",
        entityManagerFactoryRef = "resultEntityManagerFactoryBean",
        transactionManagerRef = "resultTransactionManager")
@EnableTransactionManagement
public class JpaDevConfiguration {

    //第二个数据源，必须加Qualifier
    @Autowired
    @Qualifier("dataSourceResult")
    private DataSource dataSource;

    //jpa其他参数配置
    @Autowired
    private JpaProperties jpaProperties;

    //实体管理工厂builder
    @Autowired
    private EntityManagerFactoryBuilder factoryBuilder;

    /**
     * 配置第二个实体管理工厂的bean
     * @return
     */
    @Bean(name = "resultEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        return factoryBuilder.dataSource(dataSource)
                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                .packages("com.hsl.cn.pojo.dev")
                .persistenceUnit("resultPersistenceUnit")
                .build();
    }

    /**
     * EntityManager不过解释，用过jpa的应该都了解
     * @return
     */
    @Bean(name = "resultEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactoryBean().getObject().createEntityManager();
    }

    /**
     * jpa事务管理
     * @return
     */
    @Bean(name = "resultTransactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
