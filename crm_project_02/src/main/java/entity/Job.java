package entity;

public class Job {
	int id;
	String name;
	String content;
	String projectName;
	int id_project;
	
	String executor;
	String startDate;
	String endDate;
	String statusName;
	
	Users user;
	Status status;
	Project project;
	
	public Job() {
	}

	public Job(int id, String name, String content, String startDate, String endDate, int id_project) {
		this.id = id;
		this.name = name;
		this.content = content;
		this.id_project = id_project;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	
	
	public Job(int id, String name, String startDate, String endDate, int id_project) {
		this.id = id;
		this.name = name;
		this.id_project = id_project;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Project getProject() {
		return project;
	}

	
	public void setProject(Project project) {
		this.project = project;
	}


	public Job(int id, String name, String content, String executor, String startDate, String endDate, String statusName) {
		this.id = id;
		this.name = name;
		this.projectName = content;
		this.executor = executor;
		this.startDate = startDate;
		this.endDate = endDate;
		this.statusName = statusName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return projectName;
	}

	public void setContent(String content) {
		this.projectName = content;
	}

	public int getId_project() {
		return id_project;
	}

	public void setId_project(int id_project) {
		this.id_project = id_project;
	}

	public String getExecutor() {
		return executor;
	}

	public void setExecutor(String executor) {
		this.executor = executor;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}


	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	
	
}
