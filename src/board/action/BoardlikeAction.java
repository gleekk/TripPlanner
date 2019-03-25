package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.Action;

// 게시판으로 이동 및 게시글 뿌려주는 기능
public class BoardlikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardlikeAction() 작동");
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

		int boardID = 0;
		if (request.getParameter("boardID") != null){
			boardID = Integer.parseInt(request.getParameter("boardID"));
		}
		
		//중복 확인
		BoardDAO boardDAO = new BoardDAO();
		int result = boardDAO.getcomentcheck(boardID, userID);
		System.out.println("reuslt :: "+ result);
		if(result == -1){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "중복 추천 할 수 없습니다.");			
			request.setAttribute("boardID", boardID);
			ActionForward forward = new ActionForward("boardShow.board", false);
			return forward;
			
		}else if(result == 1){
			//추천수 증가 
			boardDAO.getcomentadd(boardID);
			session.setAttribute("messageType", "성공 메시지");
			session.setAttribute("messageContent", "추천에 성공했습니다.");
			request.setAttribute("boardID", boardID);
			ActionForward forward = new ActionForward("boardShow.board", false);
			return forward;

		}else{
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "데이터베이스 오류입니다.");			
			request.setAttribute("boardID", boardID);
			ActionForward forward = new ActionForward("boardShow.board", false);
			return forward;

		}


	}

}
