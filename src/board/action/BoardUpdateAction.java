package board.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDAO;
import vo.ActionForward;
import vo.BoardDTO;
import vo.Action;

//글 수정 기능
public class BoardUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardUpdateAction 작동");

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

		String boardID = multi.getParameter("boardID");
		String tourist = multi.getParameter("tourist");

		BoardDAO boardDAO = new BoardDAO();

		String boardTitle = multi.getParameter("boardTitle");
		String boardContent = multi.getParameter("boardContent");
		if (boardTitle == null || boardTitle.equals("") || boardContent == null || boardContent.equals("")) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			ActionForward forward = new ActionForward("boardWrite.jsp", true);
			return forward;

		}

		String boardFile = "";
		String boardRealFile = "";
		File file = multi.getFile("boardFile");
		if (file != null) {
			boardFile = multi.getOriginalFileName("boardFile");
			boardRealFile = file.getName();
			String prev = boardDAO.getRealFile(boardID);
			File prevFile = new File(savePath + "/" + prev);
			if (prevFile.exists()) {
				prevFile.delete();
			}
		} else {
			boardFile = boardDAO.getFile(boardID);
			boardRealFile = boardDAO.getRealFile(boardID);
		}

		boardDAO.update(boardID, boardTitle, boardContent, boardFile, boardRealFile, tourist);
		session.setAttribute("messageType", "성공 메시지");
		session.setAttribute("messageContent", "성공적으로 게시물이 수정되었습니다.");

		BoardDTO board = boardDAO.getBoard(boardID);
		request.setAttribute("board", board);

		ActionForward forward = new ActionForward("boardShow.jsp", false);
		return forward;

	}

}
