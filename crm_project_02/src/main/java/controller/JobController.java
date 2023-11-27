package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Job;
import entity.Project;
import entity.Users;
import service.JobService;
import validation.Validation;

@WebServlet(name="jobController", urlPatterns = {"/task-add","/task"})
public class JobController extends HttpServlet{
	private JobService jobService = new JobService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/task-add")) {
			List<Users> listUser = new ArrayList<>();
			List<Project> listProject = new ArrayList<>();
			try {
				listUser = jobService.getAllUser();
				listProject = jobService.getAllProject();
			}catch (Exception e) {
				System.out.println("Lỗi không lấy được User " + e.getLocalizedMessage());
			}
			req.setAttribute("listProject", listProject);		
			req.setAttribute("listUser", listUser);
			req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			
		}else if(path.equals("/task")) {
			List<Job> listJob = new ArrayList<>();
			try {
				listJob = jobService.getAllJob();
					
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			req.setAttribute("listJob", listJob);
			req.getRequestDispatcher("task.jsp").forward(req, resp);
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		
		List<Users> listUser = new ArrayList<>();
		List<Project> listProject = new ArrayList<>();
		try {
			listUser = jobService.getAllUser();
			listProject = jobService.getAllProject();
		}catch (Exception e) {
			System.out.println("Lỗi không hiển thị danh sách " + e.getLocalizedMessage());
		}
		
		int id_project = Integer.parseInt(req.getParameter("project"));
		String name = req.getParameter("name");
		int id_user = Integer.parseInt(req.getParameter("user"));
		String dateStart = req.getParameter("startDate");
		String dateEnd = req.getParameter("endDate");
		boolean isSuccess = false;
		
		
		if(name.trim().length() == 0) {
			req.setAttribute("err", "Vui lòng không nhập vào khoảng trắng!");
		}else if(!Validation.isValidDateFormat(dateStart)) {
			req.setAttribute("StartErr", "Vui lòng nhập đúng định dạng!");	
		}else if(!Validation.isValidDateFormat(dateEnd)) {
			req.setAttribute("EndErr", "Vui lòng nhập đúng định dạng!");	
		}else {
			  
	        if(Validation.checkTimeLine(dateStart, dateEnd)) {
	        	req.setAttribute("ErrTimeLine", "Ngày kết thúc không thể xảy ra trước ngày bắt đầu!");
	        }else {
	        	
	        	String startDate = Validation.dateFormatForDB(dateStart);
	        	String endDate = Validation.dateFormatForDB(dateEnd);
	        	if(jobService.isDuplicateJob(name, id_user, id_project)) {
	        		req.setAttribute("duplicate", "Không thể giao cùng một công việc cho một người trong cùng một project");
	        	}else {
	        		isSuccess = jobService.addJob(name, startDate, endDate, id_project, id_user);
	        	}
	        
	        }
	        
	        
		}	
		req.setAttribute("listUser", listUser);
		req.setAttribute("listProject", listProject);
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("task-add.jsp").forward(req, resp);
	}
	
}
