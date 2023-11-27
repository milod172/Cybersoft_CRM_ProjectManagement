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

import entity.Project;
import payload.BaseResponse;
import service.GroupWorkService;


@WebServlet(name = "apiGroupworkController", urlPatterns = {"/api/group" , "/api/group/delete"})
	public class ApiGroupworkController extends HttpServlet{
	
		private GroupWorkService groupWorkService = new GroupWorkService();
		private Gson gson = new Gson();
		
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String path = req.getServletPath();
			
			if(path.equals("/api/group")) {
				List<Project> listProject = groupWorkService.getAllProject();
				
				BaseResponse response = new BaseResponse();
				response.setStatusCode(200);
				response.setMessage("");		//Toán tử ba ngôi
				response.setData(listProject);
				
				//Chuyển List hoặc mảng về JSON
				String dataJson = gson.toJson(response);
				
				//Trả dữ liệu dạng JSON
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");	
				out.print(dataJson);
				
				out.flush();
				
			}else if(path.equals("/api/group/delete")) {
				int id = Integer.parseInt(req.getParameter("id"));
				boolean isSuccess = groupWorkService.deleteProject(id);
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
