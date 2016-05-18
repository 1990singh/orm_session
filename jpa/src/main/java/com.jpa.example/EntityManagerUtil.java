package com.jpa.example;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by adarsh.m on 20/01/15.
 */
public class EntityManagerUtil {

  public static EntityManagerFactory getEntityManagerFactory() {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hsqldb-ds");
    return emf;
  }

}
