package com.intel.cid.common.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.intel.cid.common.bean.SubTestPlan;
import com.intel.cid.common.service.TestResultXmlResolverService;
import com.intel.cid.common.service.SubTestPlanService;

@SuppressWarnings("serial")
public class UploadTestResult extends HttpServlet {

	/**
	private Logger  logger = Logger.getLogger(UploadTestResult.class);
	
	@SuppressWarnings( { "deprecation", "unchecked" })
	protected void service(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {

		PrintWriter pw = resp.getWriter();
		pw.write("test OK");

		File desDir = new File(request.getRealPath("/xml"));
		if (!desDir.exists()) {
			desDir.mkdir();
		}

		byte[] buffer = new byte[4096];
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(4096); 
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(419430400); 
			List items = upload.parseRequest(request);
			Iterator i = items.iterator();

			while (i.hasNext()) {

				FileItem fi = (FileItem) i.next();
				if (fi.isFormField()) {

					String strname = fi.getFieldName();
					String value = fi.getString();
					logger.info(strname + "=" + value);

				} else {
					String fileName = fi.getName();
					logger.info("UploadTestResult: get upload file,"+fileName);

					if (fileName.contains("\\")) {
						String[] names = fileName.split("\\\\");
						fileName = names[names.length - 1];
					}
					if (fileName.contains("/")) {

						String[] names = fileName.split("/");
						fileName = names[names.length - 1];
					}
					// String [] names = fileName.split("\\");
					// String name = names[names.length-1];

					InputStream in = fi.getInputStream();
					File file = new File(desDir + File.separator + fileName);
					OutputStream ous = new FileOutputStream(file);
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						ous.write(buffer, 0, len);

					}
					in.close();
					ous.close();
					ApplicationContext ac = null;

					ac = WebApplicationContextUtils
							.getRequiredWebApplicationContext(request
									.getSession().getServletContext());

					TestResultXmlResolverService testResultXmlResolverService = (TestResultXmlResolverService) ac
							.getBean("testResultXmlResolverService");

					SubTestPlanService subTestPlanService = (SubTestPlanService) ac
							.getBean("subTestPlanService");
					SubTestPlan subTestPlan = testResultXmlResolverService
							.resolveSubTestPlanXml(file);
					
				}

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		super.service(request, resp);
	}
**/
}
