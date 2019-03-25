package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.CityDTO;
import vo.wordDTO;

public class AnalysisDAO {

	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;

	public AnalysisDAO() {

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

	public int[] getpopulation() {
		int[] popList = new int[23];

		String sql = "select " + "sum(case when userAge >= 1 and userAge < 6 then 1 else 0 end) 1to5, "
				+ "sum(case when userAge >= 6 and userAge < 11 then 1 else 0 end) 6to10, "
				+ "sum(case when userAge >= 11 and userAge < 16 then 1 else 0 end) 11to15, "
				+ "sum(case when userAge >= 16 and userAge < 21 then 1 else 0 end) 16to20, "
				+ "sum(case when userAge >= 21 and userAge < 26 then 1 else 0 end) 21to25, "
				+ "sum(case when userAge >= 26 and userAge < 31 then 1 else 0 end) 26to30, "
				+ "sum(case when userAge >= 31 and userAge < 36 then 1 else 0 end) 31to35, "
				+ "sum(case when userAge >= 36 and userAge < 41 then 1 else 0 end) 36to40, "
				+ "sum(case when userAge >= 41 and userAge < 46 then 1 else 0 end) 41to45, "
				+ "sum(case when userAge >= 46 and userAge < 51 then 1 else 0 end) 46to50, "
				+ "sum(case when userAge >= 51 and userAge < 56 then 1 else 0 end) 51to55, "
				+ "sum(case when userAge >= 56 and userAge < 61 then 1 else 0 end) 56to60, "
				+ "sum(case when userAge >= 61 and userAge < 66 then 1 else 0 end) 61to65, "
				+ "sum(case when userAge >= 66 and userAge < 71 then 1 else 0 end) 66to70, "
				+ "sum(case when userAge >= 71 and userAge < 76 then 1 else 0 end) 71to75, "
				+ "sum(case when userAge >= 76 and userAge < 81 then 1 else 0 end) 76to80, "
				+ "sum(case when userAge >= 81 and userAge < 86 then 1 else 0 end) 81to85, "
				+ "sum(case when userAge >= 86 and userAge < 91 then 1 else 0 end) 86to90,"
				+ "sum(case when userAge >= 91 and userAge < 96 then 1 else 0 end) 91to95, "
				+ "sum(case when userAge >= 96 and userAge < 101 then 1 else 0 end) 96to100, "
				+ "sum(case when userAge >= 101 and userAge < 106 then 1 else 0 end) 101to105, "
				+ "sum(case when userAge >= 106 and userAge < 111 then 1 else 0 end) 106to110  "
				+ "from user where userGender = '남자' ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				for (int i = 1; i <= 22; i++) {
					popList[i] = rs.getInt(i);
				}
			}
		} catch (Exception e) {
			System.out.println("getpopulation()메소드 내부에서 오류 : " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return popList;

	}

	public int[] getpopulationw() {
		int[] popList2 = new int[23];

		String sql = "select " + "sum(case when userAge >= 1 and userAge < 6 then 1 else 0 end) 	1to5, "
				+ "sum(case when userAge >= 6 and userAge < 11 then 1 else 0 end) 	6to10, "
				+ "sum(case when userAge >= 11 and userAge < 16 then 1 else 0 end) 11to15, "
				+ "sum(case when userAge >= 16 and userAge < 21 then 1 else 0 end) 16to20, "
				+ "sum(case when userAge >= 21 and userAge < 26 then 1 else 0 end) 21to25, "
				+ "sum(case when userAge >= 26 and userAge < 31 then 1 else 0 end) 26to30, "
				+ "sum(case when userAge >= 31 and userAge < 36 then 1 else 0 end) 31to35, "
				+ "sum(case when userAge >= 36 and userAge < 41 then 1 else 0 end) 36to40,  "
				+ "sum(case when userAge >= 41 and userAge < 46 then 1 else 0 end) 41to45, "
				+ "sum(case when userAge >= 46 and userAge < 51 then 1 else 0 end) 46to50, "
				+ "sum(case when userAge >= 51 and userAge < 56 then 1 else 0 end) 51to55, "
				+ "sum(case when userAge >= 56 and userAge < 61 then 1 else 0 end) 56to60,"
				+ "sum(case when userAge >= 61 and userAge < 66 then 1 else 0 end) 61to65, "
				+ "sum(case when userAge >= 66 and userAge < 71 then 1 else 0 end) 66to70, "
				+ "sum(case when userAge >= 71 and userAge < 76 then 1 else 0 end) 71to75, "
				+ "sum(case when userAge >= 76 and userAge < 81 then 1 else 0 end) 76to80,"
				+ "sum(case when userAge >= 81 and userAge < 86 then 1 else 0 end) 81to85, "
				+ "sum(case when userAge >= 86 and userAge < 91 then 1 else 0 end) 86to90,"
				+ "sum(case when userAge >= 91 and userAge < 96 then 1 else 0 end) 91to95, "
				+ "sum(case when userAge >= 96 and userAge < 101 then 1 else 0 end) 96to100, "
				+ "sum(case when userAge >= 101 and userAge < 106 then 1 else 0 end) 101to105, "
				+ "sum(case when userAge >= 106 and userAge < 111 then 1 else 0 end) 106to110  "
				+ "from user where userGender = '여자' ";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				for (int i = 1; i <= 22; i++) {

					popList2[i] = rs.getInt(i);
				}
			}
		} catch (Exception e) {
			System.out.println("getpopulationw()메소드 내부에서 오류 : " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return popList2;

	}

	// 카운트 순으로 city 가져오기
	public ArrayList<CityDTO> getCity() {
		ArrayList<CityDTO> cityList = null;

		String sql = "SELECT * FROM city ORDER BY cityCount DESC";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			cityList = new ArrayList<CityDTO>();

			while (rs.next()) {
				CityDTO cityDTO = new CityDTO();
				cityDTO.setCity(rs.getString("city"));
				cityDTO.setCityEgname(rs.getString("cityEgname"));
				cityDTO.setCitycount(rs.getString("citycount"));
				cityDTO.setCityCountry(rs.getString("cityCountry"));
				cityDTO.setCityCode(rs.getString("cityCode"));
				cityList.add(cityDTO);

			}
		} catch (Exception e) {
			System.out.println("getCity()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return cityList; // 데이터베이스 오류
	}

	public int[] getCategoryinfo() {
		int[] ctgList = new int[3];

		String sql = "select mycategory, count(mycategory) from myboard group by mycategory";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			int i = 0;

			while (rs.next()) {
				ctgList[i] = rs.getInt(2);
				i++;
			}
		} catch (Exception e) {
			System.out.println("getCategoryinfo()메소드 내부에서 오류 : " + e);
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return ctgList;

	}

	public ArrayList<String> getText() {
		ArrayList<String> boardList = null;
		String sql = "SELECT * FROM myboard";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			boardList = new ArrayList<String>();
			while (rs.next()) {
				String str = rs.getString("myboardContent");
				String str2 = str.replaceAll("<[^>]*>", " ").replaceAll("[a-zA-Z0-9]", " ").replaceAll("[ㄱ-ㅎㅏ-ㅣ]", " ").replaceAll("/^[가-힣]{0,1}+$/", " "); 

				boardList.add(str2);
			}
		} catch (Exception e) {
			System.out.println("getText()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return boardList; // 데이터베이스 오류
	}

	public void createtable() {

		String sql = "create table datatable(nov int primary key auto_increment, keyword VARCHAR(20), keyvalue int)";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("createtable()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	}
	
	public void droptable() {

		String sql = "drop table datatable";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("droptable()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	}
	
	public void insertvalue(String key, int value) {

		String sql = "insert into datatable values (null, ?, ?)";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, key);
			pstmt.setInt(2, value);
			pstmt.executeUpdate();
			

		} catch (Exception e) {
			System.out.println("insertvalue()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	}
	
	public ArrayList<wordDTO> getRankword() {
		ArrayList<wordDTO> wordList = null;

		String sql = "SELECT * FROM datatable ORDER BY keyvalue DESC  ";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			wordList = new ArrayList<wordDTO>();
			while (rs.next()) {
				wordDTO word = new wordDTO();
				word.setKeyword(rs.getString("keyword")); 
				word.setKeyvalue(rs.getInt("keyvalue")); 
				wordList.add(word);
			}
		} catch (Exception e) {
			System.out.println("getRankword()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return wordList; // 데이터베이스 오류
	}
	
	public int getRanktotal() {

		String sql = "select sum(keyvalue) from (select keyvalue  from datatable ORDER BY keyvalue DESC LIMIT 20) as tablename";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("getRanktotal()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
		return 0; // 데이터베이스 오류
	}

	public void empty() {


		String sql = "delete from datatable;";

		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println("empty()메소드 내부에서 오류 : " + e);
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			freeResource();
		}
	}
	

	

}