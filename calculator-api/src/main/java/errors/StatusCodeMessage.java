package errors;

public class StatusCodeMessage {
    private String message;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StatusCodeMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }


}
