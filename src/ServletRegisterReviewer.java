

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletRegisterReviewer
 */
@WebServlet("/RegisterReviewer")
public class ServletRegisterReviewer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletRegisterReviewer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//get parameters
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String zipcode = request.getParameter("zipcode");
		
		//validate inputs
		if(!Validator.validateNullEmptyString(name) || !Validator.validateNullEmptyString(email) || !Validator.validateNullEmptyString(zipcode))
		{
			response.sendError(400,"Invalid Inputs!");
		}
		else
		{
			System.out.println(name + email + zipcode);
		}
		
		
	}

}