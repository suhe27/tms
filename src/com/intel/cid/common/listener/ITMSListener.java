//package com.intel.cid.common.listener;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import org.apache.log4j.Logger;
//import org.springframework.context.ApplicationContext;
//import org.springframework.web.context.support.WebApplicationContextUtils;
//
//import com.intel.cid.common.service.BoardService;
//
//public class ITMSListener implements ServletContextListener {
//
//	private static Logger logger = Logger.getLogger(ITMSListener.class);
//
//	private BoardService boardService;
//
//	
//	public void contextDestroyed(ServletContextEvent event) {
//		logger.info("listener ended!");
//	}
//
//	public void contextInitialized(ServletContextEvent event) {
//
//		ApplicationContext ac = null;
//
//		ac = WebApplicationContextUtils.getRequiredWebApplicationContext(event
//				.getServletContext());
//
//		boardService = ((BoardService) ac.getBean("boardService"));
//		new Timer().schedule(new BoardTask(), 0, 60 * 1000);
//	}
//	
//
//	public BoardService getBoardService() {
//		return boardService;
//	}
//
//	public void setBoardService(BoardService boardService) {
//		this.boardService = boardService;
//	}
//
//	class BoardTask extends TimerTask {
//		public void run() {
//
//			try {
//				boardService.TimerReleaseExpireBoards();
//			} catch (Exception e) {
//
//				e.printStackTrace();
//
//			}
//
//		}
//
//	}
//
//}
