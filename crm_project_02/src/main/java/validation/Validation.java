package validation;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	private static DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");	//đầu vào theo định dạng dd/mm/yyyy.
	private static DateTimeFormatter dbFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		//đầu ra để gửi về db định dạng yyyy-mm-dd.
	private static DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");	//đầu ra hiển thị theo định dạng dd-mm-yyyy.
	
	//toLocalDate (để loại bỏ giờ giấc chỉ giữ lại ngày-tháng-năm)
	
	public static boolean isValidDateFormat(String date) {
        String regex = "\\d{2}/\\d{2}/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }
	
	
	//Đổi định dạng ngày từ dd/mm/yyyy thành yyyy-mm-dd để lưu vào database.
	public static String dateFormatForDB(String date) {
		//ép kiểu từ chuỗi định dạng dd/MM/yyyy thành thời gian cục bộ. VD: "01/07/2002" --> 01/07/2002 (thời gian ngoài đời thực)  
		LocalDate localDate = LocalDate.parse(date, inputFormatter); 
		// localDate.format dùng để biến cái thời gian ngoài đời thực về lại thành chuỗi theo format.	
		return localDate.format(dbFormatter);			
		
	}
	
	
	//Đổi định dạng ngày từ yyyy-mm-dd thành dd/mm/yyyy để hiển thị.
	public static String dateFormatForScreen(Date date) {
		LocalDate localDate = date.toLocalDate();
		return localDate.format(outputFormatter);
	}
	
	
	//Kiểm tra đầu vào, ngày kết thúc không thể xảy ra trước ngày bắt đầu
	public static boolean checkTimeLine(String dateStart, String dateEnd) {
		boolean check = false;
		LocalDate localDate1 = LocalDate.parse(dateStart, inputFormatter);
        LocalDate localDate2 = LocalDate.parse(dateEnd, inputFormatter);
        
        if(localDate2.isBefore(localDate1)) {
        	check = true;
        }
        
        return check;
	}
	
	
}
