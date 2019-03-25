package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.BoardDTO;
import vo.CommentsDTO;

public class BoardDAO {

	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;

	public BoardDAO() {

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



	public int write(String userID, String boardTitle, String boardContent, String boardFile, String boardRealFile,
			String city, String tourist) {
		String sql = "INSERT INTO board SELECT ?, IFNULL((SELECT MAX(boardID) + 1 from board), 1), ?, ?, now(), 0, ?, ?, IFNULL((SELECT MAX(boardGroup) + 1 FROM board), 0), 0, 0, 1, 0, ?, ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, boardTitle);
			pstmt.setString(3, boardContent);
			pstmt.setString(4, boardFile);
			pstmt.setString(5, boardRealFile);
			pstmt.setString(6, city);
			pstmt.setString(7, tourist);
			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("register()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public BoardDTO getBoard(String boardID) {
		BoardDTO board = new BoardDTO();
		String sql = "SELECT * FROM board WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardID(rs.getShort("boardID"));
				board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate").substring(0, 10));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				board.setLikeCount(rs.getInt("likeCount"));
				board.setCity(rs.getString("city"));
				board.setTourist(rs.getString("tourist"));

			}
		} catch (Exception e) {
			System.out.println("getUser()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return board; // 데이터베이스 오류
	}

	public ArrayList<BoardDTO> getList(String pageNumber) {
		ArrayList<BoardDTO> boardList = null;
		String sql = "SELECT * FROM board WHERE boardGroup >(SELECT MAX(boardGroup) From board) - ? AND boardGroup <= (SELECT MAX(boardGroup) FROM board) - ? ORDER BY boardGroup DESC, boardSequence ASC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
			pstmt.setInt(2, (Integer.parseInt(pageNumber) - 1) * 10);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<BoardDTO>();
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardID(rs.getShort("boardID"));
				board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate").substring(0, 10));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				board.setLikeCount(rs.getInt("likeCount"));
				board.setCity(rs.getString("city"));
				board.setTourist(rs.getString("tourist"));
				boardList.add(board);
			}
		} catch (Exception e) {
			System.out.println("getUser()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return boardList; // 데이터베이스 오류
	}

	public boolean nextPage(String pageNumber) {
		String sql = "SELECT * FROM board WHERE boardGroup >= ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(pageNumber) * 10);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("nextPage()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return false;

	}

	public int hit(String boardID) {
		String sql = "update board set boardHit =boardHit + 1 where boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("hit()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public String getFile(String boardID) {
		String sql = "SELECT boardFile FROM board WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("boardFile");
			}
			return "";
		} catch (Exception e) {
			System.out.println("getFile()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return "";

	}

	public String getRealFile(String boardID) {
		String sql = "SELECT boardRealFile FROM board WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardID);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getString("boardRealFile");
			}
			return "";
		} catch (Exception e) {
			System.out.println("getRealFile()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return "";

	}

	public int update(String boardID, String boardTitle, String boardContent, String boardFile, String boardRealFile,
			String tourist) {
		String sql = "UPDATE board SET boardTitle = ?,  boardContent = ?, boardFile = ?, boardRealFile = ?, tourist=? WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardTitle);
			pstmt.setString(2, boardContent);
			pstmt.setString(3, boardFile);
			pstmt.setString(4, boardRealFile);
			pstmt.setString(5, tourist);
			pstmt.setInt(6, Integer.parseInt(boardID));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("update()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public int delete(String boardID) {
		String sql = "update board set boardAvailable = 0 WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(boardID));
			return pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("delete()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public ArrayList<BoardDTO> getSearch(String pageNumber, String searchType, String search) {
		ArrayList<BoardDTO> boardList = null;
		String sql = "";

		if (searchType.equals("최신순")) {
			sql = "SELECT * FROM board WHERE CONCAT(boardTitle, boardContent, boardFile, userID, city, tourist) LIKE ?"
					+ "ORDER BY boardGroup DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<BoardDTO>();
				while (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardID(rs.getShort("boardID"));
					board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardContent(rs.getString("boardContent"));
					board.setBoardDate(rs.getString("boardDate").substring(0, 10));
					board.setBoardHit(rs.getInt("boardHit"));
					board.setBoardFile(rs.getString("boardFile"));
					board.setBoardRealFile(rs.getString("boardRealFile"));
					board.setBoardGroup(rs.getInt("boardGroup"));
					board.setBoardSequence(rs.getInt("boardSequence"));
					board.setBoardLevel(rs.getInt("boardLevel"));
					board.setBoardAvailable(rs.getInt("boardAvailable"));
					board.setLikeCount(rs.getInt("likeCount"));
					board.setCity(rs.getString("city"));
					board.setTourist(rs.getString("tourist"));

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
			sql = "SELECT * FROM board WHERE CONCAT(boardTitle, boardContent, boardFile, userID, city, tourist) LIKE ? "
					+ "ORDER BY boardHit DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<BoardDTO>();
				while (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardID(rs.getShort("boardID"));
					board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardContent(rs.getString("boardContent"));
					board.setBoardDate(rs.getString("boardDate").substring(0, 10));
					board.setBoardHit(rs.getInt("boardHit"));
					board.setBoardFile(rs.getString("boardFile"));
					board.setBoardRealFile(rs.getString("boardRealFile"));
					board.setBoardGroup(rs.getInt("boardGroup"));
					board.setBoardSequence(rs.getInt("boardSequence"));
					board.setBoardLevel(rs.getInt("boardLevel"));
					board.setBoardAvailable(rs.getInt("boardAvailable"));
					board.setLikeCount(rs.getInt("likeCount"));
					board.setCity(rs.getString("city"));
					board.setTourist(rs.getString("tourist"));

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
			sql = "SELECT * FROM board WHERE CONCAT(boardTitle, boardContent, boardFile, userID, city, tourist) LIKE ? "
					+ "ORDER BY likeCount DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
			try {

				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");

				rs = pstmt.executeQuery();
				boardList = new ArrayList<BoardDTO>();
				while (rs.next()) {
					BoardDTO board = new BoardDTO();
					board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardID(rs.getShort("boardID"));
					board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					board.setBoardContent(rs.getString("boardContent"));
					board.setBoardDate(rs.getString("boardDate").substring(0, 10));
					board.setBoardHit(rs.getInt("boardHit"));
					board.setBoardFile(rs.getString("boardFile"));
					board.setBoardRealFile(rs.getString("boardRealFile"));
					board.setBoardGroup(rs.getInt("boardGroup"));
					board.setBoardSequence(rs.getInt("boardSequence"));
					board.setBoardLevel(rs.getInt("boardLevel"));
					board.setBoardAvailable(rs.getInt("boardAvailable"));
					board.setLikeCount(rs.getInt("likeCount"));
					board.setCity(rs.getString("city"));
					board.setTourist(rs.getString("tourist"));

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

	public int targetPage(String pageNumber, String search) {
		 String	sql = "SELECT COUNT(boardGroup)  FROM board WHERE CONCAT(boardTitle, boardContent, boardFile, userID, city, tourist) LIKE ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%" + search + "%");
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return (((rs.getInt(1)-1) / 10)-(Integer.parseInt(pageNumber) - 1));
				}
			} catch (Exception e) {
				System.out.println("getSearch()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
	
		return 0; // 데이터베이스 오류
	}

	
	public int getcomentcheck(int boardID, String userID) {

		try {
			conn = ds.getConnection();

			String sql2 = "select COUNT(*) as count from recommendation where boardID= ? and userID = ?";

			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, boardID);
			pstmt.setString(2, userID);
			rs = pstmt.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			System.out.println(count);

			if (count >= 1) {

				return -1; // 중복됨 -1 리턴

			} else {

				String sql = "insert into recommendation values (?, ?)";

				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, boardID);
				pstmt.setString(2, userID);
				return pstmt.executeUpdate(); // 추천 완료 1 리턴
			}

		} catch (Exception e) {
			System.out.println("getcomentcheck()메소드 내부에서 오류 " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return 0; // 추천중복오류
	}

	public int getcomentadd(int boardID) {
		String sql = "UPDATE board SET likeCount = likeCount + 1 WHERE boardID = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardID);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("like()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public int Comment(String userID, int boardID, String bcomment) {
		String sql = "INSERT INTO comments  VALUES (0, ?, ?, ?)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setInt(2, boardID);
			pstmt.setString(3, bcomment);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Comment()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return -1; // 데이터베이스 오류
	}

	public ArrayList<CommentsDTO> getComment(String boardID) {
		ArrayList<CommentsDTO> commentList = null;
		String sql = "SELECT * FROM comments WHERE boardID = ?  ORDER BY commentsNo DESC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(boardID));
			rs = pstmt.executeQuery();
			commentList = new ArrayList<CommentsDTO>();
			while (rs.next()) {
				CommentsDTO comments = new CommentsDTO();
				comments.setCommentsNo(rs.getInt("commentsNo"));
				comments.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				comments.setBoardID(rs.getInt("boardID"));
				comments.setBcomment(rs.getString("bcomment").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				commentList.add(comments);
			}
		} catch (Exception e) {
			System.out.println("getComment()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return commentList; // 데이터베이스 오류
	}

	public void deleteComment(int commentsNo) {
		String sql = "delete from comments where commentsNo = ?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, commentsNo);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("deleteComment()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	}

	public ArrayList<BoardDTO> getTourist(String city) {
		ArrayList<BoardDTO> tourList = null;
		String sql = "SELECT * FROM board where city = ? and likeCount > 100 ORDER BY likeCount DESC, boardHit DESC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, city);
			rs = pstmt.executeQuery();
			tourList = new ArrayList<BoardDTO>();
			while (rs.next()) {
				BoardDTO board = new BoardDTO();
				board.setUserID(rs.getString("userID").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardID(rs.getShort("boardID"));
				board.setBoardTitle(rs.getString("boardTitle").replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
				board.setBoardContent(rs.getString("boardContent"));
				board.setBoardDate(rs.getString("boardDate").substring(0, 10));
				board.setBoardHit(rs.getInt("boardHit"));
				board.setBoardFile(rs.getString("boardFile"));
				board.setBoardRealFile(rs.getString("boardRealFile"));
				board.setBoardGroup(rs.getInt("boardGroup"));
				board.setBoardSequence(rs.getInt("boardSequence"));
				board.setBoardLevel(rs.getInt("boardLevel"));
				board.setBoardAvailable(rs.getInt("boardAvailable"));
				board.setLikeCount(rs.getInt("likeCount"));
				board.setCity(rs.getString("city"));
				board.setTourist(rs.getString("tourist"));
				tourList.add(board);
			}
		} catch (Exception e) {
			System.out.println("getUser()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return tourList; // 데이터베이스 오류
	}

	public String doublecheck2() {
		String sql = "SELECT boardContent FROM board ORDER BY boardid DESC limit 1";
		String preContent = null;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				preContent = rs.getString(1);

			}
		} catch (Exception e) {
			System.out.println("doublecheck2()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	return preContent;	
	}
}