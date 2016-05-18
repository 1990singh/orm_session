package com.jdbc.examples;

import com.jdbc.examples.dao.AuthorRepo;
import com.jdbc.examples.dao.internal.JdbcAuthorRepo;

/**
 * Created by adarsh.singh on 18/05/16.
 */
public class main {

  public static void main(String[] args) {
    AuthorRepo authorRepo = new JdbcAuthorRepo();
    AuthorsResources resources = new AuthorsResources(authorRepo);
    resources.displayAuthors();
  }

}
