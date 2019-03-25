package user.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.action.UserIndexCheckAction;
import user.action.UserJoinAction;
import user.action.UserJoinCheckAction;
import user.action.UserLoginAction;
import user.action.UserLoginCheckAction;
import user.action.UserLogoutAction;
import user.action.UserProFileCheckAction;
import user.action.UserProfileUpdateAction;
import user.action.UserUpdateAction;
import user.action.UserUpdateCheckAction;
import user.action.UserdataminingAction;
import vo.Action;
import vo.ActionForward;

@WebServlet("*.user")
public class UserFrontController extends HttpServlet {

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
		if (command.equals("/login.user")) {
			action = new UserLoginCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 로그인 기능 실행
		else if (command.equals("/loginAction.user")) {

			action = new UserLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 인덱스 페이지로 이동
		else if (command.equals("/index.user")) {
			action = new UserIndexCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 회원가입 페이지로 이동
		else if (command.equals("/join.user")) {
			action = new UserJoinCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 회원가입 기능
		else if (command.equals("/joinAction.user")) {
			action = new UserJoinAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		//로그 아웃 기능
		else if (command.equals("/logout.user")) {
			action = new UserLogoutAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		// 프로필 수정 페이지로 이동
		else if (command.equals("/profileUpdate.user")) {
			action = new UserProFileCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		// 프로필 수정 실행
		else if (command.equals("/userProfileUpdateAction.user")) {
			action = new UserProfileUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		// 회원정보 수정 페이지로 이동
		else if (command.equals("/update.user")) {
			action = new UserUpdateCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		// 회원정보 수정 실행
		else if (command.equals("/userUpdateAction.user")) {
			action = new UserUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (command.equals("/datamineAction.user")) {
			action = new UserdataminingAction();
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
