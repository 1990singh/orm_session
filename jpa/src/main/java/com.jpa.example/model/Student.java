package com.jpa.example.model;

import java.util.List;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import lombok.Data;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by adarsh.m on 20/01/15.
 */

@Data
@Entity
public class Student implements Serializable {

  private static final long serialVersionUID = -2862671438138322400L;

  @Id
  @GeneratedValue
  @Column(name="ID")
  private Long id = null;

  private String name;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(
      joinColumns={@JoinColumn(name="STD_ID", referencedColumnName="ID")},
      inverseJoinColumns={@JoinColumn(name="COR_ID", referencedColumnName="ID")})
  private List<Course> courses = newArrayList();

  @Version
  @Column(name = "version")
  private int version = 0;






}