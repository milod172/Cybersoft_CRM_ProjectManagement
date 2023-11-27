package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Project;
import service.GroupWorkService;
import validation.Validation;

@WebServlet(name="groupWorkController",  urlPatterns = {"/group", "/group-add", "/group-edit"})

public class GroupworkController extends HttpServlet{
	
	private GroupWorkService groupWorkService = new GroupWorkService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/group-add")) {
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			
		}else if(path.equals("/group")) {
			List<Project> listProject = new ArrayList<>();
			try {
				listProject = groupWorkService.getAllProject();
			}catch (Exception e) {
				System.out.println("Lỗi get All Project " + e.getLocalizedMessage());
			}
				
		req.setAttribute("listProject", listProject);
		req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
		
		
		}else if(path.equals("/group-edit")){
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			Project project = new Project();
			
			try {
				project = groupWorkService.getProjectById(id_project);
				
				
			}catch (Exception e) {
				System.out.println("Không thể cập nhật Project! " + e.getLocalizedMessage());
			}
			
			req.setAttribute("project", project);
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		String path = req.getServletPath();
		
		if(path.equals("/group-add")) {
			String name = req.getParameter("name");
			String dateStart = req.getParameter("startDate");
			String dateEnd = req.getParameter("endDate");
			boolean isSuccess = false;
			String err="";
			
			if(name.trim().length() == 0) {
				err = "Vui lòng không nhập vào khoảng trắng!";	
				req.setAttribute("err", err);
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
		       
		        	isSuccess = groupWorkService.addProject(name, startDate, endDate);	
		        }
			
			}
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
			
		}else if(path.equals("/group-edit")){
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			String name = req.getParameter("name");
			String dateStart = req.getParameter("startDate");
			String dateEnd = req.getParameter("endDate");
			boolean isSuccess = false;
			String err="";
			Project project = new Project();
			
			if(name.trim().length() == 0) {
				err = "Vui lòng không nhập vào khoảng trắng!";	
				req.setAttribute("err", err);
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
		       
		        	project = groupWorkService.getProjectById(id_project);
		        	isSuccess = groupWorkService.updateProject(name, startDate, endDate, id_project);	
		        }
			
			}
			req.setAttribute("project", project);
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("groupwork-edit.jsp").forward(req, resp);
			
			
		}

	}
	
}
