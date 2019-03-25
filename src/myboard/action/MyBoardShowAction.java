package myboard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.MyBoardDTO;
import vo.MyCommentsDTO;
import vo.PlanDTO;

//글 내용 보기
public class MyBoardShowAction implements Action{
	
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
		String myboardID = null;
		if (request.getParameter("myboardID") != null){
			myboardID = (String) request.getParameter("myboardID");
		}
		System.out.println("myboardID :: "+myboardID);
		
		if (myboardID == null || myboardID.equals("")){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "게시물을 선택해주세요.");
			ActionForward forward = new ActionForward("myboardView.jsp", false); 
			return forward;
			
		}
		
		MyBoardDAO boardDAO = new MyBoardDAO();
		MyBoardDTO board = boardDAO.getBoard(myboardID);
		
		if(board.getMyboardAvailable()==0){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "삭제된 게시물입니다.");
			ActionForward forward = new ActionForward("boardView.myboard", false); 
			return forward;
		}
		
		
		int planNo = Integer.parseInt(board.getMyplan());
		
		if(planNo != 0){
			PlanDTO plan = new TripDAO().getplan(planNo);

			request.setAttribute("plan", plan);
			
			
		}
		
	
		
	

		
		ArrayList<MyCommentsDTO> commentList  = boardDAO.getComment(myboardID);
		boardDAO.hit(myboardID);
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("board", board);
		ActionForward forward = new ActionForward("myboardShow.jsp", false);//false일 경우 dispatcher방식
		return forward;
		}
		
	}


