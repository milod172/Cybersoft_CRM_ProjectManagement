package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.MysqlConfig;
import entity.Status;

public class ProfileRepository {
	public List<Status> findAll(){
		List<Status> list = new ArrayList<>();
		String query = "SELECT * FROM Status";
		
		Connection conn = MysqlConfig.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Status s = new Status();
				s.setId(rs.getInt("id"));
				s.setName(rs.getString("name"));
				
				list.add(s);
			}	
		}catch (Exception e) {
			System.out.println("Lỗi lấy danh sách Status " + e.getLocalizedMessage());
		}
		
		return list;
	}
}
