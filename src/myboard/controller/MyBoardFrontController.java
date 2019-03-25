package myboard.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import myboard.action.MyBoardCommentAction;
import myboard.action.MyBoardCommentdeleteAction;
import myboard.action.MyBoardDeleteAction;
import myboard.action.MyBoardFileDownAction;
import myboard.action.MyBoardSearchAction;
import myboard.action.MyBoardShowAction;
import myboard.action.MyBoardUpdateAction;
import myboard.action.MyBoardUpdateCheckAction;
import myboard.action.MyBoardWriteAction;
import myboard.action.MyBoardWriteCheckAction;
import myboard.action.MyBoardlikeAction;
import myboard.action.MyPageCheckAction;
import vo.Action;
import vo.ActionForward;

@WebServlet("*.myboard")
public class MyBoardFrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// 전송된 요청 정보 파악 하는 부분
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		// Action클래스 객체를 다형성을 사용해서 참조하는 변수를 정의한 부분
		Action action = null;
		// Action클래스 객체의 excute 메소드 실행후 반환되는 ActionForward를 저장할 변수 정의
		ActionForward forward = null;

		// board.jsp(게시판) 페이지로 이동
		if (command.equals("/boardView.myboard")) {
			action = new MyBoardSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// boardShow.jsp (글 내용 보기) 페이지로 이동
		else if (command.equals("/boardShow.myboard")) {

			action = new MyBoardShowAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 글 작성 기능
		else if (command.equals("/boardWrite.myboard")) {
			action = new MyBoardWriteCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// boardWrite.jsp (글 작성) 페이지로 이동
		else if (command.equals("/boardWriteaction.myboard")) {
			action = new MyBoardWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 파일 다운로드 기능
		else if (command.equals("/boardDownload.myboard")) {
			action = new MyBoardFileDownAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 글 삭제 기능
		else if (command.equals("/boardDelete.myboard")) {
			action = new MyBoardDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 글 수정 기능
		else if (command.equals("/boardUpdate.myboard")) {
			action = new MyBoardUpdateCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 글 수정 페이지로 이동
		else if (command.equals("/UpdateAction.myboard")) {
			action = new MyBoardUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if (command.equals("/boardSearch.myboard")) {
			action = new MyBoardSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/likeAction.myboard")) {
			action = new MyBoardlikeAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		else if (command.equals("/comment.myboard")) {
			action = new MyBoardCommentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/delcomment.myboard")) {
			action = new MyBoardCommentdeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/myPage.myboard")) {
			action = new MyPageCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if (forward != null) {
			if (forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);

			}
		}

	}

}
