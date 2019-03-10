package usbtesttask.service;

import static usbtesttask.util.StringUtil.getText;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import usbtesttask.data.model.Client;
import usbtesttask.data.model.Transaction;
import usbtesttask.util.Output;

@Service
public class SaxHandler extends DefaultHandler {

    private static final int BATCH_SAVE_NUMBER = 10_000;
    private static final int OUTPUT_NUMBER = 10_000;

    private static final String TRANSACTIONS_ELEMENT_NAME = "transactions";
    private static final String TRANSACTION_ELEMENT_NAME = "transaction";
    private static final String PLACE_ELEMENT_NAME = "place";
    private static final String AMOUNT_ELEMENT_NAME = "amount";
    private static final String CURRENCY_ELEMENT_NAME = "currency";
    private static final String CARD_ELEMENT_NAME = "card";
    private static final String CLIENT_ELEMENT_NAME = "client";
    private static final String FIRST_NAME_ELEMENT_NAME = "firstName";
    private static final String LAST_NAME_ELEMENT_NAME = "lastName";
    private static final String MIDDLE_NAME_ELEMENT_NAME = "middleName";
    private static final String INN_ELEMENT_NAME = "inn";

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    private LinkedList<String> elements = new LinkedList<>();
    private Transaction transaction = new Transaction();
    private List<Transaction> transactions = new ArrayList<>(BATCH_SAVE_NUMBER);
    private long transactionsNumber = 0;
    private long startMillis = 0;

    public SaxHandler() {
    }

    @Override
    public void startDocument() throws SAXException {
        startMillis = System.currentTimeMillis();
    }

    @Override
    public void endDocument() throws SAXException {
        if (transactions.size() > 0) {
            transactionService.saveAllTransactions(transactions);
            transactions.clear();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        validateElement(qName, TRANSACTION_ELEMENT_NAME, TRANSACTIONS_ELEMENT_NAME);
        validateElement(qName, PLACE_ELEMENT_NAME, TRANSACTION_ELEMENT_NAME);
        validateElement(qName, AMOUNT_ELEMENT_NAME, TRANSACTION_ELEMENT_NAME);
        validateElement(qName, CURRENCY_ELEMENT_NAME, TRANSACTION_ELEMENT_NAME);
        validateElement(qName, CARD_ELEMENT_NAME, TRANSACTION_ELEMENT_NAME);
        validateElement(qName, CLIENT_ELEMENT_NAME, TRANSACTION_ELEMENT_NAME);
        validateElement(qName, FIRST_NAME_ELEMENT_NAME, CLIENT_ELEMENT_NAME);
        validateElement(qName, LAST_NAME_ELEMENT_NAME, CLIENT_ELEMENT_NAME);
        validateElement(qName, MIDDLE_NAME_ELEMENT_NAME, CLIENT_ELEMENT_NAME);
        validateElement(qName, INN_ELEMENT_NAME, CLIENT_ELEMENT_NAME);

        if (qName.equalsIgnoreCase(TRANSACTION_ELEMENT_NAME)) {
            transaction = new Transaction();
        } else if (qName.equalsIgnoreCase(CLIENT_ELEMENT_NAME)) {
            transaction.setClient(new Client());
        }
        elements.add(qName);
    }

    private void validateElement(String qName, String elementName, String parentElementName) throws SAXException {
        if (qName.equalsIgnoreCase(elementName)) {
            if (elements.isEmpty() || !elements.getLast().equalsIgnoreCase(parentElementName)) {
                String message = "<" + elementName + "> element is not inside <" + parentElementName + "> element.";
                Output.error(message);
                throw new SAXException(message);
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!elements.isEmpty()) {
            String element = elements.removeLast();
            if (element.equalsIgnoreCase(TRANSACTION_ELEMENT_NAME)) {

                Client client = transaction.getClient();
                client = clientService.checkAndSaveClient(client);
                transaction.setClient(client);

                transactions.add(transaction);
                transactionsNumber++;
                if (transactionsNumber % BATCH_SAVE_NUMBER == 0) {
                    transactionService.saveAllTransactions(transactions);
                    transactions.clear();
                }
                if (transactionsNumber % OUTPUT_NUMBER == 0) {
                    long seconds = getSeconds();
                    Output.info(String.format("Transactions found:   %,9d.   Done in %d second%s.",
                            transactionsNumber, seconds, (seconds == 1 ? "" : "s")));
                }
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (!elements.isEmpty()) {
            String element = elements.getLast();
            if (element.equalsIgnoreCase(PLACE_ELEMENT_NAME)) {
                transaction.setPlace(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(AMOUNT_ELEMENT_NAME)) {
                transaction.setAmount(new BigDecimal(getText(ch, start, length)));
            } else if (element.equalsIgnoreCase(CURRENCY_ELEMENT_NAME)) {
                transaction.setCurrency(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(CARD_ELEMENT_NAME)) {
                transaction.setCard(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(FIRST_NAME_ELEMENT_NAME)) {
                transaction.getClient().setFirstName(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(LAST_NAME_ELEMENT_NAME)) {
                transaction.getClient().setLastName(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(MIDDLE_NAME_ELEMENT_NAME)) {
                transaction.getClient().setMiddleName(getText(ch, start, length));
            } else if (element.equalsIgnoreCase(INN_ELEMENT_NAME)) {
                transaction.getClient().setInn(getText(ch, start, length));
            }
        }
    }

    public long getTransactionsNumber() {
        return transactionsNumber;
    }

    public long getSeconds() {
        long finishMillis = System.currentTimeMillis();
        return ((finishMillis - startMillis) / 1000);
    }

}
