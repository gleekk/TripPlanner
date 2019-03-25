package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;

public class UserFindAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String userID = request.getParameter("userID");
		if (userID == null || userID.equals("")) {
			return "-1";

		} else if (new UserDAO().registerCheck(userID) == 0) {
			try {
				StringBuffer result = new StringBuffer();
				result.append("{\"userProfile\":\"" + new UserDAO().getProfile(userID) + "\"}");

				return result.toString();

			} catch (Exception e) {
				e.printStackTrace();
				return "-1";

			}
		} else {
			return "-1";
		}
	}

}
