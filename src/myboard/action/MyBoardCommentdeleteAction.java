package myboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import vo.Action;
import vo.ActionForward;

// 게시글 삭제 기능
public class MyBoardCommentdeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("myBoardDeleteAction() 작동");
		HttpSession session = request.getSession();

		
		String myboardID = request.getParameter("myboardID");
	
		
		int mycommentsNo =  Integer.parseInt(request.getParameter("mycommentsNo"));
		
		MyBoardDAO boardDAO = new MyBoardDAO();
		boardDAO.deleteComment(mycommentsNo);
		
		request.setAttribute("myboardID", myboardID);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "삭제에 성공했습니다.");
		ActionForward forward = new ActionForward("boardShow.myboard", false);
		return forward;

		

	}

}
