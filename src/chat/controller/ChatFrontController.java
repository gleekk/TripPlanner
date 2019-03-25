package chat.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import chat.action.BoxCheckAction;
import chat.action.ChatCheckAction;
import chat.action.FindCheckAction;
import vo.Action;
import vo.ActionForward;

@WebServlet("*.chat")
public class ChatFrontController extends HttpServlet {

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

		// find.jsp(친구찾기) 페이지로 이동
		if (command.equals("/find.chat")) {
			action = new FindCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// chat.jsp(채팅창) 페이지로 이동
		else if (command.equals("/chat.chat")) {

			action = new ChatCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// box.jsp (메세지함) 페이지로 이동
		else if (command.equals("/box.chat")) {
			action = new BoxCheckAction();
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
