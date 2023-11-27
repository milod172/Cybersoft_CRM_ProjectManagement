package api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import entity.Users;
import payload.BaseResponse;
import service.UserService;

/*
 * Payload:
 * - response: chứa các class liên quan đến format json trỏ ra cho client.
 * - request: chứa các class liên quan tới format json request (tham số) client truyền lên cho server
 */


@WebServlet(name = "apiUserController", urlPatterns = {"/api/user" , "/api/user/delete"})
public class ApiUserController extends HttpServlet{

		private UserService userService = new UserService();
		//Khởi tạo thư viện GSON để sử dụng.
		private Gson gson = new Gson();
	
		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			String path = req.getServletPath();
			
			if(path.equals("/api/user")) {
				
			List<Users> listUsers = userService.getAllUser();
			
			BaseResponse response = new BaseResponse();
			response.setStatusCode(200);
			response.setMessage("");		//Toán tử ba ngôi
			response.setData(listUsers);
			
			//Chuyển List hoặc mảng về JSON
			String dataJson = gson.toJson(response);
			
			//Trả dữ liệu dạng JSON
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");	
			out.print(dataJson);
			
			out.flush();
			
			}else if(path.equals("/api/user/delete")) {
				int idTest = Integer.parseInt(req.getParameter("idUser"));
				boolean isSuccess = userService.deleteUser(idTest);
				BaseResponse response = new BaseResponse();
				response.setStatusCode(200);
				response.setMessage(isSuccess ? "Xóa Thành Công" : "Xóa thất bại");		//Toán tử ba ngôi
				response.setData(isSuccess);
				
				String json = gson.toJson(response);
				
				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(json);
			}
		}
	
		/*
		 Viết api /api/user/delete
		 Với phương thức là GET
		 Sẽ nhận tham số là id của user
		 Viết câu truy vấn xóa User với id nhận vào
		 Xóa user
		 */
}
