package usbtesttask.util;

import static org.junit.Assert.assertTrue;
import static usbtesttask.common.TestConstants.TEST_XML_FILE_CLIENTS_NUMBER;
import static usbtesttask.common.TestConstants.TEST_XML_FILE_MIN_FILE_SIZE;
import static usbtesttask.common.TestConstants.TEST_XML_FILE_PATH;
import static usbtesttask.common.TestConstants.TEST_XML_FILE_TRANSACTIONS_NUMBER;

import java.io.File;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class TransactionsXmlFileCreatorTest {

    /**
     * Generates test XML file which should be tested for parsing with {@link usbtesttask.service.SaxHandlerTest}.<br/><br/>
     * So, {@link usbtesttask.service.SaxHandlerTest} depends on successfull run of this test.<br/><br/>
     * Test XML file is not deleted before or after tests so these two tests can be run in any order.
     */
    @Test
    public void testCreateTransactionsXmlFile() {
        TransactionsXmlFileCreator.createTransactionsXmlFile(TEST_XML_FILE_PATH,
                TEST_XML_FILE_CLIENTS_NUMBER,
                TEST_XML_FILE_TRANSACTIONS_NUMBER);

        File file = new File(TEST_XML_FILE_PATH);
        assertTrue(file.exists());
        assertTrue(file.isFile());
        assertTrue(file.length() > TEST_XML_FILE_MIN_FILE_SIZE);
    }

    @Test
    public void testRandomLength() {
        int min = 1;
        int max = 100;
        boolean minFound = false;
        boolean maxFound = false;
        for (int i = 0; i < 1_000_000; i++) {
            int number = TransactionsXmlFileCreator.randomLength(min, max);
            assertTrue(number >= min);
            assertTrue(number <= max);
            if (number == min) {
                minFound = true;
            }
            if (number == max) {
                maxFound = true;
            }
        }
        assertTrue(minFound);
        assertTrue(maxFound);
    }

    @Test
    public void testRandomNumericString() {
        int lengthFrom = 1;
        int lengthTo = 20;
        for (int i = 0; i < 100_000; i++) {
            String string = TransactionsXmlFileCreator.randomNumericString(lengthFrom, lengthTo);
            assertTrue(string.matches("[0-9]{1,20}"));
        }
    }

    @Test
    public void testRandomLatinLetterString() {
        int lengthFrom = 1;
        int lengthTo = 20;
        for (int i = 0; i < 100_000; i++) {
            String string = TransactionsXmlFileCreator.randomLatinLetterString(lengthFrom, lengthTo);
            assertTrue(string.matches("[A-Z][a-z]{0,19}"));
        }
    }

}
