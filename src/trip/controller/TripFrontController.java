package trip.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import trip.action.Trip1CheckAction;
import trip.action.Trip2CheckAction;
import trip.action.Trip3CheckAction;
import trip.action.Trip3UpAction;
import trip.action.Trip4CheckAction;
import trip.action.TripDelAction;
import vo.Action;
import vo.ActionForward;

@WebServlet("*.trip")
public class TripFrontController extends HttpServlet {

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

		// login페이지로 이동(포워딩)
		if (command.equals("/step1.trip")) {
			action = new Trip1CheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 로그인 기능 실행
		else if (command.equals("/step2.trip")) {
			action = new Trip2CheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/step3.trip")) {
			action = new Trip3CheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/step4.trip")) {
			action = new Trip4CheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/step3up.trip")) {
			action = new Trip3UpAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("step3up에러");
			}
		}
		else if (command.equals("/stepDel.trip")) {
			action = new TripDelAction();
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
