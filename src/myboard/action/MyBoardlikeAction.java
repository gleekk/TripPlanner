package myboard.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import vo.Action;
import vo.ActionForward;

// 게시판으로 이동 및 게시글 뿌려주는 기능
public class MyBoardlikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("myBoardlikeAction() 작동");
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

		int myboardID = 0;
		if (request.getParameter("myboardID") != null){
			myboardID = Integer.parseInt(request.getParameter("myboardID"));
		}
		String myuserID = null;
		if (request.getParameter("myuserID") != null){
			myuserID = request.getParameter("myuserID"); // 누구의 mypage
		}
		//중복 확인
		MyBoardDAO boardDAO = new MyBoardDAO();
		int result = boardDAO.getcomentcheck(myboardID, userID);
		request.setAttribute("myboardID", myboardID);

		String pagename = null; // 페이지 구분
		if (request.getParameter("pagename") != null){
			pagename = request.getParameter("pagename"); // 누구의 mypage
		}
		
		System.out.println("pagename :: "+ pagename);

		
		
		if(result == -1){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "중복 추천 할 수 없습니다.");			
			if(pagename.equals("mypage")){
				ActionForward forward = new ActionForward("myPage.myboard?userID="+myuserID, false);//false일 경우 dispatcher방식
				return forward;	
			}else{
			ActionForward forward = new ActionForward("boardShow.myboard", false);//false일 경우 dispatcher방식
			return forward;
			}
			
		}else if(result == 1){
			//추천수 증가 
			boardDAO.getcomentadd(myboardID);
			session.setAttribute("messageType", "성공 메시지");
			session.setAttribute("messageContent", "추천에 성공했습니다.");
			if(pagename.equals("mypage")){
				ActionForward forward = new ActionForward("myPage.myboard?userID="+myuserID, false);//false일 경우 dispatcher방식
				return forward;	
			}else{
			ActionForward forward = new ActionForward("boardShow.myboard", false);//false일 경우 dispatcher방식
			return forward;
			}

		}else{
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "데이터베이스 오류입니다.");			
			
			if(pagename.equals("mypage")){
				ActionForward forward = new ActionForward("myPage.myboard?userID="+myuserID, false);//false일 경우 dispatcher방식
				return forward;	
			}else{
			ActionForward forward = new ActionForward("boardShow.myboard", false);//false일 경우 dispatcher방식
			return forward;
			}

		}


	}

}
