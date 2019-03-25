package trip.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.WeatherDTO;
import webinfo.weather;

// index페이지로 이동
public class Trip2CheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

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

		String city = request.getParameter("city");
		request.setAttribute("city", city);
		TripDAO tripDAO = new TripDAO();
		tripDAO.cityCount(city); // 도시 선호도 조사 

		ArrayList<WeatherDTO> weatherlist = new ArrayList<WeatherDTO>();

		weatherlist = new weather().test(city);
					 
		request.setAttribute("weatherlist", weatherlist);
		
		ActionForward forward = new ActionForward("step2.jsp", false);
		return forward;

	}

}
