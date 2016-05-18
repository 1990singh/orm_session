package com.jdbc.examples.dao.internal;

import com.jdbc.examples.dao.AuthorRepo;
import com.jdbc.examples.dao.models.Author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adarsh.singh on 18/05/16.
 */
public class JdbcAuthorRepo implements AuthorRepo {

  final String DATABASE_URL = "jdbc:mysql://localhost/books";
  final String SELECT_QUERY = "SELECT authorID, firstName, lastName FROM authors";

  @Override
  public List<Author> getAuthors() {

    List<Author> authors = new ArrayList<>();

    // use try-with-resources to connect to and query the database
    try (
        Connection connection = DriverManager.getConnection(
            DATABASE_URL, "root", "");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SELECT_QUERY)) {

      // display query results
      while (resultSet.next()) {
        Author author = new Author();
        author.setAuthorId(resultSet.getInt("authorID"));
        author.setFirstName(resultSet.getString("firstName"));
        author.setLastName(resultSet.getString("lastName"));

        authors.add(author);
      }
    } // AutoCloseable objects' close methods are called now
    catch (SQLException sqlException) {
      sqlException.printStackTrace();
    }
    return authors;
  }
}
