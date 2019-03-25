package gowith.action;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GowithDAO;
import vo.Action;
import vo.ActionForward;
import vo.GowithDTO;

public class GowithSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		System.out.println("GowithSearchAction() 작동");
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

		String date1=null;
		if(request.getParameter("date1")!=null){
			if(request.getParameter("date1") == ""){
			date1=null;
			}else
			date1=request.getParameter("date1");
					}
		String date2=null;
		if(request.getParameter("date2")!=null){
			if(request.getParameter("date2") == ""){
			date2=null;
			}else
			date2=request.getParameter("date2");
					}
		int age=0;
		if(request.getParameter("age")!=null){age=Integer.parseInt(request.getParameter("age"));}
		int mem=0;
		if(request.getParameter("mem")!=null){mem=Integer.parseInt(request.getParameter("mem"));}
		String destination="";
		if(request.getParameter("destination")!=null){destination=request.getParameter("destination");}

		GowithDAO dao=new GowithDAO();
		
		String pageNumber = "1";
		if(request.getParameter("pageNumber") != null){
			pageNumber = request.getParameter("pageNumber");
		}
		try{
			Integer.parseInt(pageNumber);
		}catch(Exception e){
			session.setAttribute("messageType", "오류 메시지");
			session.setAttribute("messageContent", "페이지 번호가 잘못 되었습니다.");
			response.sendRedirect("boardView.jsp");
		}
		System.out.println("pageNumber::"+pageNumber);
		
		int startPage = (Integer.parseInt(pageNumber)/10)*10+1;
		
		if(Integer.parseInt(pageNumber)%10 == 0) startPage -= 10;
		
		
		request.setAttribute("date1", date1);
		request.setAttribute("date2", date2);
		request.setAttribute("age", age);
		request.setAttribute("mem", mem);
		request.setAttribute("destination", destination);
		
		System.out.println("date1 :: "+date1);
		System.out.println("date2 :: "+date2);
		System.out.println("age :: "+age);
		System.out.println("mem :: "+mem);
		System.out.println("destination :: "+destination);

		
		int targetPage = dao.targetPage(date1, date2, age, mem, destination, pageNumber);
				
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("startPage", startPage);
		request.setAttribute("targetPage", targetPage);

		Vector<GowithDTO> v=dao.search(date1, date2,age,mem,destination, userID, pageNumber);
		request.setAttribute("getlist", v);
		
		ActionForward forward = new ActionForward("gowithView.jsp", false);
		return forward;
	}

	
	
}
