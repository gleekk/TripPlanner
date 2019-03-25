package ajax;

import java.net.URLDecoder;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatDAO;


public class ChatSubmitAction{

public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String chatContent = request.getParameter("chatContent");
		if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || chatContent == null || chatContent.equals("")) {
			return "0";
		} else if (fromID.equals(toID)) {
			return "-1";
		} else {
			fromID = URLDecoder.decode(fromID, "UTF-8");
			toID = URLDecoder.decode(toID, "UTF-8");
			HttpSession session = request.getSession();
			if (!URLDecoder.decode(fromID, "UTF-8").equals((String) session.getAttribute("userID"))) {
				return "";
			}
			chatContent = URLDecoder.decode(chatContent, "UTF-8");
			return (new ChatDAO().submit(fromID, toID, chatContent) + "");
		}
	}
}
