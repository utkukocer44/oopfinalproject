package model;

public class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber, double balance, double interestRate) {
        super(accountNumber, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        }
    }

    public double calculateInterest() {
        return balance * interestRate;
    }

    // 🔥 EKSİK OLAN METHOD
    public double getInterestRate() {
        return interestRate;
    }
}
