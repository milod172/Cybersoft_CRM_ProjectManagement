package service;

import java.util.List;

import entity.Role;
import entity.Users;
import repository.RoleRepository;
import repository.UserRepository;

public class UserService {
	
	private UserRepository userRepository = new UserRepository();
	private RoleRepository roleRepository = new RoleRepository();
	
	public boolean addUser(int id_role, String fullname, String email, String password, String phone) {
		int count = userRepository.insert(id_role, fullname, email, password, phone);
		return count > 0;
	}
	
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	
	public List<Users> getAllUser(){
		return userRepository.findAll();
	}
	
	public boolean deleteUser(int id) {
		int count = userRepository.deleteById(id);
		return count > 0;
	}
	
	public Users getUserById(int id) {
		Users u = userRepository.getUserByID(id);
		return u;
	}
	
	public boolean updateUser(String fullName, String email, String pwd, String phone, int id_role, int id) {
		int count = userRepository.update(fullName, email, pwd, phone, id_role, id);
		return count > 0;
	}
}
