package com.intel.cid.common.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.constant.Constant;
import com.intel.cid.common.dao.impl.TestPlanDaoImpl;
import com.intel.cid.common.dao.impl.TestPlanTrackDaoImpl;
import com.intel.cid.common.net.Client;
import com.intel.cid.utils.Utils;

public class DBBackupService {

	public static final Logger logger = Logger.getLogger(DBBackupService.class);

	private TestPlanTrackDaoImpl testPlanTrackDaoImpl;

	private TestPlanDaoImpl testPlanDaoImpl;

	public void backUpDB() {

		try {
			logger.info("DB Backup Started!");
			Runtime rt = Runtime.getRuntime();
			Process child = null;
			Properties prop = System.getProperties();
			String os = prop.getProperty("os.name");
			if (os.startsWith("win") || os.startsWith("Win")) {
				child = rt
						.exec("cmd /c mysqldump --user=root --password=tester --opt -ce  -R --set-charset=utf8 isg ");

			} else {
				child = rt
						.exec("mysqldump -uroot -ptester  -ce  -R --set-charset=utf8 itms ");
			}

			InputStream in = child.getInputStream();

			InputStreamReader isReader = new InputStreamReader(in, "utf8");

			String inStr;
			StringBuffer sb = new StringBuffer("");
			String outStr;
			File desFile = null;
			BufferedReader br = new BufferedReader(isReader);
			while ((inStr = br.readLine()) != null) {
				sb.append(inStr + "\r\n");
			}
			outStr = sb.toString();
			String dateStr = Utils
					.dateFormat(new Date(), "yyyy-MM-dd_HH_mm_ss");
			FileOutputStream fout = null;
			if (os.startsWith("win") || os.startsWith("Win")) {
				File desDir = new File("C://test//DB//");
				if (!desDir.exists()) {
					desDir.mkdirs();
				}
				desFile = new File("C://test//DB//" + dateStr + "_backup.sql");
				fout = new FileOutputStream(desFile);
				OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
				writer.write(outStr);
				writer.flush();
				in.close();
				isReader.close();
				br.close();
				writer.close();
				fout.close();

			} else {

				desFile = new File("/var/www/iTMSDB/" + dateStr + "_backup.sql");
				fout = new FileOutputStream(desFile);
				OutputStreamWriter writer = new OutputStreamWriter(fout, "utf8");
				writer.write(outStr);
				writer.flush();
				in.close();
				isReader.close();
				br.close();
				writer.close();
				fout.close();
				new Client("10.239.128.72", 9008).sendFile(desFile);

			}

			logger.info("DB Backup Success!");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void backupTestPlan() throws Exception {

		logger.info("start to backup TestPlan....");
		String createDate = Utils.dateFormat(new Date(), Constant.DATE_FORMAT_YMD);
		List<TestPlan> needTestPlanList = new ArrayList<TestPlan>();
		long nowTime = Utils.dateFormat(createDate,  Constant.DATE_FORMAT_YMD);
		
//TODO	
		List<TestPlan> testPlanList = testPlanDaoImpl.listAllTestPlans(1);
	
		for (TestPlan testPlan : testPlanList) {

			String startDate = testPlan.getStartDate();
			String endDate = testPlan.getEndDate();
			if (!Utils.isNullORWhiteSpace(startDate)
					&& !Utils.isNullORWhiteSpace(endDate)) {

				if (nowTime >= Utils.dateFormat(startDate, Constant.DATE_FORMAT_YMD)
						&& nowTime <= Utils.dateFormat(endDate,  Constant.DATE_FORMAT_YMD)) {

					testPlan.setCreateDate(createDate);
					needTestPlanList.add(testPlan);
				}

			}

		}

		testPlanTrackDaoImpl.addBatchTestPlanStatus(needTestPlanList);
		logger.info("end to backup TestPlan....Success!");

	}

	public TestPlanTrackDaoImpl getTestPlanTrackDaoImpl() {
		return testPlanTrackDaoImpl;
	}

	public void setTestPlanTrackDaoImpl(
			TestPlanTrackDaoImpl testPlanTrackDaoImpl) {
		this.testPlanTrackDaoImpl = testPlanTrackDaoImpl;
	}

	public TestPlanDaoImpl getTestPlanDaoImpl() {
		return testPlanDaoImpl;
	}

	public void setTestPlanDaoImpl(TestPlanDaoImpl testPlanDaoImpl) {
		this.testPlanDaoImpl = testPlanDaoImpl;
	}

}
