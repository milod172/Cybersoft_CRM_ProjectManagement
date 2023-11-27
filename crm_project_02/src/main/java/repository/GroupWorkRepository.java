package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Project;
import validation.Validation;

public class GroupWorkRepository {
	
	public int insert(String name, String startDate, String endDate) {
		String query = "INSERT INTO Project(name, startDate, endDate) VALUES(?,?,?)";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setDate(2, Date.valueOf(startDate));
			st.setDate(3, Date.valueOf(endDate));
			count = st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Lỗi không thể thêm Dự án " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public List<Project> findAll(){
		List<Project> listProject = new ArrayList<>();
		
		String query = "SELECT * From Project";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				Project j = new Project();
				
				j.setId(rs.getInt(1));
				j.setName(rs.getString(2));
				j.setStartDate(Validation.dateFormatForScreen(rs.getDate(3)));
				j.setEndDate(Validation.dateFormatForScreen(rs.getDate(4)));
				
				listProject.add(j);	
			}
	    
		}catch (Exception e) {
			System.out.println("Lỗi không thể hiển thị Project " + e.getLocalizedMessage());
		}
		
		return listProject;
	}
	
	
	public int update(String name, String startDate, String endDate, int id) {
		String query = "UPDATE Project p "
				+ "SET p.name = ?, p.startDate = ?, p.endDate = ? "
				+ "WHERE p.id = ?";
		int count = 0;
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, name);
			st.setDate(2, Date.valueOf(startDate));
			st.setDate(3, Date.valueOf(endDate));
			st.setInt(4, id);
			
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Cập nhật dự án thất bại! " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public Project getProjectbyID(int id) {
		String query = "SELECT * FROM Project WHERE id = ?";
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Project p = new Project();
				
				p.setId(rs.getInt(1));
				p.setName(rs.getString(2));
				p.setStartDate(Validation.dateFormatForScreen(rs.getDate(3)));
				p.setEndDate(Validation.dateFormatForScreen(rs.getDate(4)));
				
				return p;
			}
			
		}catch (Exception e) {
			System.out.println("Lấy dự án thất bại!" + e.getLocalizedMessage());
		}
		
		return null;
	}
	
	public int deleteById(int id) {
		String query = "DELETE FROM Project p WHERE p.id = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Project thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
	
	public int deleteJobByIdProject(int id) {
		String query = "DELETE FROM Job j WHERE j.id_project = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Job by Project id thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
	
	
	public int deletePro_UserByIdProject(int id) {
		String query = "DELETE FROM Project_Users pu WHERE pu.id_project = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Project_User by Project id thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
}
