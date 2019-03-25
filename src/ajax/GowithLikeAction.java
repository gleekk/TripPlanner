package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.GowithDAO;

public class GowithLikeAction {

	public int execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("GowithLikeAction들어왔다");
		
		int no=Integer.parseInt(request.getParameter("no"));
		
		HttpSession session = request.getSession();	
		
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		GowithDAO dao=new GowithDAO();
		int result=dao.likecheck(no, userID);
		
		return result;
	}
	
}
