package ajax;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TripDAO;
import vo.ScheduleDTO;

public class FindScheduleAction{

public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int planNo = Integer.parseInt(request.getParameter("planNo"));
		System.out.println("FindScheduleAction실행");
		StringBuffer result = new StringBuffer("");
		result.append("{\"result\":[");
		ArrayList<ScheduleDTO> scheduleList = new TripDAO().bringschedule(planNo);
		if (scheduleList.size() == 0)
			return "";
		for (int i = 0; i < scheduleList.size(); i++) {
			result.append("[{\"value\": \"" + scheduleList.get(i).getPlanNo() + "\"},");
			result.append("{\"value\": \"" + scheduleList.get(i).getDayNo() + "\"},");
			result.append("{\"value\": \"" + scheduleList.get(i).getThingNo() + "\"},");
			result.append("{\"value\": \"" + scheduleList.get(i).getTouristName() + "\"}]");
			System.out.println(scheduleList.get(i).getTouristName());
			
			if (i != scheduleList.size() - 1)
				result.append(",");
		}
		result.append("] }");
		return result.toString();		
	}
}



