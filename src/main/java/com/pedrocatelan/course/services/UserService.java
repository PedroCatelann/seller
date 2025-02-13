package com.pedrocatelan.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pedrocatelan.course.entities.User;
import com.pedrocatelan.course.repositories.UserRepository;
import com.pedrocatelan.course.services.exceptions.DatabaseException;
import com.pedrocatelan.course.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public List<User> findAll() {

		return repository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = repository.findById(id);

		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User obj) {
		obj.setPassword(new BCryptPasswordEncoder().encode(obj.getPassword()));
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			if (repository.existsById(id)) {
				repository.deleteById(id);
			} else {
				throw new ResourceNotFoundException(id);
			}
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public User update(Long id, User obj) {

		try {
			User entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}

	}

	private void updateData(User entity, User obj) {

		entity.setName(obj.getName());
		entity.setLogin(obj.getLogin());
		entity.setPhone(obj.getPhone());

	}
}
