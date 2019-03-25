package ajax;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dao.TripDAO;

import java.net.URLDecoder;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ScheduleSaveAction{

public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("UTF-8");
		System.out.println("ScheduleSaveAction 실행");
		TripDAO tripDAO = new TripDAO();
		HttpSession session = request.getSession();
		String userID = (String)session.getAttribute("userID");
		
		String departDay = request.getParameter("departDay");
		System.out.println("출발일" + departDay);
		LocalDate ldepartDay = LocalDate.parse(departDay);  
				
		int planNo = 0 ;
				
		if(request.getParameter("planNo") != null){
			
			try {
				planNo = Integer.parseInt(request.getParameter("planNo"));
				tripDAO.deleteschedule(planNo); // 계획 삭제 
				System.out.println("전달 받은 여행 계획 번호 :"+planNo);
			} catch (Exception e) {
				System.out.println("여행 일 수 계산 오류");
				e.printStackTrace();
			}
		}else{ // request로 planNo를 받지 않으면 ...
			
			planNo = tripDAO.findplanNo(userID); // 계획 번호 
			System.out.println("새로 받은 여행 계획 번호 :"+planNo);

		}

		String note = null;
		if(request.getParameter("note") != null){
		note = URLDecoder.decode(request.getParameter("note"), "UTF-8");
		//System.out.println("note :: "+note); // 파싱한 html 구문
		}
	

		try {
			Document doc = Jsoup.parse(note);
			// 3. 목록(배열)에서 정보를 가져온다.

			Elements elements1 = doc.select(".view1"); // <li class="view 부분 가져오기
			int idx1 = 0;
			System.out.println("DAY1 일정");

			for (Element element : elements1) {
				//System.out.println(++idx1 + " : " + element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx1 + " : " + subStr);
				//DAY1 일정  삽입 함수(계획번호, DAY번호, 일정번호, 일정명 )
				LocalDate ldate = ldepartDay.plusDays(0);
				String date = ldate.toString();

				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx1, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
								
				/*webdata1.add(subStr);
				for(int i = 0; i< webdata1.size(); i++){
					//DAY1 일정  삽입 함수(계획번호, DAY번호, 일정번호, 일정명 )
					System.out.println(i+ " : " + webdata1.get(i));

				}*/
			}
			
			Elements elements2 = doc.select(".view2");
			int idx2 = 0;
			System.out.println("DAY2 일정");
			for (Element element : elements2) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx2 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(1);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx2, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
				
			}
			
			Elements elements3 = doc.select(".view3");
			int idx3 = 0;
			System.out.println("DAY3 일정");
			for (Element element : elements3) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx3 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(2);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx3, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
			}
			
			Elements elements4 = doc.select(".view4");
			int idx4 = 0;
			System.out.println("DAY4 일정");
			for (Element element : elements4) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx4 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(3);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx4, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
			}
			
			Elements elements5 = doc.select(".view5");
			int idx5 = 0;
			System.out.println("DAY5 일정");
			for (Element element : elements5) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx5 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(4);
				String date = ldate.toString();
				
				int result = 0;
				result = tripDAO.schedule(planNo,date, ++idx5, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
			}
			
			Elements elements6 = doc.select(".view6");
			int idx6 = 0;
			System.out.println("DAY6 일정");
			for (Element element : elements6) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx6 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(5);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx6, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
			}
			
			Elements elements7 = doc.select(".view7");
			int idx7 = 0;
			System.out.println("DAY7 일정");
			for (Element element : elements7) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx7 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(6);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx7, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
			}
			
			Elements elements8 = doc.select(".view8");
			int idx8 = 0;
			System.out.println("DAY8 일정");
			for (Element element : elements8) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx8 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(7);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx8, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
			}
			
			Elements elements9 = doc.select(".view9");
			int idx9 = 0;
			System.out.println("DAY9 일정");
			for (Element element : elements9) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx9 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(8);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx9, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
				
			}
			
			Elements elements10 = doc.select(".view10");
			int idx10 = 0;
			System.out.println("DAY10 일정");
			for (Element element : elements10) {
				//webdata.add(element.toString());
				int po = element.toString().indexOf(">");
				int po2 = element.toString().indexOf("</li>");
				String subStr = element.toString().substring(po + 1, po2);
				//System.out.println(++idx10 + " : " + subStr);
				LocalDate ldate = ldepartDay.plusDays(9);
				String date = ldate.toString();
				int result = 0;
				result = tripDAO.schedule(planNo, date, ++idx10, subStr); // 일단 저장
				if(result == -1){
					System.out.println("schedule()함수 망함 빌어 먹을 ㅠㅠ");
					
				}else{
					System.out.println("schedule()함수 정상 작동 함 히히히힣");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}