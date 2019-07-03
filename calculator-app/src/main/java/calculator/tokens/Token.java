package calculator.tokens;

import java.util.List;

public interface Token {
    void process(StringBuilder sb, List<String> list);
}
