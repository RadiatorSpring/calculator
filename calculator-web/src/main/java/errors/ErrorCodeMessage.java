package errors;



public class ErrorCodeMessage {
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

    private String message;
    private int code;


    public ErrorCodeMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }


}
