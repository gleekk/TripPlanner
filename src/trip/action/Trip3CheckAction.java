package trip.action;


import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.BoardDTO;
import vo.WeatherDTO;
import webinfo.tourist;
import webinfo.weather;

// index페이지로 이동
public class Trip3CheckAction implements Action {

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

		String city = (String)request.getParameter("city");
		request.setAttribute("city", city);
		TripDAO tripDAO = new TripDAO();
		
		ArrayList<WeatherDTO> weatherlist = new ArrayList<WeatherDTO>();
		weatherlist = new weather().test(city);
		request.setAttribute("weatherlist", weatherlist);
		
		if (nofday > 10) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "최대 10일까지 일정 계획을 세우실수 있습니다.");
			ActionForward forward = new ActionForward("step2.jsp", false);
			return forward;
		}
		
		
		if (departDay == null ||   returnDay == null  || departDay.equals("") || returnDay.equals("")) {
			
			
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "내용을 모두 채워주세요.");
			
			ActionForward forward = new ActionForward("step2.jsp", false);
			return forward;
		}
		
		
		
		int result = 0;
		result = tripDAO.plan(userID, city, departDay, returnDay, nofday); // 일단 저장

		if(result == -1){
			System.out.println("plan()함수 망함 빌어 먹을 ㅠㅠ");
			
		}else{
			System.out.println("plan()함수 정상 작동 함 히히히힣");
		}
		 
		int nofnight = nofday-1;
		
		request.setAttribute("city", city);
		request.setAttribute("departDay", departDay);
		request.setAttribute("returnDay", returnDay);
		request.setAttribute("nofday", nofday);
		request.setAttribute("nofnight", nofnight);
		
		
		ArrayList<String> tourlist = new tourist().test(city);

		request.setAttribute("tourlist", tourlist);
		
		ArrayList<BoardDTO> memtourlist = new BoardDAO().getTourist(city);

		request.setAttribute("memtourlist", memtourlist);

		ActionForward forward = new ActionForward("step3.jsp", false);
		return forward;
	

	}

}
