package service;

import java.util.List;

import entity.Status;
import repository.ProfileRepository;

public class ProfileService {
	private ProfileRepository profileRepository = new ProfileRepository();
	
	public List<Status> getAllStatus(){
		return profileRepository.findAll();
	}
}
