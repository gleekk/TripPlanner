package gowith.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GowithDAO;
import dao.UserDAO;
import vo.Action;
import vo.ActionForward;
import vo.GowithDTO;

public class GowithWriteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String userid=request.getParameter("userid");
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		String date1=request.getParameter("date1");
		String date2=request.getParameter("date2");
		String destination=request.getParameter("destination");
		int mem=Integer.parseInt(request.getParameter("mem"));
		int age=Integer.parseInt(request.getParameter("age"));
		UserDAO udao=new UserDAO();
		String profilephoto=udao.getProfile(userid);
		
		System.out.println("profilephoto :"+ profilephoto);
		System.out.println("date1"+date1);
		System.out.println("date2"+date2);
		
		GowithDTO dto=new GowithDTO();
		dto.setUserid(userid);
		dto.setTitle(title);
		dto.setContent(content);
		dto.setDate1(date1);
		dto.setDate2(date2);
		dto.setDestination(destination);
		dto.setMem(mem);
		dto.setAge(age);
		dto.setProfilephoto(profilephoto);
		
		
		System.out.println("함수들어간다");
		GowithDAO dao=new GowithDAO();
		dao.insert(dto);
		
		ActionForward forward = new ActionForward("gowithView.with", true);
		return forward;
		
	}
	

}
