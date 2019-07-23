package errors;

public class ErrorCodeMessage {
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



    public ErrorCodeMessage(String message, int code) {
        this.message = message;
        this.code = code;
    }


}
