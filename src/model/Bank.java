package model;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Bank {

    private List<Account> accounts;
    private List<Transaction> transactions;
    private int transactionCounter;

    public Bank() {
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
        transactionCounter = 1;
    }

    // Add new account
    public void addAccount(Account account) {
        accounts.add(account);
    }

    // DEPOSIT with transaction
    public void deposit(Account account, double amount) {
        if (amount > 0) {
            account.deposit(amount);
            transactions.add(
                    new Transaction(transactionCounter++, "DEPOSIT", amount));
        }
    }

    // WITHDRAW with transaction
    public boolean withdraw(Account account, double amount) {
        if (amount > 0 && amount <= account.getBalance()) {
            account.withdraw(amount);
            transactions.add(
                    new Transaction(transactionCounter++, "WITHDRAW", amount));
            return true;
        }
        return false;
    }

    // TRANSFER with transaction
    public boolean transfer(Account from, Account to, double amount) {
        if (amount > 0 && from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);

            transactions.add(
                    new Transaction(transactionCounter++, "TRANSFER", amount));
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // FIND ACCOUNT BY NUMBER ✅ (HATA FIX)
    public Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    // EXPORT TRANSACTIONS
    public void exportTransactionsToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            writer.append("TransactionId,Type,Amount,Date\n");

            for (Transaction t : transactions) {
                writer.append(String.valueOf(t.getTransactionId())).append(",");
                writer.append(t.getType()).append(",");
                writer.append(String.valueOf(t.getAmount())).append(",");
                writer.append(t.getDate().toString()).append("\n");
            }

        } catch (IOException e) {
            System.out.println("CSV export error: " + e.getMessage());
        }
    }

    // LOAD ACCOUNTS (simple)
    public void loadAccountsFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            reader.readLine(); // header
            String line;

            while ((line = reader.readLine()) != null) {

                String[] d = line.split(",");
                String no = d[0];
                String type = d[1];
                double balance = Double.parseDouble(d[2]);
                double extra = Double.parseDouble(d[3]);

                if (type.equalsIgnoreCase("SAVINGS")) {
                    accounts.add(new SavingsAccount(no, balance, extra));
                } else if (type.equalsIgnoreCase("CHECKING")) {
                    accounts.add(new CheckingAccount(no, balance, extra));
                }
            }

        } catch (Exception e) {
            System.out.println("Account load error: " + e.getMessage());
        }
    }

    // LOAD ACCOUNTS WITH USERS
    public void loadAccountsFromCSV(String fileName, AuthService authService) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            reader.readLine(); // header
            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                String username = data[0];
                String accNo = data[1];
                String type = data[2];
                double balance = Double.parseDouble(data[3]);
                double extra = Double.parseDouble(data[4]);

                Account account;

                if (type.equalsIgnoreCase("SAVINGS")) {
                    account = new SavingsAccount(accNo, balance, extra);
                } else if (type.equalsIgnoreCase("CHECKING")) {
                    account = new CheckingAccount(accNo, balance, extra);
                } else {
                    continue;
                }

                User owner = authService.findUserByUsername(username);
                if (owner != null) {
                    owner.addAccount(account);
                    accounts.add(account);
                }
            }

            System.out.println("Accounts loaded from " + fileName);

        } catch (Exception e) {
            System.out.println("Error loading accounts CSV: " + e.getMessage());
        }
    }
}
