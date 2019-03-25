package ajax.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import ajax.BoardInfoAction;
import ajax.ChatBoxAction;
import ajax.ChatListAction;
import ajax.ChatSubmitAction;
import ajax.ChatUnreadAction;
import ajax.CityCodeFindAction;
import ajax.CityEngNameFindAction;
import ajax.CityFindAction;
import ajax.FindScheduleAction;
import ajax.GowithChatAction;
import ajax.GowithDeleteAction;
import ajax.GowithInfoAction;
import ajax.GowithLikeAction;
import ajax.ScheduleSaveAction;
import ajax.UserFindAction;
import ajax.UserRegisterCheckAction;


@WebServlet("*.ajax")
public class AjaxFrontController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doProcess(request, response);
	}

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// �쟾�넚�맂 �슂泥� �젙蹂� �뙆�븙 �븯�뒗 遺�遺�
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());

		// 
		if (command.equals("/FindSchedule.ajax")) {
			FindScheduleAction ajax = new FindScheduleAction();
			try {
				String schedule = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(schedule);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/ScheduleSave.ajax")) {
			ScheduleSaveAction ajax = new ScheduleSaveAction();
			try {
				ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/UserFind.ajax")) {
			UserFindAction ajax = new UserFindAction();
			try {
				String User = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(User);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/UserRegisterCheck.ajax")) {
			UserRegisterCheckAction ajax = new UserRegisterCheckAction();
			try {
				String UserID = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(UserID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/CityFind.ajax")) {
			CityFindAction ajax = new CityFindAction();
			try {
				String city = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(city);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/CityEngNameFind.ajax")) {
			CityEngNameFindAction ajax = new CityEngNameFindAction();
			try {
				String Engcity = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(Engcity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/CityCodeFind.ajax")) {
			CityCodeFindAction ajax = new CityCodeFindAction();
			try {
				String code = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(code);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/ChatBox.ajax")) {
			ChatBoxAction ajax = new ChatBoxAction();
			try {
				String chatlist = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(chatlist);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/ChatSubmit.ajax")) {
			ChatSubmitAction ajax = new ChatSubmitAction();
			try {
				String chatContent = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(chatContent);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/ChatList.ajax")) {
			ChatListAction ajax = new ChatListAction();
			try {
				String chat = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(chat);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (command.equals("/ChatUnread.ajax")) {
			ChatUnreadAction ajax = new ChatUnreadAction();
			try {
				String unread = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(unread);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (command.equals("/GowithInfo.ajax")) {
			GowithInfoAction ajax = new GowithInfoAction();
			try {
				JSONObject info = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (command.equals("/GowithDelete.ajax")) {
			GowithDeleteAction ajax = new GowithDeleteAction();
			try {
				String delete = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().write(delete);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (command.equals("/GowithChat.ajax")) {
			GowithChatAction ajax = new GowithChatAction();
			try {
				JSONObject chat = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(chat);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (command.equals("/GowithLike.ajax")) {
			GowithLikeAction ajax = new GowithLikeAction();
			try {
				int like = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(like);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(command.equals("/MyBoardView.ajax")){
			BoardInfoAction ajax = new BoardInfoAction();
			try {
				JSONObject info = ajax.execute(request, response);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().print(info);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
