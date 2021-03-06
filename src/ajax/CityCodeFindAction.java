package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TripDAO;

public class CityCodeFindAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String city = request.getParameter("city");

		if (city == null || city.equals("")) {

			return "1";

		} else if (new TripDAO().cityCheck(city) != null) {
			try {
				StringBuffer result = new StringBuffer();
				result.append("{\"cityName\":\"" + new TripDAO().cityCheck(city) + "\",");
				result.append("\"cityCode\":\"" + new TripDAO().cityCode(city) + "\"}");
				System.out.println(new TripDAO().cityCheck(city));
				System.out.println(new TripDAO().cityCode(city));
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
