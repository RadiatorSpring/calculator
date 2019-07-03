package roman;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RomanNumbersParserTest {


    private roman.RomanNumbersParser romanNumbersParser;

    @Before
    public void setup() {
        romanNumbersParser = new roman.RomanNumbersParser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void findRomanNumbersGivesException() {
        assertEquals("expected Exception",0, romanNumbersParser.parseRomanToCommon(""));
    }
    @Test
    public void findNumber1(){
        assertEquals(1, romanNumbersParser.parseRomanToCommon("I"));
    }

    @Test
    public void findNumber2(){
        assertEquals(2, romanNumbersParser.parseRomanToCommon("II"));
    }

    @Test
    public void findNumber4(){
        assertEquals(4, romanNumbersParser.parseRomanToCommon("IV"));
    }

    @Test
    public void findNumber5(){
        assertEquals(5, romanNumbersParser.parseRomanToCommon("V"));
    }

    @Test
    public void findNumber6(){
        assertEquals(6, romanNumbersParser.parseRomanToCommon("VI"));

    }

    @Test
    public void find9(){
        assertEquals(9, romanNumbersParser.parseRomanToCommon("IX"));
    }

    @Test
    public void find49(){
        assertEquals(49,romanNumbersParser.parseRomanToCommon("IL"));
    }

    @Test
    public void find147(){
        assertEquals(147,romanNumbersParser.parseRomanToCommon("CXLVII"));
    }

    @Test
    public void find1997(){
        assertEquals(1997,romanNumbersParser.parseRomanToCommon("MCMXCVII"));
    }



}
