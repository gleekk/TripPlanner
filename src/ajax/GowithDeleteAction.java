package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GowithDAO;

public class GowithDeleteAction {
	
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("GowithDeleteAction() 작동");
		
		int no=Integer.parseInt(request.getParameter("no"));
		System.out.println(no);
		
		GowithDAO dao=new GowithDAO();
		dao.delete(no);
		
		return "1";
	}

}
