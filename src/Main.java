import model.*;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        AuthService authService = new AuthService();
        Scanner sc = new Scanner(System.in);

        // === LOAD DATA ===
        authService.loadUsersFromCSV("src/data/users.csv");
        bank.loadAccountsFromCSV("src/data/accounts.csv", authService);

        // === LOGIN ===
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User loggedUser = authService.login(username, password);

        if (loggedUser == null) {
            System.out.println("❌ Hatalı giriş!");
            return;
        }

        System.out.println("✅ Hoş geldin " + loggedUser.getUsername());

        int choice;

        do {
            printMenu();
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showAccounts(loggedUser);
                    break;

                case 2:
                    handleDeposit(sc, bank, loggedUser);
                    break;

                case 3:
                    handleWithdraw(sc, bank, loggedUser);
                    break;

                case 4:
                    handleTransfer(sc, bank, loggedUser);
                    break;

                case 5:
                    showTransactions(bank, loggedUser);
                    break;

                case 6:
                    bank.exportTransactionsToCSV("src/data/transactions.csv");
                    System.out.println("👋 Çıkış yapıldı");
                    break;

                default:
                    System.out.println("❌ Geçersiz seçim");
            }

        } while (choice != 6);
    }

    // ===== MENU =====
    private static void printMenu() {
        System.out.println("\n--- BANK MENU ---");
        System.out.println("1. Hesapları Görüntüle");
        System.out.println("2. Para Yatır");
        System.out.println("3. Para Çek");
        System.out.println("4. Para Transferi");
        System.out.println("5. Transaction Geçmişi");
        System.out.println("6. Çıkış");
        System.out.print("Seçim: ");
    }

    // ===== SHOW ACCOUNTS =====
    private static void showAccounts(User user) {
        if (user.getAccounts().isEmpty()) {
            System.out.println("⚠️ Hesabınız yok");
            return;
        }

        for (Account acc : user.getAccounts()) {
            System.out.println(acc.getAccountNumber() +
                    " | Bakiye: " + acc.getBalance());
        }
    }

    // ===== SHOW USER TRANSACTIONS =====
    private static void showTransactions(Bank bank, User user) {

        List<Transaction> transactions = bank.getTransactionsForUser(user);

        if (transactions.isEmpty()) {
            System.out.println("⚠️ Size ait işlem yok");
            return;
        }

        System.out.println("\n--- TRANSACTION GEÇMİŞİNİZ ---");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    // ===== DEPOSIT =====
    private static void handleDeposit(Scanner sc, Bank bank, User user) {
        System.out.print("Hesap No: ");
        String accNo = sc.next();
        System.out.print("Tutar: ");
        double amount = sc.nextDouble();

        Account acc = bank.findAccountByNumber(accNo);

        if (acc != null && user.getAccounts().contains(acc)) {
            bank.deposit(acc, amount);
            System.out.println("✅ Para yatırıldı");
        } else {
            System.out.println("❌ Hesap bulunamadı");
        }
    }

    // ===== WITHDRAW =====
    private static void handleWithdraw(Scanner sc, Bank bank, User user) {
        System.out.print("Hesap No: ");
        String accNo = sc.next();
        System.out.print("Tutar: ");
        double amount = sc.nextDouble();

        Account acc = bank.findAccountByNumber(accNo);

        if (acc != null && user.getAccounts().contains(acc)) {
            if (bank.withdraw(acc, amount)) {
                System.out.println("✅ Para çekildi");
            } else {
                System.out.println("❌ Yetersiz bakiye");
            }
        } else {
            System.out.println("❌ Hesap bulunamadı");
        }
    }

    // ===== TRANSFER =====
    private static void handleTransfer(Scanner sc, Bank bank, User user) {
        System.out.print("Gönderen Hesap: ");
        String fromNo = sc.next();
        System.out.print("Alıcı Hesap: ");
        String toNo = sc.next();
        System.out.print("Tutar: ");
        double amount = sc.nextDouble();

        Account from = bank.findAccountByNumber(fromNo);
        Account to = bank.findAccountByNumber(toNo);

        if (from == null || to == null) {
            System.out.println("❌ Hesap bulunamadı");
        } else if (!user.getAccounts().contains(from)) {
            System.out.println("❌ Bu hesap size ait değil");
        } else {
            if (bank.transfer(from, to, amount)) {
                System.out.println("✅ Transfer başarılı");
            } else {
                System.out.println("❌ Yetersiz bakiye");
            }
        }
    }
}
