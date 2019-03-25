package myboard.action;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.MyBoardDAO;
import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.PlanDTO;

// 글 작성 기능 및 파일 첨부 기능
public class MyBoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardWriteAction 작동");
		HttpSession session = request.getSession();
		String userID = (String) session.getAttribute("userID");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			
			ArrayList<PlanDTO> planList = new TripDAO().bringplan(userID);
			request.setAttribute("planList", planList);
	
			ActionForward forward = new ActionForward("myboardWrite.jsp", false);
			return forward;
		}
		
		MyBoardDAO boardDAO = new MyBoardDAO();

		
		String myboardTitle = null;
		
		if (multi.getParameter("myboardTitle") != null) {
			myboardTitle = multi.getParameter("myboardTitle");
		}
		
		String myboardContent = null;
		
		if (multi.getParameter("myboardContent") != null) {
			myboardContent = multi.getParameter("myboardContent");

		}
		
		String mycategory = null;
		
		if (multi.getParameter("mycategory") != null) {
			mycategory = multi.getParameter("mycategory");
		}
		
		String myplan = null;
		if (multi.getParameter("myplan") != null) {
			myplan = multi.getParameter("myplan");
		}
		System.out.println("myplan :: "+myplan);
		
		String myvisibility = "o";
		
		if (multi.getParameter("myvisibility") != null) {
			myvisibility = multi.getParameter("myvisibility");
		}
		
		if (myboardTitle == null || myboardTitle.equals("") || myboardContent == null || myboardContent.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			
			ArrayList<PlanDTO> planList = new TripDAO().bringplan(userID);
			request.setAttribute("planList", planList);
			
			ActionForward forward = new ActionForward("myboardWrite.jsp", false);
			return forward;

		}

		String myboardFile = "";
		String myboardRealFile = "";
		
		File file = multi.getFile("myboardFile");
		
		if (file != null) {
			myboardFile = multi.getOriginalFileName("myboardFile");
			myboardRealFile = file.getName();

		}
		String pagename = multi.getParameter("pagename");

		
		String preContent = boardDAO.doublecheck();

		System.out.println("===============중복 체크 =================");
		System.out.println("preContent :: " + preContent);
		System.out.println("myboardContent :: " + myboardContent);
		if(myboardContent.equals(preContent)){
			System.out.println("===============중복 체크 =================");

			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "글 내용을 중복하실 수 없습니다.");
			
			
			
			if(pagename.equals("mypage")){
				ActionForward forward = new ActionForward("myPage.myboard?userID="+userID, true);
				return forward;
			}else{
				ActionForward forward = new ActionForward("boardView.myboard", true);
				return forward;
			}
			
		}

		
		
		boardDAO.write(userID, myboardTitle, myboardContent, myboardFile, myboardRealFile, mycategory, myplan, myvisibility);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 작성되었습니다.");

		
		if(pagename.equals("mypage")){
			ActionForward forward = new ActionForward("myPage.myboard?userID="+userID, false);
			return forward;
		}else{
			ActionForward forward = new ActionForward("boardView.myboard", false);
			return forward;
		}

	}

}
