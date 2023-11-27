package service;

import java.util.List;

import entity.Job;
import entity.Project;
import entity.Users;
import repository.GroupWorkRepository;
import repository.JobRepository;
import repository.UserRepository;

public class JobService {
	private JobRepository jobRepository = new JobRepository();
	private UserRepository userRepository = new UserRepository();
	private GroupWorkRepository groupWorkRepository = new GroupWorkRepository();
	
	public List<Job> getAllJob(){
		return jobRepository.findAll();
	}
	
	public List<Users> getAllUser(){
		return userRepository.findAll();
	}
	
	public List<Project> getAllProject(){
		return groupWorkRepository.findAll();
	}
	
	public boolean addJob(String name, String startDate, String endDate, int id_project, int id_user) {
	    Job newJob = jobRepository.insert(name, startDate, endDate, id_project);

	    if (newJob != null) {
	        jobRepository.insertProject_User(id_project, id_user);
	        int count = jobRepository.insertJob_Status_Users(id_user, newJob.getId()); // Truyền id_job vào đây

	        if (count <= 0) {
	            System.out.println("Thêm công việc thất bại!");
	            return false;
	        }

	        System.out.println("Thêm công việc thành công!");
	        return true;
	    } else {
	        System.out.println("Thêm công việc thất bại!");
	        return false;
	    }
	}
	
	public boolean isDuplicateJob(String jobName, int userId, int idProject) {
		int count = jobRepository.CountDuplicateJob(jobName, userId, idProject);
		return count > 0; 
	}
	
	public boolean updateJob(int id_status, int id_job) {
		int count = jobRepository.update(id_status, id_job);
		return count > 0;
	}
	
	public Job getJob(int id) {
		Job j = jobRepository.getJobbyID(id);
		return j;
	}
	
	public boolean deleteByID(int id_job, int id_user) {
		jobRepository.deleteJob_Status_UsersById(id_job);
		jobRepository.deleteProject_UsersById(id_user);
		int count = jobRepository.deleteById(id_job);
		return count > 0;
	}
	
	
}
