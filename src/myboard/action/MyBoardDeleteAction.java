package myboard.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import vo.Action;
import vo.ActionForward;
import vo.MyBoardDTO;

// 게시글 삭제 기능
public class MyBoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("myBoardDeleteAction() 작동");
		HttpSession session = request.getSession();
		String pagename = request.getParameter("pagename");
		String userID = (String) session.getAttribute("userID");
		String myboardID = request.getParameter("myboardID");
		if (myboardID == null || myboardID.equals("")) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "접근할 수 없습니다.");
			ActionForward forward = new ActionForward("index.user", false);
			return forward;
		}
		MyBoardDAO boardDAO = new MyBoardDAO();
		MyBoardDTO board = boardDAO.getBoard(myboardID);
		if (!userID.equals(board.getUserID())) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.user", false);
			return forward;
		}
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		String prev = boardDAO.getRealFile(myboardID);
		int result = boardDAO.delete(myboardID);
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
			
			
			
			
			if(pagename.equals("mypage")){
				ActionForward forward = new ActionForward("myPage.myboard?userID="+userID, true);//false일 경우 dispatcher방식
				return forward;	
			}else{
			
			ActionForward forward = new ActionForward("boardView.myboard", true);
			return forward;
			}
		}

	}

}
