package comv2.aunwesha.lfutil;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public abstract class LfServlet extends HttpServlet {

	private static final long serialVersionUID = 1663657170858671572L;
	private static final String LOGIN_SESSION_NAME = "ADMIN_LOG_ON";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("text/html");

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession mysession = request.getSession(true);
		Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		if (obj == null) {
			response.sendRedirect("../coreadmin/login.html");
		}
	}
	
	public String getLoggedInUserName(HttpServletRequest request){
		HttpSession mysession = request.getSession(true);
		Object obj = mysession.getAttribute(LOGIN_SESSION_NAME);
		if (obj != null) {
			return obj.toString();
		}else{
			return null;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		this.doGet(request, response);
	}

	protected Pair<String, String> retrieveDateTime() {
		Calendar calendar = new GregorianCalendar();
		Date trialTime = new Date();
		calendar.setTime(trialTime);

		String months[] = { "January", "Feburary", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };

		String strTime = calendar.get(Calendar.HOUR) + ":"
				+ calendar.get(Calendar.MINUTE);
		String strDate = months[calendar.get(Calendar.MONTH)] + " "
				+ calendar.get(Calendar.DATE) + ", "
				+ calendar.get(Calendar.YEAR);
		return new Pair<String, String>(strDate, strTime);
	}

}
