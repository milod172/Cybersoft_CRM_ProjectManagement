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
import entity.Status;
import service.JobService;
import service.ProfileService;

@WebServlet(name = "profileController", urlPatterns = {"/profile", "/profile-edit"})
public class ProfileController extends HttpServlet{
	private JobService jobService = new JobService();
	private ProfileService profileService = new ProfileService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		if(path.equals("/profile-edit")) {
			List<Status> listStatus = new ArrayList<>();
			int id_job = Integer.parseInt(req.getParameter("id_job")); 
			Job j = new Job();
			 
			try {
				j = jobService.getJob(id_job);
				listStatus = profileService.getAllStatus() ;
				System.out.println("job.status.id = " + j.getStatus().getId());	
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			req.setAttribute("job", j);
			req.setAttribute("listStatus", listStatus);
			req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
			
		}else if(path.equals("/profile")) {
			List<Job> listJob = new ArrayList<>();
			try {
				listJob = jobService.getAllJob();
					
			}catch(Exception e) {
				System.out.println(e.getLocalizedMessage());
			}
			
			req.setAttribute("listJob", listJob);
			req.getRequestDispatcher("profile.jsp").forward(req, resp);
		}
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		int id_status =  Integer.parseInt(req.getParameter("id_status"));
		int id_job = Integer.parseInt(req.getParameter("id_job"));
		boolean isSuccess = false;
		
		List<Status> listStatus = new ArrayList<>();
		Job j = new Job();
		try {
			j = jobService.getJob(id_job);
			listStatus = profileService.getAllStatus() ;
			isSuccess = jobService.updateJob(id_status, id_job);	
		
			
		}catch(Exception e) {
			System.out.println("Lỗi không thể lấy Status " + e.getLocalizedMessage());
		}

		req.setAttribute("job", j);
		req.setAttribute("listStatus", listStatus);
		req.setAttribute("isSuccess", isSuccess);
		req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
	}
}
