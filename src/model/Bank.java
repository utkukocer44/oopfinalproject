package model;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

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

            Transaction transaction = new Transaction(transactionCounter++, "TRANSFER", amount);
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

    // EXPORT TRANSACTIONS TO CSV
    public void exportTransactionsToCSV(String fileName) {

        try (FileWriter writer = new FileWriter(fileName)) {

            // CSV header
            writer.append("TransactionId,Type,Amount,Date\n");

           for (Transaction t : transactions) {
  
            writer.append(String.valueOf(t.getTransactionId())).append(",");
            writer.append(t.getType()).append(",");  
            writer.append(String.valueOf(t.getAmount())).append(",");
            writer.append(t.getDate().toString()).append("\n");
}


            System.out.println("Transactions exported to " + fileName);

        } catch (IOException e) {
            System.out.println("Error while exporting CSV: " + e.getMessage());
        }
    }
}
