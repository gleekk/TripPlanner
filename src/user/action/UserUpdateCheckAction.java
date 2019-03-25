package user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import vo.Action;
import vo.ActionForward;
import vo.UserDTO;

// 회원정보 수정 페이지로 이동
public class UserUpdateCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");

			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}

		UserDTO user = new UserDAO().getUser(userID);

		request.setAttribute("user", user);

		ActionForward forward = new ActionForward("update.jsp", false);
		return forward;

	}

}
