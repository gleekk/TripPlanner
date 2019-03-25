package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import dao.UserDAO;
import vo.ActionForward;
import vo.BoardDTO;
import vo.UserDTO;
import vo.Action;

// 글 수정 페이지로 이동 및 boardID에 해당하는 정보 가져오기 
public class BoardUpdateCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardUpdateCheckAction 작동");

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
		String boardID = request.getParameter("boardID");

		if (boardID == null || boardID.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}

		BoardDAO boardDAO = new BoardDAO();
		BoardDTO board = boardDAO.getBoard(boardID);

		request.setAttribute("board", board);
		request.setAttribute("boardID", boardID);
		request.setAttribute("user", user);

		ActionForward forward = new ActionForward("boardUpdate.jsp", false);
		return forward;

	}

}
