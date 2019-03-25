package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.MyBoardDAO;
import dao.TripDAO;
import vo.MyBoardDTO;
import vo.PlanDTO;

public class BoardInfoAction {
	public JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		HttpSession session = request.getSession();

		String userID = (String) session.getAttribute("userID");
		

		String no = request.getParameter("no");
		JSONObject obj = new JSONObject();
		MyBoardDTO dto = new MyBoardDAO().getBoard(no);

		obj.put("no", no);
		obj.put("userID", userID);
		obj.put("dbID",dto.getUserID());
		obj.put("title", dto.getMyboardTitle());
		obj.put("content", dto.getMyboardContent());
		obj.put("date", dto.getMyboardDate());
		obj.put("hit", dto.getMyboardHit());
		obj.put("file", dto.getMyboardFile());
		obj.put("realfile", dto.getMyboardRealFile());
		obj.put("group", dto.getMyboardGroup());
		obj.put("sequence", dto.getMyboardSequence());
		obj.put("level", dto.getMyboardLevel());
		obj.put("available", dto.getMyboardAvailable());
		obj.put("likecount", dto.getMylikeCount());
		obj.put("category", dto.getMycategory());
		obj.put("visibility", dto.getMyvisibility());
		
		
		int planNo = Integer.parseInt(dto.getMyplan());
		String plan = null;
		if(planNo == 0){
			
		plan = "일정없음";	
			
		obj.put("plan", plan);

		}else{
			PlanDTO planDto = new TripDAO().getplan(planNo);
			plan = planDto.getCity() + (planDto.getNofday()-1)+ "박" + planDto.getNofday() +"일"+ 
			"("+ planDto.getDepartDay()+ " ~ "+  planDto.getReturnDay() + ")" ;
					System.out.println("my ::"+plan);
			obj.put("plan", plan);
			
		}

		return obj;
	}

}
