package myboard.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GowithDAO;
import dao.MyBoardDAO;
import dao.UserDAO;
import vo.Action;
import vo.ActionForward;
import vo.GowithDTO;
import vo.MyBoardDTO;
import vo.UserDTO;

// 게시판으로 이동 및 게시글 뿌려주는 기능
public class MyPageCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("MyPageCheckAction() 작동");
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

		userID = (String) request.getParameter("userID");

		System.out.println("userID :: "+userID );		
		UserDAO userdao = new UserDAO();
		String profile = userdao.getProfile(userID);
		UserDTO user = userdao.getUser(userID);
		request.setAttribute("profile", profile);
		request.setAttribute("user", user);

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

		ArrayList<MyBoardDTO> boardList = null;
		
		if(userID.equals(session.getAttribute("userID"))){
		 boardList = new MyBoardDAO().getmyList(pageNumber, userID);
		}else{
		 boardList = new MyBoardDAO().getmyList2(pageNumber, userID);
		}
		
		
		boolean beyond = new MyBoardDAO().checktarget(pageNumber, userID);
		GowithDTO getlist = new GowithDAO().getgowith(userID);

		request.setAttribute("getlist", getlist);
		request.setAttribute("boardList", boardList);
		request.setAttribute("pageNumber", pageNumber);		
		request.setAttribute("userID", userID);	
		request.setAttribute("beyond", beyond);		


		System.out.println("MyPageCheckAction() 작동");

		ActionForward forward = new ActionForward("myPage.jsp", false);
		return forward;
	}

}
