package roman;

class RomanNumbersParser {


    int parseRomanToCommon(String expression) {

        if (expression.isEmpty()) {
            throw new IllegalArgumentException("expression cannot be empty");
        }

        return calculate(expression);


    }

    private int calculate(String expression) {

        char[] expressionChars = expression.toCharArray();
        int result = init(expressionChars[0]);

        for (int i = 1; i < expressionChars.length; i++) {

            String current = Character.toString(expressionChars[i]);
            String prev = Character.toString(expressionChars[i - 1]);
            int currentNumericValue = RomanNumbers.valueOf(current).getValue();
            int prevNumericValue = RomanNumbers.valueOf(prev).getValue();

            if (prevNumericValue < currentNumericValue) {
                result += currentNumericValue - 2 * prevNumericValue;
            } else {
                result += currentNumericValue;
            }

        }



        return result;
    }



    private int init(char romanNumber){

        return RomanNumbers.valueOf(Character.toString(romanNumber)).getValue();


    }
}
