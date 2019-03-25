package ajax;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import dao.GowithDAO;
import vo.GowithDTO;

public class GowithInfoAction {
	
	public JSONObject execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		int no = Integer.parseInt(request.getParameter("no"));
		System.out.println(no);
		
		HttpSession session = request.getSession();	
		String user=(String) session.getAttribute("userID");
		
		GowithDAO dao=new GowithDAO();
		System.out.println("getinfo()들어간다");
		GowithDTO dto=dao.getinfo(no);
		System.out.println(dto.getTitle());
		

		JSONObject obj= new JSONObject();
		obj.put("no", no);
		obj.put("user", user);
		obj.put("userid", dto.getUserid());
		obj.put("title", dto.getTitle());
		obj.put("content", dto.getContent());
		obj.put("date1", dto.getDate1());
		obj.put("date2", dto.getDate2());
		
		if(dto.getMem()==2){
			obj.put("mem", "2명");
		}else if(dto.getMem()==34){
			obj.put("mem", "3~4명");
		}else if(dto.getMem()==58){
			obj.put("mem", "5~8명");
		}else if(dto.getMem()==8){
			obj.put("mem", "8명 이상");
		}else if(dto.getMem()==0){
			obj.put("mem", "선택안함");
		}
	
		if(dto.getAge()==0){
			obj.put("age", "선택안함");
		}else{
			obj.put("age", dto.getAge()+"대");
		}
	
		obj.put("destination", dto.getDestination());
		obj.put("profilephoto", dto.getProfilephoto());
		
		
		return obj;
	}

}
