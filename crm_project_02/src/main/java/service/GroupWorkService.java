package service;

import java.util.List;

import entity.Project;
import repository.GroupWorkRepository;

public class GroupWorkService {

	private GroupWorkRepository groupWorkRepository = new GroupWorkRepository();
	
	public boolean addProject(String name, String startDate, String endDate) {
		int count = groupWorkRepository.insert(name, startDate, endDate);
		return count > 0;
	}
	
	public List<Project> getAllProject(){
		return groupWorkRepository.findAll();
	}
	
	public Project getProjectById(int id) {
		Project p = groupWorkRepository.getProjectbyID(id);
		return p;
	}
	
	public boolean updateProject(String name, String startDate, String endDate, int id) {
		int count = groupWorkRepository.update(name, startDate, endDate, id);
		return count > 0;
	}
	
	public boolean deleteProject(int id) {
		groupWorkRepository.deleteJobByIdProject(id);
		groupWorkRepository.deletePro_UserByIdProject(id);
		int count = groupWorkRepository.deleteById(id);
		return count > 0;
	}
}
