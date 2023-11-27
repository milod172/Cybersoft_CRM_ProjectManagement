package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Role;
import entity.Users;
import service.UserService;

@WebServlet(name="userController", urlPatterns = {"/user-add","/user","/user-edit"})
public class UserController extends HttpServlet{
	
	private UserService userService = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/user")) {
			List<Users> listUser = new ArrayList<>();
			try {
				listUser = userService.getAllUser();
			}catch (Exception e) {
				System.out.println("Lỗi get All User " + e.getLocalizedMessage());
			}
			req.setAttribute("listUser", listUser);
			req.getRequestDispatcher("user-table.jsp").forward(req, resp);
			
			
		}else if(path.equals("/user-add")) {
			List<Role> list = new ArrayList<>();
			try {
				list = userService.getAllRole();
			}catch (Exception e) {
				System.out.println("Lỗi get All Role " + e.getLocalizedMessage());
			}
			
			req.setAttribute("listRole", list);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			
			
		}else if(path.equals("/user-edit")) {
			List<Role> list = new ArrayList<>();
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			Users user = new Users();
			try {
				user = userService.getUserById(id_user);
				list = userService.getAllRole();
			}catch (Exception e) {
				System.out.println("Lỗi get All Role " + e.getLocalizedMessage());
			}
			
			req.setAttribute("user", user);
			req.setAttribute("listRole", list);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
		}
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		String path = req.getServletPath();
		
		if(path.equals("/user-add")) {
			String fullname = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			int id_role = Integer.parseInt(req.getParameter("role")); 
			boolean isSuccess = false;
			List<Role> list = new ArrayList<>();
			
			try {
				list = userService.getAllRole();
			}catch (Exception e) {
				System.out.println("Lỗi get All Role " + e.getLocalizedMessage());
			}
			
			if(fullname.trim().length() == 0 || email.trim().length() == 0 || password.trim().length() == 0 || phone.trim().length() == 0) {
				req.setAttribute("err", "Hãy đảm bảo tất cả ô bạn nhập vào ký tự đầu tiên không phải là khoảng trắng!");
			}else {
				isSuccess = userService.addUser(id_role, fullname, email, password, phone);
			}
			
			req.setAttribute("listRole", list);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("user-add.jsp").forward(req, resp);
			
			
		}else if(path.equals("/user-edit")) {
			String fullName = req.getParameter("fullname");
			String email = req.getParameter("email");
			String password = req.getParameter("password");
			String phone = req.getParameter("phone");
			int id_role = Integer.parseInt(req.getParameter("role")); 
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			
			boolean isSuccess = false;
			List<Role> list = new ArrayList<>();
			Users user = new Users();
			
			try {
				if(fullName.trim().length() == 0 || email.trim().length() == 0 || password.trim().length() == 0 || phone.trim().length() == 0) {
					req.setAttribute("err", "Hãy đảm bảo tất cả ô bạn nhập vào ký tự đầu tiên không phải là khoảng trắng!");
				}else {
					user = userService.getUserById(id_user);
					isSuccess = userService.updateUser(fullName, email, password, phone, id_role, id_user);
					list = userService.getAllRole();
				}
				
			}catch (Exception e) {
				System.out.println("Cập nhật User không thành công!" + e.getLocalizedMessage());
			}
			
			req.setAttribute("user", user);
			req.setAttribute("listRole", list);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("user-edit.jsp").forward(req, resp);
		}
		
		
	}

}
