package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardDTO;
import vo.CommentsDTO;
import vo.Action;

//글 내용 보기
public class BoardShowAction implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardShowAction 작동");
		HttpSession session = request.getSession();
		
		String userID = null;
		if (session.getAttribute("userID") != null){
		 userID = (String)session.getAttribute("userID");		
		}
		if (userID == null){
		session.setAttribute("messageType", "오류 메시지");
		session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
		ActionForward forward = new ActionForward("login.jsp", true); //true일 경우 redirect방식
		return forward;
		}
		String boardID = null;
		if (request.getParameter("boardID") != null){
			boardID = (String) request.getParameter("boardID");
		}
		if (boardID == null || boardID.equals("")){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "게시물을 선택해주세요.");
			ActionForward forward = new ActionForward("boardView.jsp", true); 
			return forward;
			
		}
		
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO board = boardDAO.getBoard(boardID);
		
		if(board.getBoardAvailable()==0){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "삭제된 게시물입니다.");
			ActionForward forward = new ActionForward("boardView.board", true); 
			return forward;
		}
		
		
		ArrayList<CommentsDTO> commentList  = boardDAO.getComment(boardID);

		boardDAO.hit(boardID);
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("board", board);
		ActionForward forward = new ActionForward("boardShow.jsp", false);//false일 경우 dispatcher방식
		return forward;
		}
		
	}


