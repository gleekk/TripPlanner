package webinfo;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import vo.WeatherDTO;

public class weather {

	String URL = "https://weather.naver.com/world/worldWetrCity.nhn?worldRgnCd=";
	String placeCodes = null;
	String days = null;
	String imgs = null;
	String weathers = null;
	String lows = null;
	String highs = null;
	Document doc = null;
	String name = null;
	WeatherDTO dto = null;
	
	public ArrayList<WeatherDTO> test(String key) {

		if (key.equals("오사카")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("후쿠오카")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("나고야")) {
			placeCodes = "WDJPN00026";
		} else if (key.equals("부산")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("런던")) {
			placeCodes = "WDGBR00057";
		} else if (key.equals("타이페이")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("서울")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("대구")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("파리")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("베를린")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("바르셀로나")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("로마")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("베니스")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("취리히")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("니스")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("뮌헨")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("프랑크푸르트")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("마드리드")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("피렌체")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("암스테르담")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("아테네")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("리스본")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("스톡홀름")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("삿포로")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("교토")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("신주쿠")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("시부야")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("방콕")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("싱가포르")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("하노이")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("호치민")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("홍콩")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("상하이")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("베이징")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("광저우")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("마이애미")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("로스앤젤레스")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("라스베이거스")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("시카고")) {
			placeCodes = "WDJPN00119";
		} else if (key.equals("괌")) {
			placeCodes = "WDGUM00002";
		}

		else {
			return null;
		}

		ArrayList<WeatherDTO> weatherDTO = new ArrayList<WeatherDTO>();
		// first day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			Elements day = doc.select("thead tr th:nth-child(1) span "); // 날짜 정보
			for(Element e: day){
				days = e.text();
			}	
				
			Elements img = doc.select("tbody tr td:nth-child(1) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
			Elements weather = doc.select("tbody tr td:nth-child(1) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();		
			Elements low = doc.select("tbody tr td:nth-child(1) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();		
			Elements high = doc.select("tbody tr td:nth-child(1) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		
		// second day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			
			Elements day = doc.select("thead tr th:nth-child(2) span "); // 날짜 정보
				days = day.text();
			
			Elements img = doc.select("tbody tr td:nth-child(2) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
			
			Elements weather = doc.select("tbody tr td:nth-child(2) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();
			
			Elements low = doc.select("tbody tr td:nth-child(2) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();
			
			Elements high = doc.select("tbody tr td:nth-child(2) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();
			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		// third day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			
			Elements day = doc.select("thead tr th:nth-child(3) span "); // 날짜 정보
				days = day.text();
		
			Elements img = doc.select("tbody tr td:nth-child(3) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
		
			Elements weather = doc.select("tbody tr td:nth-child(3) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();
		
			Elements low = doc.select("tbody tr td:nth-child(3) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();
		
			Elements high = doc.select("tbody tr td:nth-child(3) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();
			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		// 4 day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			
			Elements day = doc.select("thead tr th:nth-child(4) span "); // 날짜 정보
				days = day.text();
		
			Elements img = doc.select("tbody tr td:nth-child(4) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
		
			Elements weather = doc.select("tbody tr td:nth-child(4) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();
		
			Elements low = doc.select("tbody tr td:nth-child(4) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();
		
			Elements high = doc.select("tbody tr td:nth-child(4) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();
			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		
		// 5 day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			
			Elements day = doc.select("thead tr th:nth-child(5) span "); // 날짜 정보
				days = day.text();
		
			Elements img = doc.select("tbody tr td:nth-child(5) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
		
			Elements weather = doc.select("tbody tr td:nth-child(5) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();
		
			Elements low = doc.select("tbody tr td:nth-child(5) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();
		
			Elements high = doc.select("tbody tr td:nth-child(5) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();
			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		
		// 6 day
		try {
			doc = Jsoup.connect(URL+placeCodes).get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally{
			
			Elements day = doc.select("thead tr th:nth-child(6) span "); // 날짜 정보
				days = day.text();
		
			Elements img = doc.select("tbody tr td:nth-child(6) .wk_inr .icon img"); // 이미지
				imgs = img.attr("src");
		
			Elements weather = doc.select("tbody tr td:nth-child(6) .wk_inr dl dt "); // 날씨정보
				weathers = weather.text();
		
			Elements low = doc.select("tbody tr td:nth-child(6) .wk_inr dl dd:nth-child(2) em"); // 최저온도
				lows = low.text();
		
			Elements high = doc.select("tbody tr td:nth-child(6) .wk_inr dl dd:nth-child(3) em"); // 최고온도
				highs = high.text();
			
			dto = new WeatherDTO(name, imgs, days, weathers, lows, highs);
			weatherDTO.add(dto);
		}
		
		
return weatherDTO;
	}
}
