package usbtesttask;

import java.math.BigDecimal;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import usbtesttask.data.dto.ClientDto;
import usbtesttask.data.dto.TransactionDto;
import usbtesttask.data.model.Client;
import usbtesttask.data.model.Transaction;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class TestBase {

    protected static TransactionDto generateTransactionDto1() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setPlace("A PLACE 1");
        transactionDto.setAmount(new BigDecimal("10.01"));
        transactionDto.setCurrency("UAH");
        transactionDto.setCard("123456****1234");
        transactionDto.setClientDto(generateClientDto1());
        return transactionDto;
    }

    protected static TransactionDto generateTransactionDto2() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setPlace("A PLACE 2");
        transactionDto.setAmount(new BigDecimal("9876.01"));
        transactionDto.setCurrency("USD");
        transactionDto.setCard("123456****2345");
        transactionDto.setClientDto(generateClientDto2());
        return transactionDto;
    }

    protected static TransactionDto generateTransactionDto3() {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setPlace("A PLACE 3");
        transactionDto.setAmount(new BigDecimal("12.01"));
        transactionDto.setCurrency("EUR");
        transactionDto.setCard("123456****3456");
        transactionDto.setClientDto(generateClientDto2());
        return transactionDto;
    }



    protected static ClientDto generateClientDto1() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("Ivan");
        clientDto.setLastName("Ivanoff");
        clientDto.setMiddleName("Ivanoffich");
        clientDto.setInn("1234567890");
        return clientDto;
    }

    protected static ClientDto generateClientDto2() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("Petr");
        clientDto.setLastName("Petroff");
        clientDto.setMiddleName("Petroffich");
        clientDto.setInn("1234567891");
        return clientDto;
    }

    protected static ClientDto generateClientDto3() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("Sidor");
        clientDto.setLastName("Sidoroff");
        clientDto.setMiddleName("Sidoroffich");
        clientDto.setInn("1234567892");
        return clientDto;
    }



    protected static Transaction createTransactionFromTransactionDto(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setPlace(transactionDto.getPlace());
        transaction.setAmount(transactionDto.getAmount());
        transaction.setCurrency(transactionDto.getCurrency());
        transaction.setCard(transactionDto.getCard());
        transaction.setClient(createClientFromClientDto(transactionDto.getClientDto()));
        return transaction;
    }

    protected static Client createClientFromClientDto(ClientDto clientDto) {
        Client client = new Client();
        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setMiddleName(clientDto.getMiddleName());
        client.setInn(clientDto.getInn());
        return client;
    }

}
