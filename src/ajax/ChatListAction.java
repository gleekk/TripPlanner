package ajax;

import java.net.URLDecoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ChatDAO;

import vo.ChatDTO;

public class ChatListAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String fromID = request.getParameter("fromID");
		String toID = request.getParameter("toID");
		String listType = request.getParameter("listType");
		
		if (fromID == null || fromID.equals("") || toID == null || toID.equals("") || listType == null
				|| listType.equals("")) {
			return "";
		}else if (listType.equals("ten")) {
			StringBuffer result = new StringBuffer("");
			result.append("{\"result\":[");
			ChatDAO chatDAO = new ChatDAO();
			ArrayList<ChatDTO> chatList = chatDAO.getChatListByRecent(URLDecoder.decode(fromID, "UTF-8"),
					URLDecoder.decode(toID, "UTF-8"), 100);
			if (chatList.size() == 0)
				return "";
			for (int i = 0; i < chatList.size(); i++) {
				result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
				result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
				result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
				result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
				if (i != chatList.size() - 1)
					result.append(",");
			}
			result.append("], \"last\": \"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
			chatDAO.readChat(fromID, toID);
			return result.toString();
		}else{
				try {
					HttpSession session = request.getSession();
					if (!URLDecoder.decode(fromID, "UTF-8").equals((String) session.getAttribute("userID"))){
					return "";
					}

				StringBuffer result = new StringBuffer("");
				result.append("{\"result\":[");
				ChatDAO chatDAO = new ChatDAO();
				ArrayList<ChatDTO> chatList = chatDAO.getChatListByID(URLDecoder.decode(fromID, "UTF-8"),
						URLDecoder.decode(toID, "UTF-8"), listType);
				if (chatList.size() == 0)
					return "";
				for (int i = 0; i < chatList.size(); i++) {
					result.append("[{\"value\": \"" + chatList.get(i).getFromID() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getToID() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getChatContent() + "\"},");
					result.append("{\"value\": \"" + chatList.get(i).getChatTime() + "\"}]");
					if (i != chatList.size() - 1)
						result.append(",");
				}
				result.append("], \"last\": \"" + chatList.get(chatList.size() - 1).getChatID() + "\"}");
				chatDAO.readChat(fromID, toID);
				return result.toString();

			} catch (Exception e) {
				return "";

			}
		}
	}

}
