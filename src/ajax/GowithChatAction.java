package ajax;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.UserDAO;

public class GowithChatAction {
	
	public JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("GowithChatAction들어왔다");
	
		HttpSession session = request.getSession();	
		
		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		String toID = null;
		if (request.getParameter("toID") != null) {
			toID = (String) request.getParameter("toID");
		}
		
		if (userID.equals(URLDecoder.decode(toID, "UTF-8"))) {
			JSONObject obj= new JSONObject();
			obj.put("no", "-1");
			return obj;
		}
		else{
			String fromProfile = new UserDAO().getProfile(userID);
			
			String toProfile = new UserDAO().getProfile(toID);
			
			JSONObject obj= new JSONObject();
			obj.put("no", "0");
			obj.put("fromProfile", fromProfile);
			obj.put("toProfile", toProfile);
			obj.put("toID", toID);
			return obj;
			
		}
		
	}

}
