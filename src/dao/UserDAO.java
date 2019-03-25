package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import aes256.AES256Util;
public class UserDAO {
	
	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;
	
	public UserDAO() {

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
		// 로그인
		public int login(String userID, String userPassword){
			String sql = "SELECT * FROM user WHERE userID = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()){
					String key = "aes256-test-key!!";       // key는 16자 이상
			        AES256Util aes256 = new AES256Util(key);
			       String encText = rs.getString("userPassword");
			       String aesPassword = aes256.aesDecode(encText);

					if(aesPassword.equals(userPassword)){
						return 1; //로그인 성공
					}else{
						return 2; // 비밀번호 틀림
					}
				}return 0; //아이디 없음
			} catch (Exception e) {
				System.out.println("login()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
		// 아이디 중복 체크
		public int registerCheck(String userID){
			
			String sql = "SELECT * FROM user WHERE userID = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();

				if(rs.next() || userID.equals("")){

					return 0; // 이미 존재하는 회원 
				}else {

					return 1; // 가입 가능한 회원 아이디
				}
			} catch (Exception e) {
				System.out.println("registerCheck()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return -1; // 데이터베이스 오류
		}
		
		// userID 정보 가져오기
		public vo.UserDTO getUser(String userID){
			vo.UserDTO user = new vo.UserDTO();
			String sql = "SELECT * FROM user WHERE userID = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()){
					user.setUserID(userID);
					user.setUserPassword(rs.getString("userPassword"));
					user.setUserName(rs.getString("userName").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					user.setUserAge(rs.getInt("userAge"));
					user.setUserGender(rs.getString("userGender"));
					user.setUserEmail(rs.getString("userEmail").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					user.setUserProfile(rs.getString("userProfile"));
					user.setUserMotto(rs.getString("userMotto").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));

				}
			} catch (Exception e) {
				System.out.println("getUser()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return user; // 데이터베이스 오류
		}
		
		// 회원가입
		public int register(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userProfile, String userMotto ) throws Exception{
			String sql = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			String key = "aes256-test-key!!";       // key는 16자 이상
	        AES256Util aes256 = new AES256Util(key);
	        String text = userPassword;
	        String aesPassword = aes256.aesEncode(text); // 암호화
			
			
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				pstmt.setString(2, aesPassword);
				pstmt.setString(3, userName);
				pstmt.setInt(4, Integer.parseInt(userAge));
				pstmt.setString(5, userGender);
				pstmt.setString(6, userEmail);
				pstmt.setString(7, userProfile);
				pstmt.setString(8, userMotto);

				return pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("register()메소드 내부에서 오류 " + e);
				e.printStackTrace();		
		}finally{
			freeResource();
		}return -1; //데이터베이스 오류
		}
		
		// 회원 수정
		public int update(String userID, String userPassword, String userName, String userAge, String userGender, String userEmail, String userMotto){
			
			String sql = "update user set userPassword = ?, userName = ?, userAge = ?, userGender = ?, userEmail = ?, userMotto= ? where userID = ? ";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userPassword);
				pstmt.setString(2, userName);
				pstmt.setInt(3, Integer.parseInt(userAge));
				pstmt.setString(4, userGender);
				pstmt.setString(5, userEmail);
				pstmt.setString(6, userMotto);
				pstmt.setString(7, userID);
				return pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("update()메소드 내부에서 오류 " + e);
				e.printStackTrace();		
		}finally{
			freeResource();
		}return -1; //데이터베이스 오류
		}
		
		// 프로필 수정
		public int profile(String userID, String userProfile){
		
			String sql = "update user set userProfile = ? where userID = ? ";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userProfile);
				pstmt.setString(2, userID);
				return pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("profile()메소드 내부에서 오류 " + e);
				e.printStackTrace();		
		}finally{
			freeResource();
		}return -1; //데이터베이스 오류
		}
		
		// 프로필 사진 가져오기
		public String getProfile(String userID){
			
			String sql = "SELECT userProfile FROM user WHERE userID = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();

				if(rs.next() ){
					if(rs.getString("userProfile").equals("")){
						return "./images/icon.jpg";
					}
					return "upload/" + rs.getString("userProfile");
				}
			} catch (Exception e) {
				System.out.println("getProfile()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return "./images/icon.jpg";
		}

		
	
}	