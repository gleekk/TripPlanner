package user.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import vo.Action;
import vo.ActionForward;

// 로그인 기능
public class UserLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		if (userID == null || userID.equals("") || userPassword == null || userPassword.equals("")) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "모든 내용을 입력 해주세요.");
			ActionForward forward = new ActionForward("login.jsp", true);
			return forward;
		}

		int result = new UserDAO().login(userID, userPassword);
		ActionForward forward = null;

		if (result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "성공 메시지");
			request.getSession().setAttribute("messageContent", "로그인에 성공했습니다.");
			forward = new ActionForward("index.jsp", true);
			return forward;
		} else if (result == 2) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "비밀번호를 다시 확인 하세요.");
			forward = new ActionForward("login.jsp", true);
			return forward;
		} else if (result == 0) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "아이디가 존재하지 않습니다.");
			forward = new ActionForward("login.jsp", true);
			return forward;
		} else if (result == -1) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "데이터 베이스 오류가 발생 하였습니다.");
			forward = new ActionForward("login.jsp", true);
			return forward;
		} else {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "알 수 없는 오류가 발생 하였습니다.");

			forward = new ActionForward("login.jsp", true);
			return forward;
		}
	}

}
