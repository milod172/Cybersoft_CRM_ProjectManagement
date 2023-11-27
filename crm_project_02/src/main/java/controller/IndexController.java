package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "indexController", urlPatterns = {"/index", "/404", "/blank"})
public class IndexController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String path = req.getServletPath();
		
		if(path.equals("/index")) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}else if(path.equals("/404")) {
			req.getRequestDispatcher("404.jsp").forward(req, resp);
		}else if(path.equals("/blank")) {
			req.getRequestDispatcher("blank.jsp").forward(req, resp);
		}
		
	}
}
