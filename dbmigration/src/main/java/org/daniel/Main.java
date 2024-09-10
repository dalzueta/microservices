package org.daniel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;


public class Main {

  public static void main(String[] args) {
    System.out.println("Initializing db migration!");

    // Configuración de la conexión a la base de datos
    String url = "jdbc:mariadb://localhost:3306/authdb";
    String username = "root";
    String password = "example";

    try {
      Connection connection = DriverManager.getConnection(url, username, password);
      Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));

      Liquibase liquibase = new Liquibase("src/main/resources/db/migration/changelog-master.xml", new ClassLoaderResourceAccessor(), database);

      liquibase.update(new Contexts());
      System.out.println("Database migration completed successfully!");

    } catch (SQLException | LiquibaseException e) {
      e.printStackTrace();
      System.err.println("Error during database migration: " + e.getMessage());
    }
  }
}