package calculator.parsers;

import java.util.Queue;

public interface Parser {
    Queue<String> parse(String expression) ;
}
