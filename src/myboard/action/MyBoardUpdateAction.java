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
import vo.MyBoardDTO;
import vo.MyCommentsDTO;
import vo.PlanDTO;

//글 수정 기능
public class MyBoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("myBoardUpdateAction 작동");

		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			ActionForward forward = new ActionForward("index.jsp", true);
			return forward;
		}
		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();

		String myboardID = null;
		if (multi.getParameter("myboardID") != null) {
			myboardID = multi.getParameter("myboardID");
		}
		String myplan = null;
		if (multi.getParameter("myplan") != null) {
			myplan = multi.getParameter("myplan");
		}
		int planNo = Integer.parseInt(myplan);
		
		if(planNo != 0){
		PlanDTO plan = new TripDAO().getplan(planNo);

		request.setAttribute("plan", plan);
		}
		
		String mycategory = null;
		
		if (multi.getParameter("mycategory") != null) {
			mycategory = multi.getParameter("mycategory");
		}
		
		String myvisibility = "o";
		
		if (multi.getParameter("myvisibility") != null) {
			myvisibility = multi.getParameter("myvisibility");
		}
		
		System.out.println("myvisibility :: "+myvisibility);
		
		

		MyBoardDAO boardDAO = new MyBoardDAO();

		String myboardTitle = null;
		if (multi.getParameter("myboardTitle") != null) {
			myboardTitle = multi.getParameter("myboardTitle");
		}
		String myboardContent = null;
		if (multi.getParameter("myboardContent") != null) {
			myboardContent = multi.getParameter("myboardContent");
		}
		
		if (myboardTitle == null || myboardTitle.equals("") || myboardContent == null || myboardContent.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			ActionForward forward = new ActionForward("myboardWrite.jsp", true);
			return forward;

		}

		String myboardFile = "";
		String myboardRealFile = "";
		
		File file = multi.getFile("myboardFile");
		if (file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {					
			String prev = new MyBoardDAO().getBoard(myboardID).getMyboardRealFile();
			File prevFile = new File(savePath + "/" + prev);
			if (prevFile.exists()) {
				prevFile.delete();
			}
			myboardFile = multi.getOriginalFileName("myboardFile");	
			myboardRealFile = file.getName();
		} else {
			myboardFile = boardDAO.getFile(myboardID);
			myboardRealFile = boardDAO.getRealFile(myboardID);
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
			MyBoardDTO board = boardDAO.getBoard(myboardID);
			request.setAttribute("board", board);
			ActionForward forward = new ActionForward("myboardUpdate.jsp", false);
			return forward;
		
		
		}

			
		}

		boardDAO.update(myboardID, myboardTitle, myboardContent, myboardFile, myboardRealFile, myplan, mycategory, myvisibility);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");
		
		MyBoardDTO board = boardDAO.getBoard(myboardID);
		ArrayList<MyCommentsDTO> commentList  = boardDAO.getComment(myboardID);
		
		request.setAttribute("commentList", commentList);
		request.setAttribute("board", board);
		
		String pagename = multi.getParameter("pagename");
		if(pagename.equals("mypage")){
			ActionForward forward = new ActionForward("myPage.myboard?userID="+userID, false);//false일 경우 dispatcher방식
			return forward;	
		}else{
		ActionForward forward = new ActionForward("myboardShow.jsp", false);//false일 경우 dispatcher방식
		return forward;
		}
		}

}
