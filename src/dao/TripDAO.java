package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.CityDTO;
import vo.PlanDTO;
import vo.ScheduleDTO;

public class TripDAO {
	
	private Connection conn;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;
	
	public TripDAO() {

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
		
		
		// db에 도시가 있는 확인 
		public String cityCheck(String city){
			
			String sql = "SELECT * FROM city WHERE city like ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, city+"%");
				rs = pstmt.executeQuery();

				if(rs.next()){
					return rs.getString("city"); // db에 도시정보 존재 
				}else {
					return null; // db에 도시정보 없음 ...
				}
			} catch (Exception e) {
				System.out.println("cityCheck()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return null; // 데이터베이스 오류
		}
		
			public String cityEnglishName(String city){
			
			String sql = "SELECT cityEgname FROM city WHERE city like ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, city+"%");
				rs = pstmt.executeQuery();

				if(rs.next()){
					return rs.getString("cityEgname"); // db에 도시정보 존재 
				}else {
					return null; // db에 도시정보 없음 ...
				}
			} catch (Exception e) {
				System.out.println("cityEnglishName()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally{
				freeResource();
						}
			return null; // 데이터베이스 오류
		}
			
			public String cityCode(String city) {
				String sql = "SELECT cityCode FROM city WHERE city = ?";
				try {
					conn = ds.getConnection();
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, city);
					rs = pstmt.executeQuery();

					if(rs.next()){
						return rs.getString("cityCode"); // db에 도시정보 존재 
					}else {
						return null; // db에 도시정보 없음 ...
					}
				} catch (Exception e) {
					System.out.println("cityCode()메소드 내부에서 오류 : " + e);
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally{
					freeResource();
							}
				return null; // 데이터베이스 오류
			}
			
		
		// 카운트 순으로 city 가져오기
		public ArrayList<CityDTO> getCity(){
			ArrayList<CityDTO> cityList = null;

			String sql = "SELECT * FROM city ORDER BY cityCount DESC";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				cityList = new ArrayList<CityDTO>();

				while(rs.next()){
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
			} finally{
				freeResource();
						}
			return cityList; // 데이터베이스 오류
		}
		
		//도시 검색 회수 카운트 함수
		public int cityCount(String city) {
			String sql = "update city set cityCount =cityCount + 1 where city = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, city);
				return pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println("cityCount()메소드 내부에서 오류 " + e);
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return -1; // 데이터베이스 오류
		}

		
		// plan 저장하기
		public int plan(String userID, String city, String departDay, String returnDay, int nofday){
			String sql = "INSERT INTO plan VALUES (0, ?, ?, STR_TO_DATE(?,'%Y-%c-%d'), STR_TO_DATE(?,'%Y-%c-%d'), ?)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				pstmt.setString(2, city);
				pstmt.setString(3, departDay);
				pstmt.setString(4, returnDay);
				pstmt.setInt(5, nofday);

				return pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("plan()메소드 내부에서 오류 " + e);
				e.printStackTrace();		
		}finally{
			freeResource();
		}return -1; //데이터베이스 오류
		}
		
		
		
		// schedule 저장하기
		public int schedule(int planNo, String dayNo, int thingNo, String touristName){
			String sql = "INSERT INTO schedule VALUES (?, STR_TO_DATE(?,'%Y-%c-%d'), ?,?)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, planNo);
				pstmt.setString(2, dayNo);
				pstmt.setInt(3, thingNo);
				pstmt.setString(4, touristName);

				return pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("schedule()메소드 내부에서 오류 " + e);
				e.printStackTrace();		
		}finally{
			freeResource();
		}return -1; //데이터베이스 오류
		}

		public int findplanNo(String userID) {

			String sql = "select planNo from plan where userID = ? order by planNo DESC LIMIT 1";
			
			int planNo = 0 ;
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
					planNo = rs.getInt("planNo");
					return planNo;
				}
				}catch (Exception e) {
					System.out.println("findplanNo()메소드 내부에서 오류 " + e);
					e.printStackTrace();		
			}finally{
				freeResource();
			}return -1; //데이터베이스 오류
		
	}

		public ArrayList<PlanDTO> bringplan(String userID) {
			ArrayList<PlanDTO> cityList = null;
			String sql = "SELECT * FROM plan where userID = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				cityList = new ArrayList<PlanDTO>();
				while (rs.next()) {
					PlanDTO city = new PlanDTO();
					city.setPlanNo(rs.getInt("planNo"));
					city.setUserID(rs.getString("userID"));
					city.setCity(rs.getString("city"));
					city.setDepartDay(rs.getTimestamp("departDay").toString().substring(0, 10));
					city.setReturnDay(rs.getTimestamp("returnDay").toString().substring(0, 10));
					city.setNofday(rs.getInt("nofday"));
					cityList.add(city);
				}
			} catch (Exception e) {
				System.out.println("getUser()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return cityList; // 데이터베이스 오류
		}

		public ArrayList<ScheduleDTO> bringschedule(int planNo) {
			ArrayList<ScheduleDTO> scheduleList = null;
			String sql = "SELECT * FROM schedule where planNo = ? ";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, planNo);
				rs = pstmt.executeQuery();
				scheduleList = new ArrayList<ScheduleDTO>();
				while (rs.next()) {
					ScheduleDTO schedule = new ScheduleDTO();
					schedule.setPlanNo(rs.getInt("planNo"));
					schedule.setDayNo(rs.getString("dayNo"));
					schedule.setThingNo(rs.getInt("thingNo"));
					schedule.setTouristName(rs.getString("touristName"));
					scheduleList.add(schedule);
				}
			} catch (Exception e) {
				System.out.println("getUser()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return scheduleList; // 데이터베이스 오류
		}

		public void deleteplan(int planNo) {
			String sql = "delete from plan WHERE planNo = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, planNo);
				pstmt.executeUpdate();

			} catch (Exception e) {
				System.out.println("deleteplan()메소드 내부에서 오류 " + e);
				e.printStackTrace();
			} finally {
				freeResource();
			}
			
		}

		public void deleteschedule(int planNo) {
			String sql = "delete from schedule WHERE planNo = ?";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, planNo);
				pstmt.executeUpdate();

			} catch (Exception e) {
				System.out.println("deleteschedule()메소드 내부에서 오류 " + e);
				e.printStackTrace();
			} finally {
				freeResource();
			}
			
		}

		public PlanDTO getplan(int planNo) {
			String sql = "SELECT * FROM plan where planNo = ?";
			PlanDTO plan = new PlanDTO();
			System.out.println("getplan()메소드실행");
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, planNo);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					plan.setPlanNo(rs.getInt("planNo"));
					plan.setUserID(rs.getString("userID"));
					plan.setCity(rs.getString("city"));
					plan.setDepartDay(rs.getTimestamp("departDay").toString().substring(0, 10));
					plan.setReturnDay(rs.getTimestamp("returnDay").toString().substring(0, 10));
					plan.setNofday(rs.getInt("nofday"));
				}
			} catch (Exception e) {
				System.out.println("getplan()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return plan; // 데이터베이스 오류
		}
			
			
		

		
}	