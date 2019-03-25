package textmining;


import java.util.ArrayList;
import java.util.List;

import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;
import org.snu.ids.kkma.util.Timer;

import dao.AnalysisDAO;

public class Textmining {

	public void Textmining() {
		// TODO Auto-generated method stub
		Timer timer = new Timer();
		timer.start();

		AnalysisDAO mydao = new AnalysisDAO();
		//mydao.droptable(); // 기존 테이블 삭제
		//mydao.createtable(); // 테이블 생성
		mydao.empty(); // 테이블 비우기

		
		ArrayList<String> texta = mydao.getText(); // myboard테이블에 있는 글 내용 모두 가져오기 
		
		List<String> list = texta;

		StringBuilder sb = new StringBuilder();
		for (String s : list) {
		  sb.append(s);
		}
		
		String contents = sb.toString();
		
		String strToExtrtKwrd = contents;
		// init KeywordExtractor
		KeywordExtractor ke = new KeywordExtractor();
		// extract keywords
		KeywordList kl = ke.extractKeyword(strToExtrtKwrd, true);
		// print result
		int[] r = null ;
		for( int i = 0; i < kl.size(); i++ ) {
		    Keyword kwrd = kl.get(i);
		    String key = kwrd.getString();
		    int value = kwrd.getCnt();
		     mydao.insertvalue(key, value); // 각 단어와 개수 저장
		}
		
		timer.stop();
        timer.printMsg("Time");

		kl.clear();
		
		
	}

	
}
