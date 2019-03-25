package gowith.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GowithDAO;
import vo.Action;
import vo.ActionForward;
import vo.GowithDTO;

public class GowithGetLikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("GowithGetLikeAction() 작동");
		
		HttpSession session = request.getSession();
	
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("login.jsp", true);												// redirect방식
			return forward;
		}
		
		
		String pageNumber = "1";
		if(request.getParameter("pageNumber") != null){
			pageNumber = request.getParameter("pageNumber");
		}
		try{
			Integer.parseInt(pageNumber);
		}catch(Exception e){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "페이지 번호가 잘못 되었습니다.");
			response.sendRedirect("boardView.jsp");
		}
		
		int startPage = (Integer.parseInt(pageNumber)/10)*10+1;
		
		if(Integer.parseInt(pageNumber)%10 == 0) startPage -= 10;
		
		int targetPage = 0;

		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("startPage", startPage);
		request.setAttribute("targetPage", targetPage);		
		
		GowithDAO dao=new GowithDAO();
		Vector<GowithDTO> v=dao.getlike(userID);
		
		request.setAttribute("getlist", v);
		request.setAttribute("user", userID);
	
		ActionForward forward = new ActionForward("gowithView.jsp", false);
		
		
		return forward;
	}

}
