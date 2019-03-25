package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.CityDTO;
import vo.MyBoardDTO;
import vo.MyCommentsDTO;

public class MyBoardDAO {

	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;

	public MyBoardDAO() {

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


	public int write(String userID, String myboardTitle, String myboardContent, String myboardFile, String myboardRealFile, String mycategory, String myplan, String myvisibility) {
		String sql = "INSERT INTO myboard SELECT ?, IFNULL((SELECT MAX(myboardID) + 1 from myboard), 1), ?, ?, now(), 0, ?, ?, IFNULL((SELECT MAX(myboardGroup) + 1 FROM myboard), 0), 0, 0, 1, 0, ?, ?, ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, myboardTitle);
			pstmt.setString(3, myboardContent);
			pstmt.setString(4, myboardFile);
			pstmt.setString(5, myboardRealFile);
			pstmt.setString(6, mycategory);
			pstmt.setString(7, myvisibility);
			pstmt.setString(8, myplan);

			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("myregister()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public MyBoardDTO getBoard(String myboardID) {
		MyBoardDTO board = new MyBoardDTO();
		String sql = "SELECT * FROM myboard WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myboardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardID(rs.getShort("myboardID"));
				board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardContent(rs.getString("myboardContent"));
				board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
				board.setMyboardHit(rs.getInt("myboardHit"));
				board.setMyboardFile(rs.getString("myboardFile"));
				board.setMyboardRealFile(rs.getString("myboardRealFile"));
				board.setMyboardGroup(rs.getInt("myboardGroup"));
				board.setMyboardSequence(rs.getInt("myboardSequence"));
				board.setMyboardLevel(rs.getInt("myboardLevel"));
				board.setMyboardAvailable(rs.getInt("myboardAvailable"));
				board.setMylikeCount(rs.getInt("mylikeCount"));
				board.setMycategory(rs.getString("mycategory"));
				board.setMyvisibility(rs.getString("myvisibility"));
				board.setMyplan(rs.getString("myplan"));

			}
		} catch (Exception e) {
			System.out.println("mygetUser()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return board; // 데이터베이스 오류
	}

	public ArrayList<MyBoardDTO> getList(String pageNumber) {
		ArrayList<MyBoardDTO> boardList = null;
		String sql = "SELECT * FROM myboard WHERE myboardGroup >(SELECT MAX(myboardGroup) From myboard) - ? AND myboardGroup <= (SELECT MAX(myboardGroup) FROM myboard) - ? ORDER BY myboardGroup DESC, myboardSequence ASC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
			pstmt.setInt(2, (Integer.parseInt(pageNumber) - 1) * 10);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<MyBoardDTO>();
			while (rs.next()) {
				MyBoardDTO board = new MyBoardDTO();
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardID(rs.getShort("myboardID"));
				board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardContent(rs.getString("myboardContent"));
				board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
				board.setMyboardHit(rs.getInt("myboardHit"));
				board.setMyboardFile(rs.getString("myboardFile"));
				board.setMyboardRealFile(rs.getString("myboardRealFile"));
				board.setMyboardGroup(rs.getInt("myboardGroup"));
				board.setMyboardSequence(rs.getInt("myboardSequence"));
				board.setMyboardLevel(rs.getInt("myboardLevel"));
				board.setMyboardAvailable(rs.getInt("myboardAvailable"));
				board.setMylikeCount(rs.getInt("mylikeCount"));
				board.setMycategory(rs.getString("mycategory"));
				board.setMyvisibility(rs.getString("myvisibility"));
				board.setMyplan(rs.getString("myplan"));

				boardList.add(board);
			}
		} catch (Exception e) {
			System.out.println("mygetList()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return boardList; // 데이터베이스 오류
	}

	public boolean nextPage(String pageNumber) {
		String sql = "SELECT * FROM myboard WHERE myvisibility = 'o' and myboardGroup >= ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("mynextPage()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return false;

	}

	public int targetPage(String pageNumber, String search) {
		String sql = "SELECT COUNT(myboardGroup) FROM myboard WHERE CONCAT(myboardTitle, myboardContent, myboardFile, userID, mycategory) LIKE ? and myvisibility = 'o'";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + search + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return (((rs.getInt(1)-1) / 10)-(Integer.parseInt(pageNumber) - 1));
			}
		} catch (Exception e) {
			System.out.println("mytargetPage()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return 0;

	}

	public int hit(String myboardID) {
		String sql = "update myboard set myboardHit =myboardHit + 1 where myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myboardID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("myhit()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public String getFile(String myboardID) {
		String sql = "SELECT myboardFile FROM myboard WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myboardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("myboardFile");
			}
			return "";
		} catch (Exception e) {
			System.out.println("mygetFile()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return "";

	}

	public String getRealFile(String myboardID) {
		String sql = "SELECT myboardRealFile FROM myboard WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myboardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("myboardRealFile");
			}
			return "";
		} catch (Exception e) {
			System.out.println("mygetRealFile()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return "";

	}
	public int update(String myboardID, String myboardTitle, String myboardContent, String myboardFile, String myboardRealFile, String myplan, String mycategory, String myvisibility) {
		String sql = "UPDATE myboard SET myboardTitle = ?,  myboardContent = ?, myboardFile = ?, myboardRealFile = ?, myplan=? , mycategory=?, myvisibility=? WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, myboardTitle);
			pstmt.setString(2, myboardContent);
			pstmt.setString(3, myboardFile);
			pstmt.setString(4, myboardRealFile);
			pstmt.setString(5, myplan);
			pstmt.setString(6, mycategory);
			pstmt.setString(7, myvisibility);
			pstmt.setInt(8, Integer.parseInt(myboardID));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("myupdate()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public int delete(String myboardID) {
		String sql = "update myboard set myboardAvailable = 0 WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(myboardID));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("mydelete()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	

	public ArrayList<MyBoardDTO> getSearch(String pageNumber, String searchType, String search) {
		ArrayList<MyBoardDTO> boardList = null;
		String sql = "";

		if (searchType.equals("최신순")) {
			sql = "SELECT * FROM myboard WHERE CONCAT(myboardTitle, myboardContent, myboardFile, userID, mycategory) LIKE ? and myvisibility = 'o' "
					+ "ORDER BY myboardDate DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<MyBoardDTO>();
				while (rs.next()) {
					MyBoardDTO board = new MyBoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardID(rs.getShort("myboardID"));
					board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardContent(rs.getString("myboardContent"));
					board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
					board.setMyboardHit(rs.getInt("myboardHit"));
					board.setMyboardFile(rs.getString("myboardFile"));
					board.setMyboardRealFile(rs.getString("myboardRealFile"));
					board.setMyboardGroup(rs.getInt("myboardGroup"));
					board.setMyboardSequence(rs.getInt("myboardSequence"));
					board.setMyboardLevel(rs.getInt("myboardLevel"));
					board.setMyboardAvailable(rs.getInt("myboardAvailable"));
					board.setMylikeCount(rs.getInt("mylikeCount"));
					board.setMycategory(rs.getString("mycategory"));
					board.setMyplan(rs.getString("myplan"));

					boardList.add(board);
				}

			} catch (Exception e) {
				System.out.println("getSearch()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}

		} else if (searchType.equals("조회수")) {
			sql = "SELECT * FROM myboard WHERE CONCAT(myboardTitle, myboardContent, myboardFile, userID, mycategory) LIKE ? and myvisibility = 'o' "
					+ "ORDER BY myboardHit DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<MyBoardDTO>();
				while (rs.next()) {
					MyBoardDTO board = new MyBoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardID(rs.getShort("myboardID"));
					board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardContent(rs.getString("myboardContent"));
					board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
					board.setMyboardHit(rs.getInt("myboardHit"));
					board.setMyboardFile(rs.getString("myboardFile"));
					board.setMyboardRealFile(rs.getString("myboardRealFile"));
					board.setMyboardGroup(rs.getInt("myboardGroup"));
					board.setMyboardSequence(rs.getInt("myboardSequence"));
					board.setMyboardLevel(rs.getInt("myboardLevel"));
					board.setMyboardAvailable(rs.getInt("myboardAvailable"));
					board.setMylikeCount(rs.getInt("mylikeCount"));
					board.setMycategory(rs.getString("mycategory"));
					board.setMyplan(rs.getString("myplan"));

					boardList.add(board);
				}

			} catch (Exception e) {
				System.out.println("getSearch()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}

		} else if (searchType.equals("추천순")) {
			sql = "SELECT * FROM myboard WHERE CONCAT(myboardTitle, myboardContent, myboardFile, userID, mycategory) LIKE ? and myvisibility = 'o' "
					+ "ORDER BY mylikeCount DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<MyBoardDTO>();
				while (rs.next()) {
					MyBoardDTO board = new MyBoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardID(rs.getShort("myboardID"));
					board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setMyboardContent(rs.getString("myboardContent"));
					board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
					board.setMyboardHit(rs.getInt("myboardHit"));
					board.setMyboardFile(rs.getString("myboardFile"));
					board.setMyboardRealFile(rs.getString("myboardRealFile"));
					board.setMyboardGroup(rs.getInt("myboardGroup"));
					board.setMyboardSequence(rs.getInt("myboardSequence"));
					board.setMyboardLevel(rs.getInt("myboardLevel"));
					board.setMyboardAvailable(rs.getInt("myboardAvailable"));
					board.setMylikeCount(rs.getInt("mylikeCount"));
					board.setMycategory(rs.getString("mycategory"));
					board.setMyplan(rs.getString("myplan"));

					boardList.add(board);
				}

			} catch (Exception e) {
				System.out.println("getSearch()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}

		}

		return boardList; // 데이터베이스 오류
	}
	

	public int getcomentcheck(int myboardID, String userID) {

		try {
			conn = ds.getConnection();

			String sql2 = "select COUNT(*) as count from myrecommendation where myboardID= ? and userID = ?";

			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, myboardID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			System.out.println(count);
			
			if (count >= 1) {

				return -1; // 중복됨 -1 리턴

			} else {

				String sql = "insert into myrecommendation values (?, ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, myboardID);
				pstmt.setString(2, userID);
				return pstmt.executeUpdate(); // 추천 완료 1 리턴
			}

		} catch (Exception e) {
			System.out.println("mygetcomentcheck()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return 0; // 추천중복오류
	}

	public int getcomentadd(int myboardID) {
		String sql = "UPDATE myboard SET mylikeCount = mylikeCount + 1 WHERE myboardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, myboardID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("mylike()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public int Comment(String userID, int myboardID, String mybcomment) {
		String sql = "INSERT INTO mycomments  VALUES (0, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setInt(2, myboardID);			
			pstmt.setString(3, mybcomment);			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("myComment()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}
	
	
	public ArrayList<MyCommentsDTO> getComment(String myboardID) {
		ArrayList<MyCommentsDTO> commentList = null;
		String sql = "SELECT * FROM mycomments WHERE myboardID = ?  ORDER BY mycommentsNo DESC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(myboardID));
			rs = pstmt.executeQuery();
			commentList = new ArrayList<MyCommentsDTO>();
			while (rs.next()) {
				MyCommentsDTO comments = new MyCommentsDTO();
				comments.setMycommentsNo(rs.getInt("mycommentsNo"));
				comments.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				comments.setMyboardID(rs.getInt("myboardID"));
				comments.setMybcomment(rs.getString("mybcomment").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				commentList.add(comments);
			}
		} catch (Exception e) {
			System.out.println("mygetComment()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return commentList; // 데이터베이스 오류
	}

	public void deleteComment(int mycommentsNo) {
		String sql = "delete from mycomments where mycommentsNo = ?";	
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, mycommentsNo);
				pstmt.executeUpdate();
				} catch (Exception e) {
					System.out.println("mydeleteComment()메소드 내부에서 오류 : " + e);
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					freeResource();
				}
			}

	public ArrayList<MyBoardDTO> getmyList(String pageNumber, String userID) {
		ArrayList<MyBoardDTO> boardList = null;
		String sql = "SELECT * FROM myboard WHERE userID = ? and myboardAvailable = 1 ORDER BY myboardGroup DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 9 + ", " + 9;
	
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<MyBoardDTO>();
			while (rs.next()) {
				MyBoardDTO board = new MyBoardDTO();
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardID(rs.getShort("myboardID"));
				board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardContent(rs.getString("myboardContent"));
				board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
				board.setMyboardHit(rs.getInt("myboardHit"));
				board.setMyboardFile(rs.getString("myboardFile"));
				board.setMyboardRealFile(rs.getString("myboardRealFile"));
				board.setMyboardGroup(rs.getInt("myboardGroup"));
				board.setMyboardSequence(rs.getInt("myboardSequence"));
				board.setMyboardLevel(rs.getInt("myboardLevel"));
				board.setMyboardAvailable(rs.getInt("myboardAvailable"));
				board.setMylikeCount(rs.getInt("mylikeCount"));
				board.setMycategory(rs.getString("mycategory"));
				board.setMyvisibility(rs.getString("myvisibility"));
				board.setMyplan(rs.getString("myplan"));

				boardList.add(board);
			}
		} catch (Exception e) {
			System.out.println("mygetList()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return boardList; // 데이터베이스 오류
	}
	
	public ArrayList<MyBoardDTO> getmyList2(String pageNumber, String userID) {
		ArrayList<MyBoardDTO> boardList = null;
		String sql = "SELECT * FROM myboard WHERE userID = ? and myvisibility = 'o' and myboardAvailable = 1 ORDER BY myboardGroup DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 9 + ", " + 9;
	
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<MyBoardDTO>();
			while (rs.next()) {
				MyBoardDTO board = new MyBoardDTO();
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardID(rs.getShort("myboardID"));
				board.setMyboardTitle(rs.getString("myboardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setMyboardContent(rs.getString("myboardContent"));
				board.setMyboardDate(rs.getString("myboardDate").substring(0, 10));
				board.setMyboardHit(rs.getInt("myboardHit"));
				board.setMyboardFile(rs.getString("myboardFile"));
				board.setMyboardRealFile(rs.getString("myboardRealFile"));
				board.setMyboardGroup(rs.getInt("myboardGroup"));
				board.setMyboardSequence(rs.getInt("myboardSequence"));
				board.setMyboardLevel(rs.getInt("myboardLevel"));
				board.setMyboardAvailable(rs.getInt("myboardAvailable"));
				board.setMylikeCount(rs.getInt("mylikeCount"));
				board.setMycategory(rs.getString("mycategory"));
				board.setMyvisibility(rs.getString("myvisibility"));
				board.setMyplan(rs.getString("myplan"));

				boardList.add(board);
			}
		} catch (Exception e) {
			System.out.println("mygetList()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return boardList; // 데이터베이스 오류
	}

	public boolean checktarget(String pageNumber, String userID) {
		
		String sql = "SELECT COUNT(myboardGroup) FROM myboard WHERE userID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.getInt(1) > Integer.parseInt(pageNumber)*9) {
					
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("checktarget()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return false;

	}

	public String doublecheck() {
		
		String sql = "SELECT myboardContent FROM myboard ORDER BY myboardid DESC limit 1";
		String preContent = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				preContent = rs.getString(1);

			}
		} catch (Exception e) {
			System.out.println("doublecheck()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	return preContent;	
	}

//
	
}