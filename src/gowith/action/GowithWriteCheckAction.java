package gowith.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import vo.Action;
import vo.ActionForward;

public class GowithWriteCheckAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		request.setCharacterEncoding("UTF-8");// 전달 받은 request 정보 한글처리
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardWriteCheckAction 작동");
		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("login.jsp", true);
			return forward;
		}

	
		UserDAO dao=new UserDAO();
		vo.UserDTO user =dao.getUser(userID);
		String profilephoto=dao.getProfile(userID);
		
		request.setAttribute("user", user);
		request.setAttribute("profilephoto", profilephoto);

		ActionForward forward = new ActionForward("gowithWrite.jsp", false);
		return forward;

	}
	
	

}
