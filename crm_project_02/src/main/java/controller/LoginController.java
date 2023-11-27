package controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import config.MysqlConfig;
import entity.Users;

/* package:
 * - Controller: Là nơi chứa toàn bộ file liên quan tới khai báo đường dẫn và xử lý
 * 
 * Tính năng login:
 * 		Bước 1: Lấy dữ liệu người dùng nhập vào ô email và password
 * 		Bước 2: Viết một câu query kiểm tra email và password người dùng nhập vào có 
 * 		tồn tại trong database hay không.
 * 		Bước 3: Dùng JDBC kết nối tới CSDL và truyền câu query ở bước 2 cho CSDL thực thi
 * 		Bước 4: Kiểm tra dữ liệu. Nếu có dữ liệu thì là đăng nhập thành công và ngược lại
 * 		là đăng nhập thất bại. 
 * 
*/
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Cookie cookie = new Cookie("hoten", URLEncoder.encode("Nguyễn Văn A","UTF-8"));
		cookie.setMaxAge(5*60);
		
//		yêu cầu client tạo cookie
		resp.addCookie(cookie);
		
		req.getRequestDispatcher("login.html").forward(req, resp);
	}
	
	//user-add
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//Bước 1
		String email = req.getParameter("email");
		String pwd = req.getParameter("password");
		
		//Bước 2 
		//? đại diện cho tham số sẽ được truyền vào khi sử dụng JDBC
		String query = "SELECT * FROM Users u \r\n"
				+ "WHERE u.email = ? AND u.pwd = ?";
		
		//Mở kết nối tới CSDL
		Connection conn = MysqlConfig.getConnection();
		
		try {
			//Chuẩn bị câu query truyền xuống cho CSDL thông qua PrepareStatement
			PreparedStatement  st = conn.prepareStatement(query);
			
			//Gán giá trị cho tham số trong câu query có dấu ?
			st.setString(1, email);
			st.setString(2, pwd);
			
			//Thực thi câu query và lấy kết quả
			/*
			 * executeUpdate: Nếu câu query khác SELECT => INSERT, UPDATE, DELETE
			 * executeQuery: Nếu câu query là Select thì xài
			 */
			ResultSet resultSet = st.executeQuery();
			List<Users> listUser = new ArrayList<>();
			while(resultSet.next()) {	//Khi nào mà ResultSet còn qua dòng tiếp theo được thì làm
				//Duyệt qua từng dòng dữ liệu query được trong database.
				Users users = new Users();
				
				//Lấy dữ liệu từ cột duyệt được và lưu vào thuộc tính của đối tượng users
				users.setId(resultSet.getInt("id"));
				users.setEmail(resultSet.getString("email"));  //Tên cột trong database
				listUser.add(users);
			}
			
			if(listUser.size() > 0) {
				System.out.println("Đăng nhập thành công");
			}else {
				System.out.println("Đăng nhập thất bại");
			}
			
			
		}catch(SQLException e) {
			System.out.println("Lỗi thực thi truy vấn" + e.getLocalizedMessage());
		}
		
	}
}
