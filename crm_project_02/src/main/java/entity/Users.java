package entity;

//Entity: là nơi khai báo ra các class đặt tên và thuộc tính giống với lại tên bảng trong database.

public class Users {
	int id;
	String email;
	String pwd;
	
	String firstName;
	String lastName;
	String userName;
	String fullName;
	String phone;
	String roleName;
	
	int role_id;
	
	Role role;
	
	public Users() {
	}
	
	
	public Users(int id, String email, String pwd, String fullName, String phone, String roleName) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.fullName = fullName;
		this.phone = phone;
		this.roleName = roleName;
	}

	public Users(int id, String firstName, String lastName, String userName, String fullName, int role_id) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.fullName = fullName;
		this.role_id = role_id;
	}

	public Users(int id, String email, String pwd) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
	}
	
	public Users(int id, Role role, String firstName, String lastName, String userName) {
		this.id = id;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	
}
