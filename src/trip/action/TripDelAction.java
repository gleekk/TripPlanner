package trip.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TripDAO;
import vo.Action;
import vo.ActionForward;
import vo.PlanDTO;

// index페이지로 이동
public class TripDelAction implements Action {

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
				
		TripDAO tripDAO = new TripDAO();
		
		tripDAO.deleteplan(planNo); // 계획 삭제 
		tripDAO.deleteschedule(planNo); // 계획 삭제 
		
		ArrayList<PlanDTO> planList = new TripDAO().bringplan(userID);

		request.setAttribute("planList", planList);

		ActionForward forward = new ActionForward("step4.jsp", false);
		return forward;
	}
}
