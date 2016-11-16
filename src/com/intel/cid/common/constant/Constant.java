package com.intel.cid.common.constant;

public class Constant {

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String ERRORMSG = "errorMsg";

	public static final String SUCCESSMSG = "successMsg";
	
	public static final int RESULT_NONE = 0;
	
	public static final int RESULT_PASS = 1;

	public static final int RESULT_FAIL = 2;

	public static final int RESULT_NOTRUN = 3;

	public static final int RESULT_BLOCK = 4;

	public static final int BOARD_STATUS_IDLE = 1;

	public static final int BOARD_STATUS_INUSE = 2;

	public static final int BOARD_STATUS_BROKEN = 3;

	public static final String BOARD_OPER_APPLY = "apply";

	public static final String BOARD_OPER_RELEASE = "release";

	public static final int USER_AUTHORITY_ADMIN = 9;

	public static final int USER_AUTHORITY_GUEST = 1;

	public static final String POSITIVE_INTEGER_REGEX = "^[1-9][0-9]{0,10}$";

	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	
	public static final String DB_EXITS = "exits";
	
	public static final String DB_MYSQL = "mysql";
		
	public static final String PROJECT_DPDK="DPDK";
	
	public static final String PROJECT_MEDIA="MEDIA";
	
	public static final String PROJECT_STORAGE="STORAGE";
	
	public static final String PROJECT_WIKIKI="WIKIKI";
}
