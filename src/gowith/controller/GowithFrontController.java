package gowith.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import gowith.action.GowithCheckAction;
import gowith.action.GowithGetLikeAction;
import gowith.action.GowithSearchAction;
import gowith.action.GowithWriteAction;
import gowith.action.GowithWriteCheckAction;
import vo.Action;
import vo.ActionForward;


@WebServlet("*.with")
public class GowithFrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		System.out.println(command);
		
		if (command.equals("/gowithView.with")) {
			action = new GowithCheckAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		else if(command.equals("/gowithwrite.with")){
			action = new GowithWriteCheckAction();	
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/gowithwriteaction.with")){	
			
			action = new GowithWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/gowithsearch.with")){	
			
			action = new GowithSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/gowithgetlike.with")){	
			
			action = new GowithGetLikeAction();
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
