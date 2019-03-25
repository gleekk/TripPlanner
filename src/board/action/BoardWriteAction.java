package board.action;

import java.io.File;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import dao.TripDAO;
import dao.UserDAO;
import vo.Action;
import vo.ActionForward;
import vo.BoardDTO;
import vo.CityDTO;
import vo.UserDTO;

// 글 작성 기능 및 파일 첨부 기능
public class BoardWriteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardWriteAction 작동");
		HttpSession session = request.getSession();

		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다.");
			
			ArrayList<CityDTO> cityList = new TripDAO().getCity();
			request.setAttribute("cityList", cityList);
	
			ActionForward forward = new ActionForward("boardWrite.jsp", false);
			return forward;
		}
		
		String userID = null;
		String boardTitle =  null;
		String boardContent =  null;
		String city =  null;
		String tourist =  null;
		
		if (multi.getParameter("userID") != null){
			 userID = (String)multi.getParameter("userID");		
			}
		if (multi.getParameter("boardTitle") != null){
			boardTitle = (String)multi.getParameter("boardTitle");		
			}
		if (multi.getParameter("boardContent") != null){
			boardContent = (String)multi.getParameter("boardContent");		
			}
		if (multi.getParameter("city") != null){
			city = (String)multi.getParameter("city");		
			}
		if (multi.getParameter("tourist") != null){
			tourist = (String)multi.getParameter("tourist");		
			}
		
		request.setAttribute("userID", userID);

		if (boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")
				|| tourist == null || tourist.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			
			ArrayList<CityDTO> cityList = new TripDAO().getCity();
			request.setAttribute("cityList", cityList);
			
			ActionForward forward = new ActionForward("boardWrite.jsp", false);
			return forward;

		}

		String boardFile = "";
		String boardRealFile = "";
		
		File file = multi.getFile("boardFile");
		
		if (file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();

		}
		BoardDAO boardDAO = new BoardDAO();
		
		
		String preContent = boardDAO.doublecheck2();

		System.out.println("===============중복 체크 =================");
		System.out.println("preContent :: " + preContent);
		System.out.println("boardContent :: " + boardContent);
		if(boardContent.equals(preContent)){
		System.out.println("===============중복 체크 =================");

			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "글 내용을 중복하실 수 없습니다.");
	
			ActionForward forward = new ActionForward("boardView.board", true);
			return forward;
		
			
		}
		
		
		
		boardDAO.write(userID, boardTitle, boardContent, boardFile, boardRealFile, city, tourist);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 작성되었습니다.");

		ActionForward forward = new ActionForward("boardView.board", false);
		return forward;

	}

}
