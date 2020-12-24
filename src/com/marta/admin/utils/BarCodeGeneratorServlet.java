package com.marta.admin.utils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.servlet.http.*;
import javax.servlet.*;

import com.barcodelib.barcode.Linear;

import java.io.*;

public class BarCodeGeneratorServlet extends HttpServlet{

private static final long serialVersionUID = 1L;

	private int height=0;
	private int width=0;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		height=Integer.parseInt(getServletConfig().getInitParameter("height"));
		width=Integer.parseInt(getServletConfig().getInitParameter("width"));
	}

  /**
   * Method to generate the barcode image.
   */
	protected void doGet(HttpServletRequest req, HttpServletResponse response)
		throws IOException, ServletException {

		//Expire response
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);

		System.setProperty("java.awt.headless", "true");

		Linear linear = new Linear();
		//String bareCodeStr = Util.getRandomNumber();
		HttpSession session = req.getSession(true);
		String barCodeStr = "";
		if(null != session.getAttribute(Constants.BAR_CODE_STR) &&
				!session.getAttribute(Constants.BAR_CODE_STR).equals("")){
			barCodeStr = session.getAttribute(Constants.BAR_CODE_STR).toString();
		}

		linear.setData(barCodeStr);
		linear.setType(Linear.CODABAR);
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		try{
			//To generate barcode image.
			image = linear.renderBarcode();
		}catch(Exception e){
			e.printStackTrace();
		}

		OutputStream outputStream = response.getOutputStream();
		if(image !=null){
		ImageIO.write(image, "jpeg", outputStream);
		}
		outputStream.close();
		System.setProperty("java.awt.headless", "false");

		}
	}
