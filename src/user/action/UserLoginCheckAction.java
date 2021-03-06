package user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.Action;
import vo.ActionForward;

//로그인 페이지로 이동 기능 
public class UserLoginCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}

		if (userID != null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있는 상태입니다.");
			ActionForward forward = new ActionForward("index.jsp", true); // true일 경우 redirect방식
			return forward;
		} else {

			ActionForward forward = new ActionForward("login.jsp", true);
			return forward;
		}

	}

}
