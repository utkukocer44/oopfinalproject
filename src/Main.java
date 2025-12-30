
import model.*;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();

        Account savings =
                new SavingsAccount("SA-1001", 5000, 0.05);

        Account checking =
                new CheckingAccount("CA-2001", 2000, 1000);

        bank.addAccount(savings);
        bank.addAccount(checking);

        savings.deposit(1000);
        savings.withdraw(500);

        checking.withdraw(2500); // overdraft test

        bank.transfer(savings, checking, 1000);

        System.out.println("Savings balance: " + savings.getBalance());
        System.out.println("Checking balance: " + checking.getBalance());

        System.out.println("\nTransaction History:");
        for (Transaction t : bank.getTransactions()) {
            System.out.println(t);
        }
    }
}
