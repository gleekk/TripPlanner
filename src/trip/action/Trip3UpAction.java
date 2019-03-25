package trip.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.Action;
import vo.ActionForward;
import webinfo.tourist;

// index페이지로 이동
public class Trip3UpAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
	
		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("index.jsp", false);
			return forward;
		}
		
		String departDay = null;
		String returnDay = null;
		int nofday = 0;
		if (request.getParameter("nofday") != null) {
			try {
				nofday = Integer.parseInt(request.getParameter("nofday"));
				System.out.println("여행 일수 : "+nofday);
			} catch (Exception e) {
				System.out.println("여행 일 수 계산 오류");
			}
		}
		
		if (request.getParameter("returnDay") != null) {
			returnDay = request.getParameter("returnDay");
		}
		if (request.getParameter("departDay") != null) {
			departDay = request.getParameter("departDay");
		}
		String city = null;
		 city = request.getParameter("city");
		
		int planNo = 0;
		
			if (request.getParameter("planNo") != null) {
				try {
					planNo = Integer.parseInt(request.getParameter("planNo"));
					System.out.println("여행계획번호 :"+planNo);
				} catch (Exception e) {
					System.out.println("여행 일 수 계산 오류");
					e.printStackTrace();
				}
			}	

		int nofnight = 0;

		
		nofnight = nofday-1;

		request.setAttribute("city", city);
		request.setAttribute("departDay", departDay);
		request.setAttribute("returnDay", returnDay);
		request.setAttribute("nofday", nofday);
		request.setAttribute("nofnight", nofnight);
		request.setAttribute("planNo", planNo);
		
		System.out.println(city);
		System.out.println(departDay);
		System.out.println(returnDay);
		System.out.println(nofday);
		System.out.println(nofnight);
		
		ArrayList<String> tourlist = new tourist().test(city);

		request.setAttribute("tourlist", tourlist);
		

		ActionForward forward = new ActionForward("step7.jsp", false);
		return forward;
	}
}
