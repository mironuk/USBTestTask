package usbtesttask.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import usbtesttask.TestBase;
import usbtesttask.data.dto.TransactionDto;
import usbtesttask.data.model.Transaction;
import usbtesttask.data.repository.ClientRepository;
import usbtesttask.data.repository.TransactionRepository;

public class TransactionServiceTest extends TestBase {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @Before
    public void init() {
        transactionRepository.deleteAllNotCascaded();
        clientRepository.deleteAllNotCascaded();
    }

    @Test
    public void testSaveTransaction() {
        TransactionDto transactionDto = generateTransactionDto1();
        Transaction transaction = createTransactionFromTransactionDto(transactionDto);

        transaction.setClient(clientService.saveClient(transaction.getClient()));
        transaction = transactionService.saveTransaction(transaction);

        long transactionId = transaction.getTransactionId();

        Optional<Transaction> optionalTransaction = transactionService.getTransactionById(transactionId);
        assertTrue(optionalTransaction.isPresent());

        Transaction actualTransaction = optionalTransaction.get();

        assertEquals(transactionDto.getPlace(), actualTransaction.getPlace());
        assertEquals(transactionDto.getAmount(), actualTransaction.getAmount());
        assertEquals(transactionDto.getCurrency(), actualTransaction.getCurrency());
        assertEquals(transactionDto.getCard(), actualTransaction.getCard());
    }

    @Test
    public void testSaveAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();

        TransactionDto transactionDto1 = generateTransactionDto1();
        Transaction transaction1 = createTransactionFromTransactionDto(transactionDto1);
        transactions.add(transaction1);

        TransactionDto transactionDto2 = generateTransactionDto2();
        Transaction transaction2 = createTransactionFromTransactionDto(transactionDto2);
        transactions.add(transaction2);

        TransactionDto transactionDto3 = generateTransactionDto3();
        Transaction transaction3 = createTransactionFromTransactionDto(transactionDto3);
        transactions.add(transaction3);

        transaction1.setClient(clientService.checkAndSaveClient(transaction1.getClient()));
        transaction2.setClient(clientService.checkAndSaveClient(transaction2.getClient()));
        transaction3.setClient(clientService.checkAndSaveClient(transaction3.getClient()));

        transactions = transactionService.saveAllTransactions(transactions);

        long transactionId1 = transactions.get(0).getTransactionId();
        long transactionId2 = transactions.get(1).getTransactionId();
        long transactionId3 = transactions.get(2).getTransactionId();

        Optional<Transaction> optionalTransaction1 = transactionService.getTransactionById(transactionId1);
        assertTrue(optionalTransaction1.isPresent());

        Transaction actualTransaction1 = optionalTransaction1.get();

        assertEquals(transactionDto1.getPlace(), actualTransaction1.getPlace());
        assertEquals(transactionDto1.getAmount(), actualTransaction1.getAmount());
        assertEquals(transactionDto1.getCurrency(), actualTransaction1.getCurrency());
        assertEquals(transactionDto1.getCard(), actualTransaction1.getCard());

        Optional<Transaction> optionalTransaction2 = transactionService.getTransactionById(transactionId2);
        assertTrue(optionalTransaction2.isPresent());

        Transaction actualTransaction2 = optionalTransaction2.get();

        assertEquals(transactionDto2.getPlace(), actualTransaction2.getPlace());
        assertEquals(transactionDto2.getAmount(), actualTransaction2.getAmount());
        assertEquals(transactionDto2.getCurrency(), actualTransaction2.getCurrency());
        assertEquals(transactionDto2.getCard(), actualTransaction2.getCard());

        Optional<Transaction> optionalTransaction3 = transactionService.getTransactionById(transactionId3);
        assertTrue(optionalTransaction3.isPresent());

        Transaction actualTransaction3 = optionalTransaction3.get();

        assertEquals(transactionDto3.getPlace(), actualTransaction3.getPlace());
        assertEquals(transactionDto3.getAmount(), actualTransaction3.getAmount());
        assertEquals(transactionDto3.getCurrency(), actualTransaction3.getCurrency());
        assertEquals(transactionDto3.getCard(), actualTransaction3.getCard());
    }

    @Test
    public void testDeleteTransactionById() {
        TransactionDto transactionDto = generateTransactionDto1();
        Transaction transaction = createTransactionFromTransactionDto(transactionDto);

        transaction.setClient(clientService.saveClient(transaction.getClient()));
        transaction = transactionService.saveTransaction(transaction);

        long transactionId = transaction.getTransactionId();

        Optional<Transaction> optionalTransaction = transactionService.getTransactionById(transactionId);
        assertTrue(optionalTransaction.isPresent());

        transactionService.deleteTransactionById(transactionId);
        optionalTransaction = transactionService.getTransactionById(transactionId);
        assertFalse(optionalTransaction.isPresent());
    }

}
