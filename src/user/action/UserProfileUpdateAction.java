package user.action;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.UserDAO;
import vo.Action;
import vo.ActionForward;

//프로필 수정 기능
public class UserProfileUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MultipartRequest multi = null;
		int fileMaxSize = 10 * 1024 * 1024;
		String savePath = request.getRealPath("/upload").replace("\\\\", "/");
		try {
			multi = new MultipartRequest(request, savePath, fileMaxSize, "UTF-8", new DefaultFileRenamePolicy());
		} catch (Exception e) {
			request.getSession().setAttribute("messageType", "오류 메시지");
			request.getSession().setAttribute("messageContent", "파일 크기는 10MB를 넘을 수 없습니다. 또는 저장 폴더를 생성 하세요");
			ActionForward forward = new ActionForward("profileUpdate.jsp", true);
			return forward;
		}

		String userID = multi.getParameter("userID");
		HttpSession session = request.getSession();

		String fileName = "";
		File file = multi.getFile("userProfile");
		if (file != null) {
			String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
			if (ext.equals("jpg") || ext.equals("png") || ext.equals("gif")) {
				String prev = new UserDAO().getUser(userID).getUserProfile();
				File prevFile = new File(savePath + "/" + prev);
				if (prevFile.exists()) {
					prevFile.delete();
				}
				fileName = file.getName();
			} else {
				if (file.exists()) {
					file.delete();
				}
				session.setAttribute("messageType", "오류 메시지");
				session.setAttribute("messageContent", "이미지 파일만 업로드 가능합니다.");
				ActionForward forward = new ActionForward("profileUpdate.jsp", true);
				return forward;
			}

		}

		new UserDAO().profile(userID, fileName);
		//session.setAttribute("messageType", "성공 메시지");
		//session.setAttribute("messageContent", "성공적으로 프로필이 변경되었습니다..");

		ActionForward forward = new ActionForward("profileUpdate.user", true);
		return forward;

	}
}
