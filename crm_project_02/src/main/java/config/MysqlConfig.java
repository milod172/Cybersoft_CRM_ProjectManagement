package config;

import java.sql.Connection;
import java.sql.DriverManager;

//Cấu hình JDBC kết nối tới Server MySQL
public class MysqlConfig {

	public static Connection getConnection() {
		try {
			//Khai báo Driver sử dụng cho JDBC tương ứng với CSDL cần kết nối
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Khai báo Driver sẽ mở kết nối tói CSDL nào
            return DriverManager.getConnection("jdbc:mysql://localhost:3308/layoutcrm","root", "dung1702");
        } catch (Exception ex) {
            //Lỗi xảy ra sẽ chạy vào đây
        	System.out.println("Lỗi kết nối database " + ex.getLocalizedMessage());
        	return null;
        }
	}
}
