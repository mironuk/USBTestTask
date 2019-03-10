package usbtesttask;

import static usbtesttask.common.CommonConstants.DEFAULT_ENCODING;
import static usbtesttask.util.StringUtil.removeUnderscores;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import usbtesttask.service.SaxHandler;
import usbtesttask.util.Output;
import usbtesttask.util.TransactionsXmlFileCreator;

@SpringBootApplication
public class USBTestTaskApplication implements CommandLineRunner {

    private static final String GENERATE_COMMAND_LINE_PARAM = "-generate";
    private static final String PARSE_COMMAND_LINE_PARAM = "-parse";

    @Autowired
    private Environment environment;

    @Autowired
    private SaxHandler saxHandler;

    public static void main(String[] args) {
        if ((args.length == 4 && args[0].equals(GENERATE_COMMAND_LINE_PARAM))
                || (args.length == 2 && args[0].equals(PARSE_COMMAND_LINE_PARAM))) {
            Output.info("Starting application...");
            SpringApplication app = new SpringApplication(USBTestTaskApplication.class);
            app.setBannerMode(Mode.OFF);
            app.setLogStartupInfo(false);
            app.run(args);
        } else {
            Output.error("JAR command line parameters (one of the following):");
            Output.error("-generate <path-to-XML-file-to-save> <unique-clients-number> <transactions-number>");
            Output.error("-parse <path-to-XML-file-to-parse>");
        }
    }

    @Override
    public void run(String... args) throws Exception {
        if (!isTestProfile()) {
            Output.info("Application started.");
            if (args[0].equals(GENERATE_COMMAND_LINE_PARAM)) {
                generate(args);
            } else if (args[0].equals(PARSE_COMMAND_LINE_PARAM)) {
                parse(args);
            }
        }
    }

    private boolean isTestProfile() {
        String[] profiles = environment.getActiveProfiles();
        for (String profile : profiles) {
            if (profile != null && profile.equalsIgnoreCase("test")) {
                return true;
            }
        }
        return false;
    }

    private void generate(String... args) {
        Integer clientsNumber;
        Integer transactionsNumber;
        String pathToXmlFile = args[1];
        try {
            clientsNumber = Integer.valueOf(removeUnderscores(args[2]));
            transactionsNumber = Integer.valueOf(removeUnderscores(args[3]));
        } catch (NumberFormatException ex) {
            Output.error("<unique-clients-number> and <transactions-number> command line parameters should be positive integers.");
            return;
        }
        long startMillis = System.currentTimeMillis();

        TransactionsXmlFileCreator.createTransactionsXmlFile(pathToXmlFile, clientsNumber, transactionsNumber);

        long finishMillis = System.currentTimeMillis();
        long seconds = (finishMillis - startMillis) / 1000;
        Output.info(String.format("Done in %d second%s.", seconds, (seconds == 1 ? "" : "s")));
    }

    private void parse(String... args) {
        try {
            String pathToXmlFile = args[1];

            InputStream inputStream = new FileInputStream(pathToXmlFile);
            Reader reader = new InputStreamReader(inputStream, DEFAULT_ENCODING);

            InputSource inputSource = new InputSource(reader);
            inputSource.setEncoding(DEFAULT_ENCODING);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(inputSource, saxHandler);
            Output.info(String.format("Transactions found and saved: %,9d.", saxHandler.getTransactionsNumber()));
            long seconds = saxHandler.getSeconds();
            Output.info(String.format("Done in %d second%s.", seconds, (seconds == 1 ? "" : "s")));
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Output.error("Error: " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
    }

}
