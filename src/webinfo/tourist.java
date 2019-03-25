package webinfo;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class tourist {

	String URL = null;

	public ArrayList<String> test(String key) {

		if (key.equals("오사카")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298566-Activities-Osaka_Osaka_Prefecture_Kinki.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("후쿠오카")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298207-Activities-Fukuoka_Fukuoka_Prefecture_Kyushu.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("나고야")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298106-Activities-Nagoya_Aichi_Prefecture_Tokai_Chubu.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("부산")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g297884-Activities-Busan.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("런던")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g186338-Activities-London_England.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("타이페이")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g293913-Activities-Taipei.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("서울")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g294197-Activities-Seoul.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("대구")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g297886-Activities-Daegu.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("파리")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187147-Activities-Paris_Ile_de_France.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("베를린")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187323-Activities-Berlin.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("바르셀로나")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187497-Activities-Barcelona_Catalonia.html";
		} else if (key.equals("로마")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187791-Activities-Rome_Lazio.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("베니스")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187870-Activities-Venice_Veneto.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("취리히")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g188113-Activities-Zurich.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("니스")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187234-Activities-Nice_French_Riviera_Cote_d_Azur_Provence_Alpes_Cote_d_Azur.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("뮌헨")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187309-Activities-Munich_Upper_Bavaria_Bavaria.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("프랑크푸르트")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187337-Activities-Frankfurt_Hesse.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("마드리드")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187514-Activities-Madrid.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("피렌체")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g187895-Activities-Florence_Tuscany.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("암스테르담")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g188590-Activities-Amsterdam_North_Holland_Province.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("아테네")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g189400-Activities-Athens_Attica.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("리스본")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g189158-Activities-Lisbon_Lisbon_District_Central_Portugal.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("스톡홀름")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g189852-Activities-Stockholm.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("삿포로")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298560-Activities-Sapporo_Hokkaido.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("교토")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298564-Activities-Kyoto_Kyoto_Prefecture_Kinki.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("신주쿠")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g1066457-Activities-Shinjuku_Tokyo_Tokyo_Prefecture_Kanto.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("시부야")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g1066456-Activities-Shibuya_Tokyo_Tokyo_Prefecture_Kanto.html";
		} else if (key.equals("방콕")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g293916-Activities-Bangkok.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("싱가포르")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g294265-Activities-Singapore.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("하노이")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g293924-Activities-Hanoi.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("호치민")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g293925-Activities-Ho_Chi_Minh_City.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("홍콩")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g294217-Activities-Hong_Kong.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("상하이")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g308272-Activities-Shanghai.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("베이징")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g294212-Activities-Beijing.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("광저우")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g298555-Activities-Guangzhou_Guangdong.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("마이애미")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g34438-Activities-Miami_Florida.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("로스앤젤레스")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g32655-Activities-Los_Angeles_California.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("라스베이거스")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g45963-Activities-Las_Vegas_Nevada.html#ATTRACTION_SORT_WRAPPER";
		} else if (key.equals("시카고")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g35805-Activities-Chicago_Illinois.html";
		} else if (key.equals("괌")) {
			URL = "https://www.tripadvisor.co.kr/Attractions-g60668-Activities-Guam.html#ATTRACTION_SORT_WRAPPER";
		}

		else {
			return null;
		}

		// 1. Document를 가져온다.
		// 2. 목록을 가져온다.
		ArrayList<String> webdata = new ArrayList<String>();
		try {
			Document doc = Jsoup.connect(URL).get();
			Elements elements = doc.select(".listing_title > a");
			// 3. 목록(배열)에서 정보를 가져온다.
			//int idx = 0;
			for (Element element : elements) {
				webdata.add(element.toString());
				//System.out.println("===========================");
				//System.out.println(++idx + " : " + element.toString());
				//System.out.println("===========================");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> webdata2 = new ArrayList<String>();

		for (int j = 0; j < 30; j++) {
			String web = webdata.get(j);

			String newweb = web.replaceAll("/Attraction", "https://www.tripadvisor.co.kr/Attraction");
			int po = newweb.indexOf(">");
			int po2 = newweb.indexOf("</a>");
			String subStr = newweb.substring(po + 1, po2);// 여행지명 가져오기 
			String link = newweb.substring(0, po + 1);
			String linkn = link.replaceAll(">", " target='_blank'> ");

			webdata2.add(j + 1 + ". " + "<b>" + subStr + "</b>" +linkn + "자세히 보기</a><br>");

		}

		return webdata2;
	}
}
