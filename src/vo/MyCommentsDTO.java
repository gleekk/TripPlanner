package vo;

public class MyCommentsDTO {
	
	
	private int 	mycommentsNo;
	private String	userID;
	private int		myboardID;
	private String	mybcomment;

public int getMycommentsNo() {
	return mycommentsNo;
}
public void setMycommentsNo(int mycommentsNo) {
	this.mycommentsNo = mycommentsNo;
}
public String getUserID() {
	return userID;
}
public void setUserID(String userID) {
	this.userID = userID;
}
public int getMyboardID() {
	return myboardID;
}
public void setMyboardID(int myboardID) {
	this.myboardID = myboardID;
}
public String getMybcomment() {
	return mybcomment;
}
public void setMybcomment(String mybcomment) {
	this.mybcomment = mybcomment;
}


}
