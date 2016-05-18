package com.jpa.example.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by adarsh.m on 20/01/15.
 */

@Data
@Entity
public class Course {

  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id = null;

  private String name;

  @ManyToMany(mappedBy = "courses")
  private List<Student> students = newArrayList();

}
