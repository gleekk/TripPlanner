package board.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.Action;
import vo.ActionForward;

// 게시글 삭제 기능
public class BoardCommentdeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardDeleteAction() 작동");
		HttpSession session = request.getSession();

		
		String boardID = request.getParameter("boardID");
	
		
		int commentsNo =  Integer.parseInt(request.getParameter("commentsNo"));
		
		BoardDAO boardDAO = new BoardDAO();
		boardDAO.deleteComment(commentsNo);
		
		request.setAttribute("boardID", boardID);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "삭제에 성공했습니다.");
		ActionForward forward = new ActionForward("boardShow.board", false);
		return forward;

		

	}

}
