package com.estech.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estech.workshopmongo.domain.User;
import com.estech.workshopmongo.dto.UserDTO;
import com.estech.workshopmongo.repository.UserRepository;
import com.estech.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repo;
	
	public List<User> findAll() {
		return repo.findAll();
	}
	
	public User findById(String id) {
		Optional<User> user = repo.findById(id);
		return user.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
	}
	
	public User insert(User obj) {
		return repo.insert(obj);
	}
	
	public void deleteById(String id) {
		if(!(findById(id)==null)) {
			repo.deleteById(id);
		}
	}
	
	public User update(User obj) {
		try {
			Optional<User> newUser = repo.findById(obj.getId());
			User user = newUser.get();
			updateData(user, obj);
			return repo.save(user);
		} catch (ObjectNotFoundException e) {
			throw new ObjectNotFoundException("Objeto não encontrado");
		}
	}
	
	private void updateData(User user, User obj) {
		user.setName(obj.getName());
		user.setEmail(obj.getEmail());
	}
	
	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
}
