package vn.com.pharmacity.webapp;

/**
 * @author  
 * @date 2019/3/12 5:14 PM
 */
public class ResponseVO {

    /**
     * Whether the call is successful
     */
    private Boolean success;

    /**
     * The prompt information returned
     */
    private String message;

    /**
     * content
     */
    private Object content;

    public static ResponseVO buildSuccess() {
        ResponseVO response = new ResponseVO();
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildSuccess(Object content) {
        ResponseVO response = new ResponseVO();
        response.setContent(content);
        response.setSuccess(true);
        return response;
    }

    public static ResponseVO buildFailure(String message) {
        ResponseVO response = new ResponseVO();
        response.setSuccess(false);
        response.setMessage(message);
        System.out.println(message);
        return response;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
