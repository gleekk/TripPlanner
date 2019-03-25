package ajax;


import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatDAO;

public class ChatUnreadAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			return "0";
		} else {
			userID = URLDecoder.decode(userID, "UTF-8");
			HttpSession session = request.getSession();
			if (!URLDecoder.decode(userID, "UTF-8").equals((String) session.getAttribute("userID"))) {

				return "";
			}

			return (new ChatDAO().getAllUnreadChat(userID) + "");
		}

	}
}
