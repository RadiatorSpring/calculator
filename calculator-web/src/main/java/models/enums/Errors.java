package models.enums;

public enum Errors {
    EMPTY_STACK_EXCEPTION_MESSAGE("The number of operators cannot be greater than the number of operands, using negative numbers requires brackets", 400),
    ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE("There cannot be letters nor spaces between digits and there should be at least 2 operands and 1 operator", 400),
    EMPTY_PARAMETER_EXCEPTION("The expression parameter cannot be empty", 400),
    CANNOT_DIVIDE_BY_ZERO("You cannot divide by zero", 400),
    GENERAL_EXCEPTION_MESSAGE("Something went wrong", 500),
    IS_NOT_EVALUATED("Is not evaluated", 202),
    DOES_NOT_EXIST("There is no entity with this id", 400);


    private String message;
    private int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Errors(String message, int code) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    Errors(int code) {
        this.code = code;
    }

    public static int getCodeWithString(String message) {
        for (Errors e : Errors.values()) {
            if(e.getMessage().equals(message)){
                return e.getCode();
            }
        }
        return -1;
    }
}
