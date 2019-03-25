package myboard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MyBoardDAO;
import vo.ActionForward;
import vo.MyBoardDTO;
import vo.Action;

// 게시판으로 이동 및 게시글 뿌려주는 기능
public class MyBoardCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("myBoardCheckAction() 작동");
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

		String pageNumber = "1";
		if(request.getParameter("pageNumber") != null){
			pageNumber = request.getParameter("pageNumber");
		}
		try{
			Integer.parseInt(pageNumber);
		}catch(Exception e){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "페이지 번호가 잘못 되었습니다.");
			response.sendRedirect("boardView.jsp");
		}
		
		int startPage = (Integer.parseInt(pageNumber)/10)*10+1;
		
		if(Integer.parseInt(pageNumber)%10 == 0) startPage -= 10;
		
		int targetPage = new MyBoardDAO().targetPage(pageNumber, "");
		

		ArrayList<MyBoardDTO> boardList = new MyBoardDAO().getList(pageNumber);
		
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("startPage", startPage);
		request.setAttribute("targetPage", targetPage);

		System.out.println("myBoardCheckAction() 작동");

		ActionForward forward = new ActionForward("myboardView.jsp", false);
		return forward;
	}

}
