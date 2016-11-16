package com.intel.cid.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.intel.cid.common.action.BaseAction;
import com.intel.cid.common.bean.Board;
import com.intel.cid.common.bean.TestPlan;
import com.intel.cid.common.bean.TestUnit;
import com.intel.cid.common.bean.User;
import com.intel.cid.common.service.BoardService;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class MailGenerator {

	public String[] getContentByBoardAllocate(BaseAction baseAction, User user,
			String operation, String[] boards) throws Exception {
		 HttpServletRequest httpRequest = ServletActionContext.getRequest();
		 String addr = "http://" + httpRequest.getServerName() + ":"
		 + httpRequest.getServerPort();
		Calendar calendar = new GregorianCalendar();
		String subject = "[STV-iTMS server] Board Allocation WW"
				+ calendar.get(Calendar.WEEK_OF_YEAR) + "."
				+ (calendar.get(Calendar.DAY_OF_WEEK) - 1);

		BoardService boardService = baseAction.getBoardService();
		List<Board> boardList = boardService.listOperationBoards(boards);

		for (Board board : boardList) {
			if (Utils.isNullORWhiteSpace(board.getStartDate())) {
				board.setStartDate("");
			}
			if (Utils.isNullORWhiteSpace(board.getEndDate())) {
				board.setEndDate("");
			}
			if (Utils.isNullORWhiteSpace(board.getComments())) {
				board.setComments("");
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("boardList", boardList);
		map.put("num", boardList.size());
		map.put("addr", addr);
		map.put("operation", operation);
		String ret[] = new String[2];
		ret[0] = subject;
		ret[1] = doFreemarkerMail(map, "allocated_board.ftl");
		return ret;
	}

	public String[] getContentByBoardRelease(List<Board> expireBoardList)
			throws Exception {
		Calendar calendar = new GregorianCalendar();
		String subject = "[STV-iTMS server] Board Allocation WW"
				+ calendar.get(Calendar.WEEK_OF_YEAR) + "."
				+ (calendar.get(Calendar.DAY_OF_WEEK) - 1);

		User user = expireBoardList.get(0).getUser();
		for (Board board : expireBoardList) {
			if (Utils.isNullORWhiteSpace(board.getStartDate())) {
				board.setStartDate("");
			}
			if (Utils.isNullORWhiteSpace(board.getEndDate())) {
				board.setEndDate("");
			}
			if (Utils.isNullORWhiteSpace(board.getComments())) {
				board.setComments("");
			}
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("user", user);
		map.put("boardList", expireBoardList);
		String ret[] = new String[2];
		ret[0] = subject;
		ret[1] = doFreemarkerMail(map, "autoreleased_boards.ftl");
		return ret;
	}
	
	public String[] getTasksAssignmentContent(BaseAction baseAction,TestPlan testPlan,List<TestUnit>testUnitList,User user) throws Exception {
		
		 HttpServletRequest httpRequest = ServletActionContext.getRequest();
		 String addr = "http://" + httpRequest.getServerName() + ":"
		 + httpRequest.getServerPort();
		 
		 String teamName = testPlan.getPlanName();
		 String subject = teamName+" Task assignment for Project: "+
				testPlan.getPlanName();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testPlan", testPlan);
		map.put("addr", addr);
		map.put("testUnitList", testUnitList);
		map.put("user", user);
		map.put("teamName", teamName);
		String ret[] = new String[2];
		ret[0] = subject;
		ret[1] = doFreemarkerMail(map, "subplan_assignment.ftl");
		return ret;
		
		
		
	}
	
	
	
	private String doFreemarkerMail(Map<String, Object> map, String template)
			throws Exception {
		Configuration freemarkerCfg = new Configuration();
		freemarkerCfg.setEncoding(Locale.getDefault(), "utf-8");
		freemarkerCfg.setClassForTemplateLoading(this.getClass(),
				"\\com\\intel\\cid\\common\\template");
		Template freetemplate = freemarkerCfg.getTemplate(template);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(bos);
		freetemplate.process(map, out);
		String content = bos.toString();
		out.close();
		return content;
	}
}
