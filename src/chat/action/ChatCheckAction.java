package chat.action;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import vo.Action;
import vo.ActionForward;

// 선택한 아이디의 회원과 채팅하기  
public class ChatCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		String toID = null;
		if (request.getParameter("toID") != null) {
			toID = (String) request.getParameter("toID");
		}

		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}
		if (toID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "대화 상대가 지정되지 않았습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}
		if (userID.equals(URLDecoder.decode(toID, "UTF-8"))) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "자기 자신에게는 쪽지를 보낼수 없습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}

		String fromProfile = new UserDAO().getProfile(userID);
		String toProfile = new UserDAO().getProfile(toID);

		request.setAttribute("fromProfile", fromProfile);
		request.setAttribute("toProfile", toProfile);
		request.setAttribute("toID", toID);

		ActionForward forward = new ActionForward("chat.jsp", false);
		return forward;

	}

}
