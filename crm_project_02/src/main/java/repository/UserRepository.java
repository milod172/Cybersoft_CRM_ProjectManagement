package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Role;
import entity.Users;



public class UserRepository {

	public int insert(int id_role, String fullname, String email, String password, String phone) {
		int count = 0;
		String query = "INSERT INTO Users(fullName, email, pwd, phone, id_role) VALUES (?, ?, ?, ?, ?)";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, fullname);
			st.setString(2, email);
			st.setString(3, password);
			st.setString(4, phone);
			st.setInt(5, id_role);
			
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Lỗi thêm User " + e.getLocalizedMessage());
		}
		
		return count;
			
	}
//	String query = "SELECT u.id, u.firstName, u.lastName, u.userName, r.name  as 'Role'\r\n"
//	+ "From Users u \r\n"
//	+ "JOIN `Role` r ON u.id_role = r.id ;";
	
	public List<Users> findAll(){
		List<Users> listUser = new ArrayList<>();

		String query = "SELECT u.id, u.firstName, u.lastName, u.fullName, u.userName, r.name  as 'Role'\r\n"
		+ "From Users u \r\n"
		+ "JOIN `Role` r ON u.id_role = r.id";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Users u = new Users();
				
				u.setId(rs.getInt("id"));
	            u.setFirstName(rs.getString("firstName"));
	            u.setLastName(rs.getString("lastName"));
	            u.setUserName(rs.getString("userName"));
	            u.setFullName(rs.getString("fullName"));
	            
	            Role role = new Role();
	            role.setName(rs.getString("Role"));		//Cái tham số mình truyền trong đây là cái cột 'Role' mà mình dùng
	            u.setRole(role);
	            
	            listUser.add(u);
			}
	    
		}catch (Exception e) {
			System.out.println("Lỗi không thể hiển thị User " + e.getLocalizedMessage());
		}
		
		return listUser;
	}
	
	public int deleteById(int id) {
		String query = "DELETE FROM Users u where u.id = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			
		}
		return count;
	}
	
	public int update(String fullName, String email, String pwd, String phone, int id_role, int id) {
		int count = 0;
		String query = "UPDATE Users u "
				+ "SET u.fullName = ?, u.email = ? , u.pwd = ?, u.phone = ?, u.id_role = ? "
				+ "WHERE u.id = ? ";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, fullName);
			st.setString(2, email);
			st.setString(3, pwd);
			st.setString(4, phone);
			st.setInt(5, id_role);
			st.setInt(6, id);
			
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Update User thất bại! " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
		public Users getUserByID(int id) {
		String query = "SELECT u.id, u.email, u.pwd, u.fullName, u.phone, r.id, r.name FROM Users u "
				+ "JOIN `Role` r ON u.id_role = r.id "
				+ "WHERE u.id = ?";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				String email = rs.getString("u.email");
				String pwd	= rs.getString("u.pwd");
				String fullName	= rs.getString("u.fullName");
				String phone	= rs.getString("u.phone");
				String roleName = rs.getString("r.name");
				int id_role = rs.getInt("r.id");
				int u_id = rs.getInt("u.id");
				
				Users u = new Users(u_id, email, pwd, fullName, phone, roleName); 
				Role role = new Role();
		        role.setName(roleName);	
		        role.setId(id_role);
		        u.setRole(role);
				
				return u;
			}
		}catch (Exception e) {
			System.out.println("Lỗi không tìm thấy User by ID " + e.getLocalizedMessage());
		}
		
		return null;
	}
		
		public int deleteByRoleID(int id) {
			int count = 0;
			String query = "DELETE FROM Users u WHERE u.id_role = ?";
			Connection conn = MysqlConfig.getConnection();
			
			try {
				PreparedStatement st = conn.prepareStatement(query);
				st.setInt(1, id);
				count = st.executeUpdate();
				
			}catch (Exception e) {
				System.out.println("Xóa id_role của User thất bại! " + e.getLocalizedMessage());
			}
			
			return count;
		}
}


 	
	
 
 
