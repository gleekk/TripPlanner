package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.ChatDTO;

public class ChatDAO {
	
	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;
	
	public ChatDAO() {

		try {
			// 1.Was서버와 연결된 웹프로젝트의 모든정보를 가지고 있는 컨텍스트객체 생성
			Context init = new InitialContext();

			// 2.연결된 Was서버에서 DataSource(커넥션풀)을 검색해서 얻기
			ds = (DataSource) init.lookup("java:comp/env/jdbc/itwillbs8");

		} catch (Exception e) {
			System.out.println("커넥션풀 가져오기 실패 : " + e);
			e.printStackTrace();
		}

	}
	
	// 리소스 반납 (해제)메소드
		public void freeResource() {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception err) {
					err.printStackTrace();
				}
			}
		}
		
		
		public ArrayList<ChatDTO> getChatListByID(String fromID, String toID, String chatID){
			ArrayList<ChatDTO> chatList = null;
			
			String sql = "SELECT * FROM chat WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > ? ORDER BY chatTime";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, fromID);
				pstmt.setString(2, toID);				
				pstmt.setString(3, toID);				
				pstmt.setString(4, fromID);				
				pstmt.setInt(5, Integer.parseInt(chatID));					
				rs = pstmt.executeQuery();
				chatList = new ArrayList<ChatDTO>();
				while(rs.next()){
					ChatDTO chat = new ChatDTO();
					chat.setChatID(rs.getInt("chatID"));
					chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
					String timeType = "오전";
					if(chatTime >= 12) {
						timeType = "오후";
						chatTime -= 12;
					}
					chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
					chatList.add(chat);
				}
			} catch (Exception e) {
				System.out.println("getChatListByID()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return chatList; // 데이터베이스 오류
		}
		
		
		
		public ArrayList<ChatDTO> getChatListByRecent(String fromID, String toID, int number){
			ArrayList<ChatDTO> chatList = null;
			
			String sql = "SELECT * FROM chat WHERE ((fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?)) AND chatID > (SELECT MAX(chatID) - ? FROM chat WHERE(fromID = ? AND toID = ?) OR (fromID = ? AND toID = ?) ) ORDER BY chatTime";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, fromID);
				pstmt.setString(2, toID);				
				pstmt.setString(3, toID);				
				pstmt.setString(4, fromID);				
				pstmt.setInt(5, number);
				pstmt.setString(6, fromID);
				pstmt.setString(7, toID);				
				pstmt.setString(8, toID);				
				pstmt.setString(9, fromID);	
				rs = pstmt.executeQuery();
				chatList = new ArrayList<ChatDTO>();
				while(rs.next()){
					ChatDTO chat = new ChatDTO();
					chat.setChatID(rs.getInt("chatID"));
					chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
					String timeType = "오전";
					if(chatTime >= 12) {
						timeType = "오후";
						chatTime -= 12;
					}
					chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
					chatList.add(chat);
				}
			} catch (Exception e) {
				System.out.println("getChatListByRecent()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return chatList; // 데이터베이스 오류
		}
		
		public ArrayList<ChatDTO> getBox(String userID){
			ArrayList<ChatDTO> chatList = null;
			
			String sql = "SELECT * FROM chat WHERE chatID IN (SELECT MAX(chatID) FROM chat WHERE toID = ? OR fromID = ? GROUP BY fromID, toID)";
			
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				pstmt.setString(2, userID);				
			
				rs = pstmt.executeQuery();
				chatList = new ArrayList<ChatDTO>();
				while(rs.next()){
					ChatDTO chat = new ChatDTO();
					chat.setChatID(rs.getInt("chatID"));
					chat.setFromID(rs.getString("fromID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setToID(rs.getString("toID").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					chat.setChatContent(rs.getString("chatContent").replaceAll(" ", "&nbsp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					int chatTime = Integer.parseInt(rs.getString("chatTime").substring(11, 13));
					String timeType = "오전";
					if(chatTime >= 12) {
						timeType = "오후";
						chatTime -= 12;
					}
					chat.setChatTime(rs.getString("chatTime").substring(0, 11) + " " + timeType + " " + chatTime + ":" + rs.getString("chatTime").substring(14, 16) + "");
					chatList.add(chat);
				}
				for(int i =0; i<chatList.size(); i++){
					ChatDTO x = chatList.get(i);
					for(int j = 0; j<chatList.size(); j++ ){
						ChatDTO y = chatList.get(j);
						if(x.getFromID().equals(y.getToID()) && x.getToID().equals(y.getFromID())){
							if(x.getChatID() < y.getChatID()){
								chatList.remove(x);
								i--;
								break;
							}else{
								chatList.remove(y);
								j--;
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("getBox1()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return chatList; // 데이터베이스 오류
		}
	
		public int submit(String fromID, String toID, String chatContent){
			
			String sql = "INSERT INTO chat VALUES (NULL, ?, ?, ?, NOW(), 0)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, fromID);
				pstmt.setString(2, toID);				
				pstmt.setString(3, chatContent);	
				return pstmt.executeUpdate();

			} catch (Exception e) {
				System.out.println("submit()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
		public int readChat(String fromID, String toID){
			
			String sql = "UPDATE chat SET chatRead = 1 WHERE (fromID = ? AND toID = ?)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, toID);
				pstmt.setString(2, fromID);		
				
				return pstmt.executeUpdate();

			} catch (Exception e) {
				System.out.println("readChat()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
		public int getAllUnreadChat(String userID){
			
			String sql = "SELECT COUNT(chatID) FROM chat WHERE toID = ? AND chatRead = 0";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()){
					return rs.getInt("COUNT(chatID)");
				}
				return 0 ;
			} catch (Exception e) {
				System.out.println("getAllUnreadChat()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
		
			public int getUnreadChat(String fromID, String toID){
			
			String sql = "SELECT COUNT(chatID) FROM chat WHERE fromID = ? AND toID = ? AND chatRead = 0";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, fromID);
				pstmt.setString(2, toID);
				rs = pstmt.executeQuery();
				if(rs.next()){
					return rs.getInt("COUNT(chatID)");
				}
				return 0 ;
			} catch (Exception e) {
				System.out.println("getUnreadChat()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
}	