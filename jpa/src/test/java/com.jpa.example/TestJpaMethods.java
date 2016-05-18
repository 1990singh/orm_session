package com.jpa.example;



import com.jpa.example.model.Student;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class TestJpaMethods {

  private EntityManager em;

  @Before
  public void beforeEach() {
    em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
  }

  @After
  public void afterEach() {
    em.close();
  }

  @Test
  public void testAutoIncrement() {
    EntityTransaction transaction = em.getTransaction();
    transaction.begin();

    Student student1 = new Student();
    student1.setName("testStudent");

    assertNull(student1.getId());
    em.persist(student1);

    assertNotNull(student1.getId());

    transaction.commit();
  }

}
