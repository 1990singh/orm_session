package com.jpa.advance.example;

import com.google.common.collect.Sets;
import com.google.inject.AbstractModule;

import com.yammer.dropwizard.db.DatabaseConfiguration;

import java.util.Properties;

import fk.sp.common.extensions.dropwizard.db.HasDatabaseConfiguration;
import fk.sp.common.extensions.guice.jpa.spring.JpaWithSpringModule;
import com.jpa.advance.example.config.OrderModule;

/**
 * Created by adarsh.m on 21/01/15.
 */
public class TestModule extends AbstractModule {

  @Override
  protected void configure() {
    install(new OrderModule());
    install(new JpaWithSpringModule(Sets.newHashSet("com.jpa.advance.example.model"), new Properties()) {
      @Override
      protected void additionalJpaProperties(Properties jpaProperties) {
        super.additionalJpaProperties(jpaProperties);
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
      }
    });

    bind(HasDatabaseConfiguration.class).toInstance(getDatabaseConfiguration());
  }

  public HasDatabaseConfiguration getDatabaseConfiguration() {
    return new HasDatabaseConfiguration() {
      @Override
      public DatabaseConfiguration getDatabaseConfiguration() {
        DatabaseConfiguration databaseConfiguration = new DatabaseConfiguration();
        databaseConfiguration.setDriverClass("org.hsqldb.jdbc.JDBCDriver");
        databaseConfiguration.setUrl("jdbc:hsqldb:mem:.");
        databaseConfiguration.setUser("SA");
        databaseConfiguration.setPassword("");
        return databaseConfiguration;
      }
    };
  }
}
