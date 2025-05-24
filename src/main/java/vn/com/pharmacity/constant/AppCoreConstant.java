package vn.com.pharmacity.constant;

public class AppCoreConstant {

	// PASSWORD_WEB
	public static final String PASS_ENCRYPT = "********";

	// RESULT_STATUS
	public static final int RESULT_STATUS_SUCCESS = 1;
	public static final int RESULT_STATUS_FAIL = 0;

	// STRING CONSTANT
	public static final String STR_TRUE = "true";
	public static final String STR_FALSE = "false";

	// FORMAT DATE
	public static final String DDMMYYYY_TIME_HYPHEN = "dd-MM-yyyy hh:mm:ss";
	public static final String DDMMYYYY_SLASH = "dd/MM/yyyy";
	public static final String YYYYMMDD_TIME = "yyyyMMddHHmmss";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String DDMMYYYY_TIME_SLASH = "dd/MM/yyyy hh:mm:ss";
	public static final String YYMMDD = "yyMMdd";

	// REPONSE CODE - MESSAGE
	public static final String ERROR = "error";
	public static final String SUCCESS = "success";
	public static final String INFO = "info";
	public static final String WARNING = "warning";
	public static final int RESULT_CODE_SYSTEM_ERROR = -99;
	public static final int SUCCESS_CODE = 200;

	// REQUEST METHOD
	public static final String REQUEST_POST = "POST";
	public static final String REQUEST_PUT = "PUT";
	public static final String REQUEST_DELETE = "DELETE";

	// SEQUENCE
	public static final String SEQ = "SEQ_";

	// CHARACTER SPECIAL
	/** QUESTION_MARK */
	public static final String QUESTION_MARK = "?";
	public static final String EMPTY = "";
	public static final String UNDERLINED = "_";
	public static final String HYPHEN = "-";
	public static final String BLANK = " ";
	public static final String ASTERISK = "*";
	public static final String DOT = ".";
	public static final String COMMA = ",";
	public static final String SEMI_COLON = ";";
	/** HASHTAG */
	public static final String HASHTAG = "#";

	// TABLE
	public static final String TABLE_ROLE = "Roles";
	public static final String TABLE_USERS = "Users";
	public static final String TABLE_USER_SESSIONS = "UserSessions";
	public static final String TABLE_MEDICINE = "Medicine";
    public static final String TABLE_MEDICINE_GROUP = "MedicineGroups";
    public static final String TABLE_MEDICINE_TYPE = "MedicineTypes";
    public static final String TABLE_MEDICINE_UNIT = "MedicineUnits";
    public static final String TABLE_MEDICINE_SUPPLIER = "Suppliers";
	public static final String TABLE_AUDIT_LOGS = "AuditLogs";
	//public static final String TABLE_TICKET_DETAIL_SEAT = "ticket_detail_seat";
//	public static final String TABLE_ROW_OF_SEAT = "row_of_seat";
//	public static final String TABLE_SEAT_DETAIL = "seat_detail";

	// EXTERNAL CONFIG DATA SOURCE HIKARI
	public static final String SPRING_DATASOURCE_POOL_NAME = "spring.datasource.hikari.poolName";
	public static final String SPRING_DATASOURCE_AUTO_COMMIT = "spring.datasource.hikari.auto-commit";
	public static final String SPRING_DATASOURCE_REGISTER_MBEANS = "spring.datasource.hikari.register-mbeans";
	public static final String SPRING_DATASOURCE_MINIMUM_IDLE = "spring.datasource.hikari.minimum-idle";
	public static final String SPRING_DATASOURCE_IDLE_TIMEOUT = "spring.datasource.hikari.idle-timeout";
	public static final String SPRING_DATASOURCE_CONNECTION_TIMEOUT = "spring.datasource.hikari.connection-timeout";
	public static final String SPRING_DATASOURCE_MAX_LIFETIME = "spring.datasource.hikari.max-lifetime";
	// CONFIG DATASOURCE
	public static final String SPRING_DATASOURCE_DRIVER_CLASS_NAME = "spring.datasource.driver-class-name";
	public static final String SPRING_DATASOURCE_URL = "spring.datasource.url";
	public static final String SPRING_DATASOURCE_USERNAME = "spring.datasource.username";
	public static final String SPRING_DATASOURCE_PASSWORD = "spring.datasource.password";
	public static final String SPRING_DATASOURCE_JNDI_NAME = "spring.datasource.jndi-name";
	

    public static final String MSG_LIST = "messageList";
    public static final String MSG_SUCCESS_SAVE = "message.success.save.label";
    public static final String MSG_FAIL_SUBMIT = "message.submit.fail";
}
