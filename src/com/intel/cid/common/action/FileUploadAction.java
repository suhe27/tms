package com.intel.cid.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

import com.intel.cid.common.bean.Team;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.service.ExcelResolverService;
import com.intel.cid.common.service.TestResultXmlResolverService;
import com.intel.cid.utils.Utils;
import com.opensymphony.xwork2.ActionContext;

public class FileUploadAction extends BaseAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8292796918473961153L;

    private File[] uploads;

    private String[] uploadFileNames;

    private String[] uploadContentTypes;

    private byte[] buffer = new byte[4096];

    private String savePath;
    
    private int subExecutionId;

    private String allowTypes;

    private List<File> fileList = new ArrayList<File>();
    
    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getAllowTypes() {
        return allowTypes;
    }

    public void setAllowTypes(String allowTypes) {
        this.allowTypes = allowTypes;
    }

    public void setUpload(File[] upload) {
        System.out.println("invoke");
        this.uploads = upload;

    }
    
	public int getSubExecutionId() {
		return subExecutionId;
	}
	public void setSubExecutionId(int subExecutionId) {
		this.subExecutionId = subExecutionId;
	}

    public File[] getUpload() {

        return this.uploads;
    }

    public String[] getUploadFileName() {
        return this.uploadFileNames;
    }

    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileNames = uploadFileName;
    }

    public String[] getUploadContentType() {
        return this.uploadContentTypes;
    }

    public void setUploadContentType(String[] uploadContentType) {
        this.uploadContentTypes = uploadContentType;
    }

    public String toImportTestCase() {

        return SUCCESS;
    }
    
    public String toImportTestResult() {

        return SUCCESS;
    }
    
    public String toImportTestResultByXML() {

        return SUCCESS;
    }

    public String toImportPerformanceResultByExcel() {

        return SUCCESS;
    }
    
    public File[] getUploads() {
        return uploads;
    }

    public void setUploads(File[] uploads) {
        this.uploads = uploads;
    }

    public String[] getUploadFileNames() {
        return uploadFileNames;
    }

    public void setUploadFileNames(String[] uploadFileNames) {
        this.uploadFileNames = uploadFileNames;
    }

    public String[] getUploadContentTypes() {
        return uploadContentTypes;
    }

    public void setUploadContentTypes(String[] uploadContentTypes) {
        this.uploadContentTypes = uploadContentTypes;
    }

  
    public ExcelResolverService getExcelResolverService() {
		return excelResolverService;
	}

	public void setExcelResolverService(ExcelResolverService excelResolverService) {
		this.excelResolverService = excelResolverService;
	}
	
    public TestResultXmlResolverService getTestResultXmlResolverService() {
		return testResultXmlResolverService;
	}

	public void setTestResultXmlResolverService(TestResultXmlResolverService testResultXmlResolverService) {
		this.testResultXmlResolverService = testResultXmlResolverService;
	}

	public String importTestCase() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		long time = System.currentTimeMillis();
		if (user == null) {
			return LOGIN;
		}
        ServletContext sc = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath(savePath+File.separator+"testcase"+File.separator+Utils.dateFormat(new Date(), "yyyy_MM_dd"));
        File desDir = new File(path);
        if (!desDir.exists()) {
			desDir.mkdirs();
		}
        
        
        
        if (uploadFileNames == null) {
            context.put(Constant.ERRORMSG, "please choose file to upload!!!");
            return "input";
        }
        String[] allowTypes = this.allowTypes.split(",");
        HashSet<String> allowSet = new HashSet<String>();
        for (String allowType : allowTypes) {
            allowSet.add(allowType);

        }
        if (uploadFileNames != null) {
            for (String filename : uploadFileNames) {
                String suffixName = filename.substring(filename.lastIndexOf("."), filename.length());

                if (!allowSet.contains(suffixName)) {
                    context.put(Constant.ERRORMSG, "Just only support execl file upload!!!");
                    return "input";
                }

            }
        }

        if (uploads != null) {
            InputStream ins = null;
            OutputStream ous = null;
            for (int i = 0; i < uploads.length; i++) {
                try {
                    ins = new FileInputStream(uploads[i]);
                    File singleFile = new File(path + File.separator + Utils.renameFileAppendSuffix(uploadFileNames[i], String.valueOf(++time)));
					ous = new FileOutputStream(singleFile);
                    int len = 0;
                    while ((len = ins.read(buffer)) > 0) {
                        ous.write(buffer, 0, len);

                    }
                    fileList.add(singleFile);
                } catch (FileNotFoundException e) {
                    context.put(Constant.ERRORMSG, "an error has occurred!");
                    return ERROR;

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    if (null != ins) {
                        try {
                            ins.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (ous != null) {
                        try {
                            ous.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }

        }

        excelResolverService.updateBatchTestCaseForXLS(context,fileList); 
        return SUCCESS;
    }
	
	public String importPerformanceResultByExcel() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		long time = System.currentTimeMillis();
		if (user == null) {
			return LOGIN;
		}
        ServletContext sc = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath(savePath+File.separator+"testcase"+File.separator+Utils.dateFormat(new Date(), "yyyy_MM_dd"));
        File desDir = new File(path);
        if (!desDir.exists()) {
			desDir.mkdirs();
		}
        
        
        
        if (uploadFileNames == null) {
            context.put(Constant.ERRORMSG, "please choose file to upload!");
            return "input";
        }
        String[] allowTypes = this.allowTypes.split(",");
        HashSet<String> allowSet = new HashSet<String>();
        for (String allowType : allowTypes) {
            allowSet.add(allowType);

        }
        if (uploadFileNames != null) {
            for (String filename : uploadFileNames) {
                String suffixName = filename.substring(filename.lastIndexOf("."), filename.length());

                if (!allowSet.contains(suffixName)) {
                    context.put(Constant.ERRORMSG, "Only support Excel xlsx file type upload!");
                    return "input";
                }

            }
        }

        if (uploads != null) {
            InputStream ins = null;
            OutputStream ous = null;
            for (int i = 0; i < uploads.length; i++) {
                try {
                    ins = new FileInputStream(uploads[i]);
                    File singleFile = new File(path + File.separator + Utils.renameFileAppendSuffix(uploadFileNames[i], String.valueOf(++time)));
					ous = new FileOutputStream(singleFile);
                    int len = 0;
                    while ((len = ins.read(buffer)) > 0) {
                        ous.write(buffer, 0, len);

                    }
                    fileList.add(singleFile);
                } catch (FileNotFoundException e) {
                    context.put(Constant.ERRORMSG, "an error has occurred!");
                    return ERROR;

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    if (null != ins) {
                        try {
                            ins.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (ous != null) {
                        try {
                            ous.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }

        }

        excelResolverService.updateBatchPerformanceResultByExcel(context,fileList,subExecutionId); 
        return SUCCESS;
    }
	
	public String importTestResult() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		long time = System.currentTimeMillis();
		if (user == null) {
			return LOGIN;
		}
        ServletContext sc = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath(savePath+File.separator+"testcase"+File.separator+Utils.dateFormat(new Date(), "yyyy_MM_dd"));
        File desDir = new File(path);
        if (!desDir.exists()) {
			desDir.mkdirs();
		}
        
        
        
        if (uploadFileNames == null) {
            context.put(Constant.ERRORMSG, "please choose file to upload!");
            return "input";
        }
        String[] allowTypes = this.allowTypes.split(",");
        HashSet<String> allowSet = new HashSet<String>();
        for (String allowType : allowTypes) {
            allowSet.add(allowType);

        }
        if (uploadFileNames != null) {
            for (String filename : uploadFileNames) {
                String suffixName = filename.substring(filename.lastIndexOf("."), filename.length());

                if (!allowSet.contains(suffixName)) {
                    context.put(Constant.ERRORMSG, "Only support execl xlsx file type upload!");
                    return "input";
                }

            }
        }

        if (uploads != null) {
            InputStream ins = null;
            OutputStream ous = null;
            for (int i = 0; i < uploads.length; i++) {
                try {
                    ins = new FileInputStream(uploads[i]);
                    File singleFile = new File(path + File.separator + Utils.renameFileAppendSuffix(uploadFileNames[i], String.valueOf(++time)));
					ous = new FileOutputStream(singleFile);
                    int len = 0;
                    while ((len = ins.read(buffer)) > 0) {
                        ous.write(buffer, 0, len);

                    }
                    fileList.add(singleFile);
                } catch (FileNotFoundException e) {
                    context.put(Constant.ERRORMSG, "an error has occurred!");
                    return ERROR;

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    if (null != ins) {
                        try {
                            ins.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (ous != null) {
                        try {
                            ous.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }

        }

        excelResolverService.updateBatchTestResultForXLS(context,fileList,subExecutionId); 
        return SUCCESS;
    }

/**
 * Import test case execution result into testresult table by XML file type. XML file is auto generated by automation framework.	
 * @return
 * @throws Exception
 */
	public String importTestResultByXML() throws Exception {
		ActionContext context = ActionContext.getContext();
		User user = (User) context.getSession().get("user");
		long time = System.currentTimeMillis();
		/**
		if (user == null) {
			return LOGIN;
		}
		**/
        ServletContext sc = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath(savePath+File.separator+"testcase"+File.separator+Utils.dateFormat(new Date(), "yyyy_MM_dd"));
        File desDir = new File(path);
        if (!desDir.exists()) {
			desDir.mkdirs();
		}
        
        
        
        if (uploadFileNames == null) {
            context.put(Constant.ERRORMSG, "please choose file to upload!");
            return "input";
        }
        String[] allowTypes = this.allowTypes.split(",");
        HashSet<String> allowSet = new HashSet<String>();
        for (String allowType : allowTypes) {
            allowSet.add(allowType);

        }
        if (uploadFileNames != null) {
            for (String filename : uploadFileNames) {
                String suffixName = filename.substring(filename.lastIndexOf("."), filename.length());

                if (!allowSet.contains(suffixName)) {
                    context.put(Constant.ERRORMSG, "Only support XML file type upload!");
                    return "input";
                }

            }
        }

        if (uploads != null) {
            InputStream ins = null;
            OutputStream ous = null;
            for (int i = 0; i < uploads.length; i++) {
                try {
                    ins = new FileInputStream(uploads[i]);
                    File singleFile = new File(path + File.separator + Utils.renameFileAppendSuffix(uploadFileNames[i], String.valueOf(++time)));
					ous = new FileOutputStream(singleFile);
                    int len = 0;
                    while ((len = ins.read(buffer)) > 0) {
                        ous.write(buffer, 0, len);

                    }
                    fileList.add(singleFile);
                } catch (FileNotFoundException e) {
                    context.put(Constant.ERRORMSG, "an error has occurred!");
                    return ERROR;

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    if (null != ins) {
                        try {
                            ins.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (ous != null) {
                        try {
                            ous.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }

        }

        testResultXmlResolverService.updateBatchTestResultForXML(context,fileList); 

        return SUCCESS;
    }	
	public String interfaceImportTestResultByXML() throws Exception {
			
		ActionContext context = ActionContext.getContext();
		//Manual set login status
		User user = new User();
		user.setUserName("import");
		context.getSession().put("user", user);
		
		long time = System.currentTimeMillis();
        ServletContext sc = (ServletContext) context.get(ServletActionContext.SERVLET_CONTEXT);
        String path = sc.getRealPath(savePath+File.separator+"testcase"+File.separator+Utils.dateFormat(new Date(), "yyyy_MM_dd"));
        File desDir = new File(path);
        if (!desDir.exists()) {
			desDir.mkdirs();
		}
      
        if (uploadFileNames == null) {
            context.put(Constant.ERRORMSG, "please choose file to upload!");
            return "input";
        }
        //Verify upload file type
        String[] allowTypes = this.allowTypes.split(",");
        HashSet<String> allowSet = new HashSet<String>();
        for (String allowType : allowTypes) {
            allowSet.add(allowType);
        }
        String filename = uploadFileNames[0];
        String suffixName = filename.substring(filename.lastIndexOf("."), filename.length());
        if (!allowSet.contains(suffixName)) {
            context.put(Constant.ERRORMSG, "Only support XML file type upload!");
            return "input";
        }

        if (uploads != null) {
            InputStream ins = null;
            OutputStream ous = null;
            for (int i = 0; i < uploads.length; i++) {
                try {
                    ins = new FileInputStream(uploads[i]);
                    File singleFile = new File(path + File.separator + Utils.renameFileAppendSuffix(uploadFileNames[i], String.valueOf(++time)));
					ous = new FileOutputStream(singleFile);
                    int len = 0;
                    while ((len = ins.read(buffer)) > 0) {
                        ous.write(buffer, 0, len);

                    }
                    fileList.add(singleFile);
                } catch (FileNotFoundException e) {
                    context.put(Constant.ERRORMSG, "an error has occurred!");
                    return ERROR;

                } catch (IOException e) {

                    e.printStackTrace();
                } finally {

                    if (null != ins) {
                        try {
                            ins.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if (ous != null) {
                        try {
                            ous.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }

        }
        
        testResultXmlResolverService.updateBatchTestResultForXML(context,fileList); 

        return SUCCESS;
    }	
}
