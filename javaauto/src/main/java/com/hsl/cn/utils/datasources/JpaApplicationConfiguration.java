package com.hsl.cn.utils.datasources;

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
        basePackages = "com.hsl.cn.dataobject.respority.dev",
        entityManagerFactoryRef = "applicationEntityManagerFactoryBean",
        transactionManagerRef = "applicationTransactionManager")
@EnableTransactionManagement
public class JpaApplicationConfiguration {

    //第二个数据源，必须加Qualifier
    @Autowired
    @Qualifier("dataSourceApplication")
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
    @Bean(name = "applicationEntityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        return factoryBuilder.dataSource(dataSource)
                .properties(jpaProperties.getHibernateProperties(new HibernateSettings()))
                .packages("com.hsl.cn.dataobject.pojo.dev")
                .persistenceUnit("resultPersistenceUnit")
                .build();
    }

    /**
     * EntityManager不过解释，用过jpa的应该都了解
     * @return
     */
    @Bean(name = "applicationEntityManager")
    public EntityManager entityManager() {
        return entityManagerFactoryBean().getObject().createEntityManager();
    }

    /**
     * jpa事务管理
     * @return
     */
    @Bean(name = "applicationTransactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
        return jpaTransactionManager;
    }
}
