package com.jdbc.examples.dao;

import com.jdbc.examples.dao.models.Author;

import java.util.List;

/**
 * Created by adarsh.singh on 18/05/16.
 */
public interface AuthorRepo {

    List<Author> getAuthors();

}
