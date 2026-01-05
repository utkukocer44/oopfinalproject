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

    // =====================
    // ACCOUNT OPERATIONS
    // =====================

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public Account findAccountByNumber(String accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        return null;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // =====================
    // TRANSACTIONS
    // =====================

    public void deposit(Account account, double amount) {
        if (amount <= 0)
            return;

        account.deposit(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                "DEPOSIT",
                amount,
                null,
                account.getAccountNumber()));
    }

    public boolean withdraw(Account account, double amount) {
        if (amount <= 0 || amount > account.getBalance()) {
            return false;
        }

        account.withdraw(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                "WITHDRAW",
                amount,
                account.getAccountNumber(),
                null));
        return true;
    }

    public boolean transfer(Account from, Account to, double amount) {
        if (amount <= 0 || from.getBalance() < amount) {
            return false;
        }

        from.withdraw(amount);
        to.deposit(amount);

        transactions.add(new Transaction(
                transactionCounter++,
                "TRANSFER",
                amount,
                from.getAccountNumber(),
                to.getAccountNumber()));
        return true;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // =====================
    // CSV EXPORT
    // =====================

    public void exportTransactionsToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {

            writer.append("id,type,amount,fromAccount,toAccount,date\n");

            for (Transaction t : transactions) {
                writer.append(t.getTransactionId() + ",")
                        .append(t.getType() + ",")
                        .append(t.getAmount() + ",")
                        .append(t.getFromAccount() + ",")
                        .append(t.getToAccount() + ",")
                        .append(t.getDate().toString())
                        .append("\n");
            }

            System.out.println("Transactions exported to " + fileName);

        } catch (IOException e) {
            System.out.println("CSV export error: " + e.getMessage());
        }
    }

    // =====================
    // CSV LOAD
    // =====================

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
