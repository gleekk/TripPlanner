package board.action;

import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServlet;

import dao.BoardDAO;
import vo.ActionForward;
import vo.Action;

public class BoardFileDownAction extends HttpServlet implements Action{
	
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardFileDownAction() 작동");

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String boardID = request.getParameter("boardID");

		if (boardID == null || boardID.equals("")){
		session.setAttribute("messageType", "오류 메시지");
		session.setAttribute("messageContent", "접근 할 수 없습니다.");
		ActionForward forward = new ActionForward("index.jsp", false);
		return forward;
	}
		String root = request.getSession().getServletContext().getRealPath("/");
		String savePath = root + "upload";
		String fileName = "";
		String realFile = "";
		BoardDAO boardDAO = new BoardDAO();
		fileName = boardDAO.getFile(boardID);
		realFile = boardDAO.getRealFile(boardID);

		if(fileName.equals("")|| realFile.equals("")){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "접근 할 수 없습니다.");
			ActionForward forward = new ActionForward("index.jsp", false);
			return forward;
		}

		try{

			String sFilePath = savePath + "\\" + realFile;
			byte b[] = new byte[4096];
			FileInputStream in = new FileInputStream(sFilePath);

			String sMimeType = request.getSession().getServletContext().getMimeType(sFilePath);
			System.out.println("sMimeType>>>" + sMimeType);

			if (sMimeType == null)
				sMimeType = "application/octet-stream";

			response.setContentType(sMimeType);
			String agent = request.getHeader("User-Agent");
			boolean ieBrowser = (agent.indexOf("MSIE") > -1) || (agent.indexOf("Trident") > -1);

			if (ieBrowser) {
				fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			}

			response.setHeader("Content-Disposition", "attachment; filename= " + fileName);

			ServletOutputStream out2 = response.getOutputStream();
			int numRead;

			while ((numRead = in.read(b, 0, b.length)) != -1) {
				out2.write(b, 0, numRead);
			}
			out2.flush();
			out2.close();
			in.close();		
		
		}catch(Exception e){
			e.printStackTrace();
			
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "다운로드 프로세스 오류 발생");
			ActionForward forward = new ActionForward("boardView.board", false);
			return forward;
			
		}

		
		
		request.setAttribute("boardID", boardID);
		ActionForward forward = new ActionForward("boardShow.board", false);
		return forward;
		}
		
	}


