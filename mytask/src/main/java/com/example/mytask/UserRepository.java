
package com.example.mytask;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.example.mytask.model.User;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers to Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findByEmail(String email);
	Page<User> findAll(Pageable pageable);

}	