package model;

import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Account> accounts;
    private List<Transaction> transactions;
    private int transactionCounter;

    public Bank() {
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        transactionCounter = 1;
    }

    // Add new account to bank
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // Transfer money between accounts
    public void transfer(Account from, Account to, double amount) {
        if (amount > 0 && from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);

            Transaction transaction =
                    new Transaction(transactionCounter++, "TRANSFER", amount);
            transactions.add(transaction);
        }
    }

    // Get all transactions
    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Get all accounts
    public List<Account> getAccounts() {
        return accounts;
    }
}
