package com.jdbc.examples;

import com.jdbc.examples.dao.AuthorRepo;
import com.jdbc.examples.dao.internal.JdbcAuthorRepo;

/**
 * Created by adarsh.singh on 18/05/16.
 */
public class AuthorsResources {

  private final AuthorRepo authorRepo;

  public AuthorsResources(AuthorRepo authorRepo) {
    this.authorRepo = authorRepo;
  }

  public void displayAuthors() {
    System.out.println(authorRepo.getAuthors());
  }
}
