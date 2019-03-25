package ajax;

import java.io.IOException;
import java.time.LocalDate;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import dao.TripDAO;
import vo.PlanDTO;


@WebServlet("/CalAddValServlet")
public class CalAddValServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		int planNo = Integer.parseInt(request.getParameter("planNo"));
		PlanDTO dto = new TripDAO().getplan(planNo);
		JSONObject object = new JSONObject();
		object.put("title", dto.getCity());
		object.put("start", dto.getDepartDay());
		
		String departDay = dto.getReturnDay();
		LocalDate ldepartDay = LocalDate.parse(departDay); 
		LocalDate ldate = ldepartDay.plusDays(1);
		String date = ldate.toString();
	
		object.put("end", date);
		System.out.println(object);
		response.getWriter().print(object);
		
		
	}

	
}
