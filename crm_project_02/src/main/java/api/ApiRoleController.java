package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Role;
import payload.BaseResponse;
import service.RoleService;

@WebServlet(name = "apiRoleController", urlPatterns = {"/api/role" , "/api/role/delete"})
public class ApiRoleController extends HttpServlet{

	private RoleService roleService = new RoleService();
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/api/role")) {
			
			List<Role> listRole = roleService.getAllRole();
			
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage("");		//Toán tử ba ngôi
			response.setData(listRole);
			
			//Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(response);
			
			//Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");	
			out.print(dataJson);
			
			out.flush();
		}else if(path.equals("/api/role/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = roleService.deleteRole(id);
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage(isSuccess ? "Xóa Thành Công" : "Xóa thất bại");		
			response.setData(isSuccess);
			
			String json = gson.toJson(response);
			
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(json);
		}
	}
}
