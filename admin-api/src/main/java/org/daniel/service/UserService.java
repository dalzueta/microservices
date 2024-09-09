package org.daniel.service;

import java.util.List;
import org.daniel.model.User;
import org.daniel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository repository;

  public List<User> getAll() {
    return repository.findAll();
  }

  public User add(User user) {
    return repository.save(user);
  }
}
