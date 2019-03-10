package usbtesttask.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import usbtesttask.data.model.Transaction;
import usbtesttask.data.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Transactional
    public List<Transaction> saveAllTransactions(List<Transaction> transactions) {
        return transactionRepository.saveAll(transactions);
    }

    public Optional<Transaction> getTransactionById(long transactionId) {
        return transactionRepository.findById(transactionId);
    }

    @Transactional
    public void deleteTransactionById(long transactionId) {
        transactionRepository.deleteById(transactionId);
    }

}
