package usbtesttask.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import usbtesttask.data.model.Client;

public class TransactionsXmlFileCreator {

    private static Random random = new Random();

    public static void createTransactionsXmlFile(String xmlFilePath, int clientsNumber, int transactionsNumber) {
        String[] currencies = { "UAH", "USD", "EUR" };

        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < clientsNumber; i++) {
            Client client = new Client();
            client.setFirstName(randomLatinLetterString(3, 8));
            client.setLastName(randomLatinLetterString(3, 12));
            client.setMiddleName(randomLatinLetterString(3, 15));
            client.setInn(randomNumericString(10, 10));
            clients.add(client);
        }

        File file = new File(xmlFilePath);
        FileWriter fr = null;
        BufferedWriter br = null;
        try {
            fr = new FileWriter(file);
            br = new BufferedWriter(fr);

            br.write("<?xml version='1.0' encoding='UTF-8'?>\r\n");
            br.write("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\r\n");
            br.write("\t<soap:Body>\r\n");
            br.write("\t\t<ns2:GetTransactionsResponse xmlns:ns2=\"http://dbo.qulix.com/ukrsibdbo\">\r\n");
            br.write("\t\t  <transactions>\r\n");

            for (int i = 0; i < transactionsNumber; i++) {
                br.write("\t\t    <transaction>\r\n");

                int placeNumber = random.nextInt(1000);
                String card = randomNumericString(6, 6) + "****" + randomNumericString(4, 4);
                String currency = currencies[random.nextInt(currencies.length)];
                String amount = randomNumericString(1, 6) + '.' + randomNumericString(2, 2);
                Client client = clients.get(random.nextInt(clients.size()));

                br.write("\t\t      <place>A PLACE " + placeNumber + "</place>\r\n");
                br.write("\t\t      <amount>" + amount + "</amount>\r\n");
                br.write("\t\t      <currency>" + currency + "</currency>\r\n");
                br.write("\t\t      <card>" + card + "</card>\r\n");
                br.write("\t\t      <client>\r\n");
                br.write("\t\t        <firstName>" + client.getFirstName() + "</firstName>\r\n");
                br.write("\t\t        <lastName>" + client.getLastName() + "</lastName>\r\n");
                br.write("\t\t        <middleName>" + client.getMiddleName() + "</middleName>\r\n");
                br.write("\t\t        <inn>" + client.getInn() + "</inn>\r\n");
                br.write("\t\t      </client>\r\n");

                br.write("\t\t    </transaction>\r\n");
            }

            br.write("\t\t  </transactions>\r\n");
            br.write("\t\t</ns2:GetTransactionsResponse>\r\n");
            br.write("\t</soap:Body>\r\n");
            br.write("</soap:Envelope>\r\n");

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static int randomLength(int lengthFrom, int lengthTo) {
        int length = random.nextInt(lengthTo - lengthFrom + 1) + lengthFrom;
        return length;
    }

    protected static String randomNumericString(int lengthFrom, int lengthTo) {
        int length = randomLength(lengthFrom, lengthTo);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    protected static String randomLatinLetterString(int lengthFrom, int lengthTo) {
        int length = randomLength(lengthFrom, lengthTo);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int charCode = random.nextInt(26) + 97;    // Latin lowercase letter
            if (i == 0) {
                charCode -= 32;                        // Capitalize first letter
            }
            sb.append(Character.valueOf((char) charCode));
        }
        return sb.toString();
    }

}
