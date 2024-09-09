package org.daniel.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String username;
  String password;
  String token;
  String token_type;

  LocalDateTime created_at; // Cambiar String a LocalDateTime
}
