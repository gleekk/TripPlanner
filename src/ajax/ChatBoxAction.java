package ajax;


import java.net.URLDecoder;
import java.util.ArrayList;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatDAO;
import dao.UserDAO;
import vo.ChatDTO;

public class ChatBoxAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			return "";
		} else {
			try {
				HttpSession session = request.getSession();
				if (!URLDecoder.decode(userID, "UTF-8").equals((String) session.getAttribute("userID"))) {
					return "";
				}
				userID = URLDecoder.decode(userID, "UTF-8");
				StringBuffer result = new StringBuffer("");
				result.append("{\"result\":[");
				ChatDAO chatDAO = new ChatDAO();
				ArrayList<ChatDTO> chatList = chatDAO.getBox(userID);
				if (chatList.size() == 0)
					return "";
				for (int i = chatList.size() - 1; i >= 0; i--) {
					String unread = "";
					String userProfile = "";
					if (userID.equals(chatList.get(i).getToID())) {
						unread = chatDAO.getUnreadChat(chatList.get(i).getFromID(), userID) + "";
						if (unread.equals("0"))
							unread = "";
					}
					if (userID.equals(chatList.get(i).getToID())) {
						userProfile = new UserDAO().getProfile(chatList.get(i).getFromID());
					} else {
						userProfile = new UserDAO().getProfile(chatList.get(i).getToID());
					}
					result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"},");
					result.append("{\"value\": \"" + unread + "\"},");
					result.append("{\"value\": \"" + userProfile + "\"}]");
					if (i != 0)
						result.append(",");
				}
				result.append("], \"last\": \"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
				return result.toString();
			} catch (Exception e) {
				return "";
			}
		}
	}
}
