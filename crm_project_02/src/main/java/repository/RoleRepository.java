package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Role;

//Chứa toàn bộ câu truy vấn liên quan đến bảng Role
public class RoleRepository {
	
	public int insert(String name, String desc) {
		int count=0;
		String query = "INSERT INTO `Role` (name, description) VALUES (?,?)";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, desc);
			
			count = st.executeUpdate();
			
		}catch (SQLException e) {
			System.out.println("Lỗi thêm role " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	//Đối với câu Select tên hàm sẽ bắt đầu bằng chữ find
	//Nếu có điều kiện where : by
	public List<Role> findAll(){
		String query = "Select * From Role";
		Connection conn = MysqlConfig.getConnection();
		List<Role> list = new ArrayList<>();
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Role r = new Role();
				
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				r.setDescription(rs.getString("description"));
				
				list.add(r);
			}
			
		}catch (Exception e) {
			System.out.println("Lỗi hiển thị Role " + e.getLocalizedMessage());
		}
		return list;
		
	}
	
	public Role getRoleByID(int id) {
		String query = "Select * From Role WHERE id = ?";
		Connection conn = MysqlConfig.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {		
				Role r = new Role();
				
				r.setId(rs.getInt("id"));
				r.setName(rs.getString("name"));
				r.setDescription(rs.getString("description"));
				
				return r;
			}
			
		}catch (Exception e) {
			System.out.println("Lỗi không lấy được Role! " + e.getLocalizedMessage());
		}
		return null;
	}
	
	public int update(int id, String name, String description) {
		int count = 0;
		String query = "UPDATE `Role` r "
				+ "SET  r.name = ? ,r.description = ? "
				+ "WHERE r.id = ?";
		
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setString(2, description);
			st.setInt(3, id);
			
			count = st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Lỗi không thể update Role! " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	public int deleteById(int id) {
		String query = "DELETE FROM Role r where r.id = ?";
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
}
