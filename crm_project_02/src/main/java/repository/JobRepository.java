package repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import config.MysqlConfig;
import entity.Job;
import entity.Project;
import entity.Status;
import entity.Users;
import validation.Validation;

public class JobRepository {
	
//	CHưa xong
	public List<Job> findAll(){
		List<Job> listJob = new ArrayList<>();
		
		String query = "SELECT DISTINCT j.id, j.name ,p.name as 'ProjectName' , u.fullName as 'Executor', u.id, j.startDate, j.endDate, s.name as 'Status'\r\n"
				+ "FROM Job j \r\n"
				+ "JOIN Job_Status_Users jsu ON j.id = jsu.id_job \r\n"
				+ "JOIN Project_Users pu ON jsu.id_user = pu.id_user AND pu.id_project = j.id_project \r\n"
				+ "JOIN Users u ON pu.id_user = u.id\r\n"
				+ "JOIN Project p ON pu.id_project = p.id\r\n"
				+ "JOIN Status s ON jsu.id_status = s.id";
		
		Connection conn = MysqlConfig.getConnection();
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Job j = new Job();
				
				j.setId(rs.getInt("id"));
				j.setName(rs.getString("name"));
				
				Project p = new Project();
				p.setName(rs.getString("ProjectName"));
				j.setProject(p);
				
				//Code còn thiếu
				Users u = new Users();
				u.setFullName(rs.getString("Executor"));
				u.setId(rs.getInt("u.id"));
				j.setUser(u);
				
				
				j.setStartDate(Validation.dateFormatForScreen(rs.getDate("startDate")));
				j.setEndDate(Validation.dateFormatForScreen(rs.getDate("endDate")));
				
				
				Status s = new Status();
				s.setName(rs.getString("Status"));
				j.setStatus(s);
				
				listJob.add(j);
			}
			
		}catch (Exception e) {
			System.out.println("Lỗi không thể hiển thị danh sách Job " + e.getLocalizedMessage());
		}
		
		return listJob;
	}
	
	public Job insert(String name, String startDate, String endDate, int id_project) {
	    Job job = null; // Khởi tạo biến để lưu trữ thông tin của công việc mới

	    Connection conn = MysqlConfig.getConnection();
	    String query = "INSERT INTO Job(name, startDate, endDate, id_project) VALUES(?, ?, ?, ?)";

	    try {
	        PreparedStatement st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        st.setString(1, name);
	        st.setDate(2, Date.valueOf(startDate));
	        st.setDate(3, Date.valueOf(endDate));
	        st.setInt(4, id_project);
	        st.executeUpdate();

//ResultSet đóng vai trò như một bảng ghi (đọc tất cả dữ liệu từ đầu đến cuối)
//PreparedStatement.getGeneratedKeys() để lấy giá trị tự động tạo, khi câu truy vấn của ta chứa một cột có giá trị auto_increment
//generatedKeys.next() được sử dụng để duyệt qua các dòng của bảng ghi ResultSet
//Tạo một biến id_job để lưu lại giá trị bảng ghi tại cột thứ (1) là id của Job bên Database (cột mà có giá trị tự động tăng)	        
	        
	        ResultSet generatedKeys = st.getGeneratedKeys();		
	        if (generatedKeys.next()) {
	            int id_job = generatedKeys.getInt(1); 	// Lấy giá trị ID và gán vào id_job
	            job = new Job(id_job, name, startDate, endDate, id_project); // Tạo đối tượng Job mới
	        } else {
	            throw new Exception("Không thể lấy ID của công việc vừa thêm vào.");
	        }
	    } catch (Exception e) {
	        System.out.println("Lỗi thêm mới Job " + e.getLocalizedMessage());
	    }

	    return job; 
	}
	
	
	public int insertProject_User(int id_project, int id_user) {
		int count = 0;
		Connection conn = MysqlConfig.getConnection();
		String query = "INSERT INTO Project_Users(id_user,id_project) VALUES(?,?)";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id_user);
			st.setInt(2, id_project);
			count = st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Lỗi thêm mới Job " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public int insertJob_Status_Users(int id_user, int id_job) {
		int count = 0;
		Connection conn = MysqlConfig.getConnection();
		String query = "INSERT INTO Job_Status_Users (id_user,id_status,id_job) VALUES(?,?,?)";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id_user);
			st.setInt(2, 3);
			st.setInt(3, id_job);
			count = st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Lỗi thêm mới Job " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	public int CountDuplicateJob(String jobName, int userId, int idProject) {
		Connection conn = MysqlConfig.getConnection();
        String query = "SELECT COUNT(*) FROM Job j " +
                       "JOIN Job_Status_Users jsu ON j.id = jsu.id_job " +
                       "JOIN Project_Users pu ON jsu.id_user = pu.id_user AND pu.id_project = j.id_project " +
                       "JOIN Project p ON pu.id_project = p.id " +
                       "WHERE j.name = ? AND jsu.id_user = ? AND p.id = ?";

        try (PreparedStatement st = conn.prepareStatement(query)) {
            st.setString(1, jobName);
            st.setInt(2, userId);
            st.setInt(3, idProject);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); // Trả về số lượng bản ghi thỏa mãn câu truy vấn
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; // Trả về 0 nếu có lỗi hoặc không tìm thấy bản ghi thỏa mãn
    
    }
	
	
	public int update(int id_status, int id_job) {
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		String query = "UPDATE Job_Status_Users jsu "
				+ "SET jsu.id_status = ? "
				+ "WHERE jsu.id_job = ? ";
		try {
			PreparedStatement st = conn.prepareStatement(query);
	        st.setInt(1, id_status);
			st.setInt(2, id_job);
	        count = st.executeUpdate();
			
		}catch (Exception e) {
			System.out.println("Update công việc thất bại! " + e.getLocalizedMessage());
		}
		
		return count;
	}
	
	
	public Job getJobbyID(int id) {
		String query = "SELECT p.name, j.id, j.name, j.content, j.startDate, j.endDate, j.id_project, jsu.id_status  FROM Job j "
				+ "JOIN Job_Status_Users jsu ON j.id = jsu.id_job "
				+ "JOIN Project_Users pu ON jsu.id_user = pu.id_user AND pu.id_project = j.id_project "
				+ "JOIN Project p ON pu.id_project = p.id "
				+ "WHERE j.id = ?";
		
		Connection conn = MysqlConfig.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				int job_id = rs.getInt("j.id");
				String name = rs.getString("j.name");
                String content = rs.getString("j.content");
                String startDate = Validation.dateFormatForScreen(rs.getDate("j.startDate"));
                String endDate = Validation.dateFormatForScreen(rs.getDate("j.endDate"));
          		int id_project = rs.getInt("id_project");
          		int status_id = rs.getInt("jsu.id_status"); 
          		String project_name = rs.getString("p.name");
          		
				Job j = new Job(job_id, name, content, startDate, endDate, id_project);	
				Status status = new Status();
				status.setId(status_id);
				j.setStatus(status);
				
				Project p = new Project();
				p.setName(project_name);
				j.setProject(p);
				
				return j;
			}
		}catch (Exception e) {
			System.out.println("Lỗi không tìm thấy Job đó");
		}
		
		return null;
	}
	
	
	public int deleteJob_Status_UsersById(int id) {
		String query = "DELETE FROM Job_Status_Users jsu WHERE jsu.id_job = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Job_Status_Users by id_job thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
	
	
	public int deleteById(int id) {
		String query = "DELETE FROM Job j WHERE j.id = ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Job by Job id thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
	
	
	public int deleteProject_UsersById(int id) {
		String query = "DELETE FROM Project_Users pu WHERE pu.id_user =  ?";
		Connection conn = MysqlConfig.getConnection();
		int count = 0;
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, id);
			count = st.executeUpdate();
		}catch (Exception e) {
			System.out.println("Xóa Job_Status_Users by id_job thất bại! " + e.getLocalizedMessage());
		}
		return count;
	}
}
