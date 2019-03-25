package board.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.Action;
import vo.ActionForward;
import vo.BoardDTO;

// 게시글 삭제 기능
public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardDeleteAction() 작동");
		HttpSession session = request.getSession();

		String userID = (String) session.getAttribute("userID");
		String boardID = request.getParameter("boardID");
		if (boardID == null || boardID.equals("")) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			ActionForward forward = new ActionForward("index.user", true);
			return forward;
		}
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO board = boardDAO.getBoard(boardID);
		if (!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.user", true);
			return forward;
		}
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		String prev = boardDAO.getRealFile(boardID);
		int result = boardDAO.delete(boardID);
		if (result == -1) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.user", true);
			return forward;
		} else {
			File prevFile = new File(savePath + "/" + prev);
			if (prevFile.exists()) {
				prevFile.delete();
			}
			session.setAttribute("messageType", "성공 메시지");
			session.setAttribute("messageContent", "삭제에 성공했습니다.");
			ActionForward forward = new ActionForward("boardView.board", true);
			return forward;

		}

	}

}
