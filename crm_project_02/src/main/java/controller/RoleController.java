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
import service.RoleService;


@WebServlet(name="roleController", urlPatterns = {"/role-add","/role", "/role-edit"})
public class RoleController extends HttpServlet{

	private RoleService roleService = new RoleService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//Lấy ra đường dẫn mà người dùng đang gọi
		String path = req.getServletPath();
		
		if(path.equals("/role-add")) {
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			
			
		}else if(path.equals("/role")) {
			List<Role> listRole = new ArrayList<>();
			try {
				listRole = roleService.getAllRole();
					
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			req.setAttribute("listRole", listRole);
			req.getRequestDispatcher("role-table.jsp").forward(req, resp);
			
			
		}else if(path.equals("/role-edit")) {
			int id_role = Integer.parseInt(req.getParameter("id_role"));
			Role role = new Role();
			
			try {
				role = roleService.getRoleById(id_role);
				
			}catch (Exception e) {
				System.out.println("Lỗi update Role " + e.getLocalizedMessage());
			}
			
			req.setAttribute("role", role);
			req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//Nhận tham số nếu có.
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		boolean isSuccess = false;
		String path = req.getServletPath();
		
		if(path.equals("/role-add")) {
			String roleName = req.getParameter("role-name");
			String description = req.getParameter("desc");
		
			if(roleName.trim().length() == 0) {
				req.setAttribute("msg", "Hãy đảm bảo ký tự đầu tiên bạn nhập vào không phải là khoảng trắng");
			}else if(description.trim().length() == 0) {
				req.setAttribute("msg", "Hãy đảm bảo ký tự đầu tiên bạn nhập vào không phải là khoảng trắng");
			}else {
				isSuccess = roleService.addRole(roleName, description);	
			}

			
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("role-add.jsp").forward(req, resp);
			
		}else if(path.equals("/role-edit")) {
			int id = Integer.parseInt(req.getParameter("id_role")); 
			String name = req.getParameter("role-name");
			String description = req.getParameter("desc");
			
			Role role = new Role();
			
			try {
				
				if(name.trim().length() == 0) {
					req.setAttribute("msg", "Hãy đảm bảo ký tự đầu tiên bạn nhập vào không phải là khoảng trắng");
				}else if(description.trim().length() == 0) {
					req.setAttribute("msg", "Hãy đảm bảo ký tự đầu tiên bạn nhập vào không phải là khoảng trắng");
				}else {
					role = roleService.getRoleById(id);
					isSuccess = roleService.updateRole(id, name, description);
				}

			}catch (Exception e) {
				System.out.println("Cập nhật User không thành công!" + e.getLocalizedMessage());
			}
			
			req.setAttribute("role", role);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("role-edit.jsp").forward(req, resp);
		}
	}
}
