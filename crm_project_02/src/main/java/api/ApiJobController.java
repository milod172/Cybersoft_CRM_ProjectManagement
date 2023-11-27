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

import entity.Job;
import payload.BaseResponse;
import service.JobService;

@WebServlet(name = "apiJobController", urlPatterns = {"/api/task" , "/api/task/delete"})
public class ApiJobController extends HttpServlet{
	
	private JobService jobService = new JobService();
	private Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/api/task")) {
			List<Job> listJob = jobService.getAllJob();
			
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage("");		//Toán tử ba ngôi
			response.setData(listJob);
			
			//Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(response);
			
			//Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");	
			out.print(dataJson);
			
			out.flush();
			
		}else if(path.equals("/api/task/delete")) {
			int id_job = Integer.parseInt(req.getParameter("idJob"));
			int id_user = Integer.parseInt(req.getParameter("idUser"));
			boolean isSuccess = jobService.deleteByID(id_job, id_user);
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
