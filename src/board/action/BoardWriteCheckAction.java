package board.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.Action;
import vo.ActionForward;
import vo.CityDTO;
import dao.TripDAO;


//글 작성 페이지로 이동 기능
public class BoardWriteCheckAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");// 전달 받은 request 정보 한글처리
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("BoardWriteCheckAction 작동");
		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("login.jsp", true);
			return forward;
		}
		
		ArrayList<CityDTO> cityList = new TripDAO().getCity();
		request.setAttribute("cityList", cityList);
		

		ActionForward forward = new ActionForward("boardWrite.jsp", false);
		return forward;

	}

}
