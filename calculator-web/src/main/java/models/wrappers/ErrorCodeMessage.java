package models.wrappers;



public class ErrorCodeMessage {

    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public ErrorCodeMessage() {
    }

    public ErrorCodeMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }


}
