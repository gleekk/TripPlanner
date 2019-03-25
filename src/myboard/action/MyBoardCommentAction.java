package myboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import vo.Action;
import vo.ActionForward;

// 게시판으로 이동 및 게시글 뿌려주는 기능
public class MyBoardCommentAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		System.out.println("BoardCommentAction() 작동");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}

		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("login.jsp", true); // true일													// redirect방식
			return forward;
		}

		int myboardID = Integer.parseInt(request.getParameter("myboardID"));
		
		String mybcomment = request.getParameter("mybcomment");
		MyBoardDAO boardDAO = new MyBoardDAO();

		int result =  boardDAO.Comment(userID, myboardID, mybcomment);
		
		if(result == 1){
		request.setAttribute("myboardID", myboardID);
		ActionForward forward = new ActionForward("boardShow.myboard", false);
		return forward;
		}else{
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "댓글 달기에 실패 했습니다.");
			request.setAttribute("myboardID", myboardID);
			ActionForward forward = new ActionForward("boardShow.myboard", false);
			return forward;
		}
	}
}
