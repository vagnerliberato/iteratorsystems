package br.iteratorsystems.cps.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet para processar as imagens dinamicamente.
 * @author André
 *
 */
public class ImageServlet extends HttpServlet {

	private static final long serialVersionUID = 3640663497569374138L;
	
	/**
	 * Construtor padrão.
	 */
	public ImageServlet() {
		super();
	}

	/**
	 * do Get
	 * @param request - Request de http
	 * @param response - Response de http
	 * @throws ServletException Servlet Exception
	 * @throws IOException IOException  
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			String file = request.getParameter("file");
			
			String[] diretorio = 
							request.getParameter("dir").split(":"); 
			
			if(diretorio.length > 1) {
				StringBuilder builderDiretorio = new StringBuilder(diretorio[0].trim()+":");
				builderDiretorio.append(File.separator);
				builderDiretorio.append(diretorio[1].trim()+File.separator);
				
				BufferedInputStream in = new BufferedInputStream(
									new FileInputStream(builderDiretorio.toString() + file.trim()));

				byte[] bytes = new byte[in.available()];

				in.read(bytes);
				in.close();

				response.getOutputStream().write(bytes);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * do Post
	 * @param request - Request de http
	 * @param response - Response de http
	 * @throws ServletException Servlet Exception
	 * @throws IOException IOException  
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		this.doGet(request, response);
	}
}
