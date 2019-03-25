package user.action;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import dao.AnalysisDAO;
import textmining.Textmining;
import vo.Action;
import vo.ActionForward;
import vo.CityDTO;
import vo.wordDTO;

// index페이지로 이동
public class UserdataminingAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("UserdataminingAction() 작동");
		HttpSession session = request.getSession();

		String userID = null;
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}

		if (userID == null) {
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "현재 로그인이 되어 있지 않습니다.");
			ActionForward forward = new ActionForward("login.jsp", true); // true일													// redirect방식
			return forward;
		}
		
		
		AnalysisDAO analysisDAO = new AnalysisDAO();
		int[] p = analysisDAO.getpopulation();
		int[] w = analysisDAO.getpopulationw();

		String class1= "["+p[0]+","+p[1]+","+p[2]+","+p[3]+","+p[4]+","+p[5]+","+p[6]+","+p[7]+","+p[8]+","+p[9]+","+p[10]+","+p[11]+","+p[12]+","+p[13]+","+p[14]+","+p[15]+","+p[16]+","+p[17]+","+p[18]+","+p[19]+","+p[20]+","+p[21]+"]";
		String class2= "["+w[22]+","+w[1]+","+w[2]+","+w[3]+","+w[4]+","+w[5]+","+w[6]+","+w[7]+","+w[8]+","+w[9]+","+w[10]+","+w[11]+","+w[12]+","+w[13]+","+w[14]+","+w[15]+","+w[16]+","+w[17]+","+w[18]+","+w[19]+","+w[20]+","+w[21]+"]";

		request.setAttribute("class1", class1);
		request.setAttribute("class2", class2);
		
		
		ArrayList<CityDTO> cityList = analysisDAO.getCity();
		request.setAttribute("cityList", cityList);
		
		String fivecity = "[['"+cityList.get(0).getCity()+"',"+cityList.get(0).getCitycount()+"],"
						+ "['"+cityList.get(1).getCity()+"',"+cityList.get(1).getCitycount()+"],"
						+ "['"+cityList.get(2).getCity()+"',"+cityList.get(2).getCitycount()+"],"
						+ "['"+cityList.get(3).getCity()+"',"+cityList.get(3).getCitycount()+"],"
						+ "['"+cityList.get(4).getCity()+"',"+cityList.get(4).getCitycount()+"]]";
		
		request.setAttribute("fivecity", fivecity);
		
		
		int[] c = analysisDAO.getCategoryinfo();

		String category = "[[['기타',"+c[0]+"],['여행계획',"+c[1]+"], ['여행후기',"+c[2]+"]]]";
		
		request.setAttribute("category", category);
	

		Textmining tt = new Textmining();
		tt.Textmining(); // 텍스트 마이닝 시작
		
		ArrayList<wordDTO> wordranklist = analysisDAO.getRankword();
		System.out.println("단어 빈도 순위 :: "+wordranklist.get(0).getKeyword()+"("+wordranklist.get(0).getKeyvalue()+"),"+wordranklist.get(1).getKeyword()+"("+wordranklist.get(1).getKeyvalue()+"),"+wordranklist.get(2).getKeyword()+"("+wordranklist.get(2).getKeyvalue()+")");

		
		int ranktotal = analysisDAO.getRanktotal();
		
		System.out.println("total value :: "+ranktotal);
		
		List list = new ArrayList();

		   for(int i = 0 ; i < 25 ; i++){

		StringBuffer result = new StringBuffer();
		if(wordranklist.get(i).getKeyword().length()==1){ // 한글 외자 제거
			result.append("{\"text\":\" \",");
		}else{
			result.append("{\"text\":\"" + wordranklist.get(i).getKeyword() + "\",");
		}
		
		int values = wordranklist.get(i).getKeyvalue();
		int insertvalue = (int)Math.ceil(values*700/ranktotal);
		result.append("\"size\":\"" + insertvalue + "\"}");
		
		list.add(result);
		}
		
			request.setAttribute("list", list);

		

		ActionForward forward = new ActionForward("datamining.jsp", false);
		return forward;

	}

}
