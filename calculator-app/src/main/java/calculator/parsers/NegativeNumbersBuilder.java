package calculator.parsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used for finding and adding negative numbers to the List of elements of expression
 */
public class NegativeNumbersBuilder {

    /**
     *  Searches for brackets with minus and number only inside if one is found they are concated and added to the list
     * @param numbersAndOperations the List of elements to find and add negative numbers
     * @return List with operations and numbers, which can now include negative numbers
     */
    public List<String> buildListWithOperatorsAndNegativeNumbers(List<String> numbersAndOperations) {
        numbersAndOperations = deleteWhiteSpaces(numbersAndOperations);
        Iterator iterator = numbersAndOperations.iterator();
        List<String> listWithNegativeNumbers = new ArrayList<>();

        String prev = "";
        while (iterator.hasNext()) {
            String curr = (String) iterator.next();
            if (isMinus(curr) && isBracketOrEmpty(prev) && iterator.hasNext()) {
                String next = (String) iterator.next();
                curr = curr.concat(next);
                iterator.remove();
            }
            listWithNegativeNumbers.add(curr);
            prev = curr;
        }
        return listWithNegativeNumbers;
    }

    private List<String> deleteWhiteSpaces(List<String> numbersAndOperations) {
        return numbersAndOperations.stream().filter(element -> !element.equals(" ") && !element.equals("")).collect(Collectors.toList());
    }

    private boolean isMinus(String s) {
        return s.equals("-");
    }

    private boolean isBracketOrEmpty(String s) {
        return s.matches("[()]") || s.isEmpty();
    }

}
