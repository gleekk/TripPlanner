package myboard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import dao.TripDAO;
import dao.UserDAO;
import vo.Action;
import vo.ActionForward;
import vo.MyBoardDTO;
import vo.PlanDTO;
import vo.UserDTO;

// 글 수정 페이지로 이동 및 boardID에 해당하는 정보 가져오기 
public class MyBoardUpdateCheckAction implements Action {

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
		String myboardID = request.getParameter("myboardID");

		if (myboardID == null || myboardID.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}

		MyBoardDAO boardDAO = new MyBoardDAO();
		MyBoardDTO board = boardDAO.getBoard(myboardID);
		
		ArrayList<PlanDTO> planList = new TripDAO().bringplan(userID);
		
		request.setAttribute("planList", planList);

		request.setAttribute("board", board);
		request.setAttribute("myboardID", myboardID);
		request.setAttribute("user", user);

		String pagename = request.getParameter("pagename");
		request.setAttribute("pagename", pagename);

		ActionForward forward = new ActionForward("myboardUpdate.jsp?pagename="+pagename, false);
		return forward;

	}

}
