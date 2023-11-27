package service;

import java.util.List;

import entity.Role;
import repository.RoleRepository;
import repository.UserRepository;

//Chứa đựng class chuyên đi xử lý logic code cho Controller
//Cách đặt tên RoleControlle => RoleService
//Tên hàm phải đặt ứng với chức năng sẽ làm trên giao diện

public class RoleService {
	
	private RoleRepository roleRepository = new RoleRepository();
	private UserRepository userRepository = new UserRepository();
	
	public boolean addRole(String name, String desc) {
		int count = roleRepository.insert(name, desc);
		return count > 0;
	}
	
	public List<Role> getAllRole(){
		return roleRepository.findAll();
	}
	
	public Role getRoleById(int id) {
		Role role = roleRepository.getRoleByID(id);
		return role;
	}
	
	public boolean updateRole(int id, String name, String description) {
		int count = roleRepository.update(id, name, description);
		return count > 0;
	}
	
	public boolean deleteRole(int id) {
		userRepository.deleteByRoleID(id);
		int count = roleRepository.deleteById(id);
		return count > 0;
	}
}
