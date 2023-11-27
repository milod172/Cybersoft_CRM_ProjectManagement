package entity;


public class Project {
	private int id;
	private String name;
	private String startDate;
	private String endDate;
	
	public Project() {
	}

	public Project(String startDate, String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Project(int id, String name, String startDate, String endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
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
	
}
