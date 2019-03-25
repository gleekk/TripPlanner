package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import vo.GowithDTO;

public class GowithDAO {

	private Connection con;
	private ResultSet rs;
	private DataSource ds;
	private PreparedStatement pstmt;
	
	public GowithDAO() {

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
			if (con != null) {
				try {
					con.close();
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
		
		public int targetPage(String date1, String date2, int age, int mem, String destination, String pageNumber) {
			
			String sql="select COUNT(no) FROM gowith where date1 >=IFNULL(?, '2017-01-01') and date2 <=IFNULL(?, '2022-01-01') and IF (0 = ?, age=10 or 20 or 30 or 40 or 0 , age= ?) and IF (0 = ?, mem=2 or 34 or 58 or 8 or 100 , mem= ?) and destination like ?";
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, date1);
				pstmt.setString(2, date2);
				pstmt.setInt(3,age);
				pstmt.setInt(4,age);
				pstmt.setInt(5,mem);
				pstmt.setInt(6,mem);
				pstmt.setString(7, "%"+destination+"%");
				
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return (((rs.getInt(1)-1) / 10)-(Integer.parseInt(pageNumber) - 1));
				}
			} catch (Exception e) {
				System.out.println("targetPage2()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return 0;
		}
		
		
			public int targetPage2( String pageNumber, String userID) {
			
			String sql="select COUNT(no)  from gowith as go inner join (select * from gowithlike where user=?) as gowith on go.no=gowith.boardno";
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if (rs.next()) {
					return (((rs.getInt(1)-1) / 10)-(Integer.parseInt(pageNumber) - 1));
				}
			} catch (Exception e) {
				System.out.println("targetPage2()메소드 내부에서 오류 : " + e);
				System.out.println(e.getMessage());
				e.printStackTrace();
			} finally {
				freeResource();
			}
			return 0;
		}

			UserDAO userDAO = new UserDAO();
			
		public Vector<GowithDTO> getlist(String userID, String pageNumber){
			Vector<GowithDTO> v=new Vector<>();
			try {
				con = ds.getConnection();
				String sql="select no, userid, title, content, date1, date2, mem, age, destination, profilephoto, likecheck"
						 +" from gowith as go left outer join (select * from gowithlike where user=?) as gowith"
						 +" on go.no=gowith.boardno ORDER BY no DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs=pstmt.executeQuery();
				while(rs.next()){
					GowithDTO dto =new GowithDTO();
					dto.setNo(rs.getInt(1));
					dto.setUserid(rs.getString(2).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setTitle(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setContent(rs.getString(4).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setDate1(rs.getString(5));
					dto.setDate2(rs.getString(6));
					dto.setMem(rs.getInt(7));
					dto.setAge(rs.getInt(8));
					dto.setDestination(rs.getString(9).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					
					String profile = userDAO.getProfile(rs.getString(2));
					dto.setProfilephoto(profile);
					
					dto.setLikecheck(rs.getString(11));
					v.add(dto);
				}
				
			} catch (Exception e) {
				System.out.println("getlist()에서 오류"+e);
			}finally{
				freeResource();
			}
			return v;
		}
		
		public void insert(GowithDTO dto){
			
			try {
				
				con=ds.getConnection();
				String sql="insert into gowith(userid,title,content,date1,date2,mem,age,destination,profilephoto)"
							+"values(?,?,?,STR_TO_DATE(?,'%Y-%c-%d'),STR_TO_DATE(?,'%Y-%c-%d'),?,?,?,?)";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, dto.getUserid());
				pstmt.setString(2,dto.getTitle());
				pstmt.setString(3, dto.getContent());
				pstmt.setString(4, dto.getDate1());
				pstmt.setString(5, dto.getDate2());
				pstmt.setInt(6, dto.getMem());
				pstmt.setInt(7, dto.getAge());
				pstmt.setString(8, dto.getDestination());
				
				String profile = userDAO.getProfile(dto.getUserid());				
				pstmt.setString(9, profile);
				
				pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				System.out.println("insert()에서 오류:" +e);
			}finally{
				freeResource();
			}
			
		}
		
		
		public Vector<GowithDTO> search(String date1, String date2,int age,
										int mem, String destination, String userID, String pageNumber){
			Vector<GowithDTO> v = new Vector<GowithDTO>();
			String sql="select no, userid, title, content, date1, date2, mem, age, destination, profilephoto, likecheck from gowith as go left outer join (select * from gowithlike where user=?) as gowith on go.no=gowith.boardno where date1 >=IFNULL(?, '2017-01-01') and date2 <=IFNULL(?, '2022-01-01') and IF (0 = ?, age=10 or 20 or 30 or 40 or 0 , age= ?) and IF (0 = ?, mem=2 or 34 or 58 or 8 or 100 , mem= ?) and destination like ? ORDER BY no DESC LIMIT " + (Integer.parseInt(pageNumber) - 1) * 10 + ", " + 10;

			try {
				
				con=ds.getConnection();
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1, userID);
					pstmt.setString(2, date1);
					pstmt.setString(3, date2);
					pstmt.setInt(4,age);
					pstmt.setInt(5,age);
					pstmt.setInt(6,mem);
					pstmt.setInt(7,mem);
					pstmt.setString(8, "%"+destination+"%");
					rs=pstmt.executeQuery();
				while(rs.next()){
					GowithDTO gdto=new GowithDTO();
					gdto.setNo(rs.getInt(1));
					gdto.setUserid(rs.getString(2).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					gdto.setTitle(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					gdto.setContent(rs.getString(4).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					gdto.setDate1(rs.getString(5));
					gdto.setDate2(rs.getString(6));
					gdto.setMem(rs.getInt(7));
					gdto.setAge(rs.getInt(8));
					gdto.setDestination(rs.getString(9).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					
					String profile = userDAO.getProfile(rs.getString(2));					
					gdto.setProfilephoto(profile);
	
					gdto.setLikecheck(rs.getString(11));
					v.add(gdto);
				}
				
			}catch (Exception e) {
				System.out.println("search2()함수에서 오류:"+e);
			}finally{
				freeResource();
			}
			
			return v;
		}
		
		
		public GowithDTO getinfo(int no){
				
			GowithDTO dto=null;
			
			try {
				con=ds.getConnection();
				String sql="select * from gowith where no=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, no);
				rs=pstmt.executeQuery();
				if(rs.next()){
					dto=new GowithDTO();
					dto.setNo(rs.getInt(1));
					dto.setUserid(rs.getString(2).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setTitle(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setContent(rs.getString(4).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setDate1(rs.getString(5));
					dto.setDate2(rs.getString(6));
					dto.setMem(rs.getInt(7));
					dto.setAge(rs.getInt(8));
					dto.setDestination(rs.getString(9).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					
					String profile = userDAO.getProfile(rs.getString(2));
					dto.setProfilephoto(profile);
					
				}
				
			} catch (Exception e) {
				System.out.println("getinfo()메소드에서 오류:"+e);
			}finally{
				freeResource();
			}
			
			return dto;
		}

		
		
		public void delete(int no) {
			
			try {
				
				con=ds.getConnection();
				String sql="delete from gowith where no=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, no);
				pstmt.executeUpdate();
				
			} catch (Exception e) {
				System.out.println("delete()메소드에서 오류:"+e);
			}finally{
				freeResource();
			}
			
		}

		
		public int likecheck(int no, String userID) {

			try {
				con=ds.getConnection();
				String sql="select * from gowithlike where boardno=? and user=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1, no);
				pstmt.setString(2, userID);
				rs=pstmt.executeQuery();
				if(rs.next()){
					sql="delete from gowithlike where boardno=? and user=? ";
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1, no);
					pstmt.setString(2, userID);
					pstmt.executeUpdate();
					return -1;
				}else{
					sql="insert into gowithlike(boardno,user,likecheck) values(?,?,1)";
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1, no);
					pstmt.setString(2, userID);
					pstmt.executeUpdate();
					return 1;
				}
					
				
			}catch (Exception e) {
				System.out.println("checklike()메소드에서 오류:"+e);
				return 0;
			} finally {
				freeResource();
			}
		
		}

		public Vector<GowithDTO> getlike(String userID) {
			
			Vector<GowithDTO> v=new Vector<>();
			try {
				con=ds.getConnection();
				String sql="select no, userid, title, content, date1, date2, mem, age, destination, profilephoto, likecheck"
						 +" from gowith as go inner join (select * from gowithlike where user=?) as gowith"
						 +" on go.no=gowith.boardno";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs=pstmt.executeQuery();
				
				while(rs.next()){
					GowithDTO dto =new GowithDTO();
					dto.setNo(rs.getInt(1));
					dto.setUserid(rs.getString(2).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setTitle(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setContent(rs.getString(4).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setDate1(rs.getString(5));
					dto.setDate2(rs.getString(6));
					dto.setMem(rs.getInt(7));
					dto.setAge(rs.getInt(8));
					dto.setDestination(rs.getString(9).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					
					String profile = userDAO.getProfile(rs.getString(2));
					dto.setProfilephoto(profile);
					dto.setLikecheck(rs.getString(11));
					v.add(dto);
				}
			} catch (Exception e) {
				System.out.println("getlike()메소드에서 오류:"+e);
			}finally{
				freeResource();
			}
			return v;
		}

		public void updateprofile(String userID, String profile) {
			
			try {
				con=ds.getConnection();
				String sql="update gowith set profilephoto =? where userid=?";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, profile);
				pstmt.setString(2, userID);
				pstmt.executeUpdate();

			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}

	
		public GowithDTO getgowith(String userID){
			
			GowithDTO dto=null;
			
			try {
				con=ds.getConnection();
				String sql="select * from gowith where userID = ? ORDER BY no DESC ";
				pstmt=con.prepareStatement(sql);
				pstmt.setString(1, userID);
				rs=pstmt.executeQuery();
				if(rs.next()){
					dto=new GowithDTO();
					dto.setNo(rs.getInt(1));
					dto.setUserid(rs.getString(2).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setTitle(rs.getString(3).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setContent(rs.getString(4).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					dto.setDate1(rs.getString(5));
					dto.setDate2(rs.getString(6));
					dto.setMem(rs.getInt(7));
					dto.setAge(rs.getInt(8));
					dto.setDestination(rs.getString(9).replaceAll(" ", "&nbsp").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\n", "<br>"));
					
					
					String profile = userDAO.getProfile(rs.getString(2));
					dto.setProfilephoto(profile);
					
				}
				
			} catch (Exception e) {
				System.out.println("getinfo()메소드에서 오류:"+e);
			}finally{
				freeResource();
			}
			
			return dto;
		}


		
}
