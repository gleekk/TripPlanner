package trip.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.CityDTO;

// index페이지로 이동
public class Trip1CheckAction implements Action {

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
			ActionForward forward = new ActionForward("login.jsp", false);
			return forward;
		}

	
		ArrayList<CityDTO> cityList = new TripDAO().getCity();
		request.setAttribute("cityList", cityList);


		
		
		ActionForward forward = new ActionForward("step1.jsp", false);
		return forward;
		
	}

}
