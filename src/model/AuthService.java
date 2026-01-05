package model;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;

public class AuthService {

    private List<User> users;

    public AuthService() {
        users = new ArrayList<>();
    }

    public void loadUsersFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            reader.readLine(); // header atla

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                String username = data[0];
                String password = data[1];

                users.add(new User(username, password));
            }

            System.out.println("✅ Users loaded from " + fileName);

        } catch (Exception e) {
            System.out.println("❌ Error loading users CSV: " + e.getMessage());
        }
    }

    public User login(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username)
                    && u.checkPassword(password)) {
                return u;
            }
        }
        return null;
    }

    public User findUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    // 🔥 ACCOUNT SAHİBİNİ BUL (CSV SAVE İÇİN KRİTİK)
    public User findOwnerOfAccount(Account account) {
        for (User user : users) {
            if (user.getAccounts().contains(account)) {
                return user;
            }
        }
        return null;
    }
}
