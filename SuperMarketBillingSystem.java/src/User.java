import java.util.*;
import java.util.regex.Pattern;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
public class User {
    private String username;
    private String password;
    private String role; //Buyer or Seller
    private boolean isLoggedIn;

    public User (String username, String password, String role) {
        this.username = username;
        this.password = encryptPassword(password); // Encrypt password is optional
        this.role = role;
        this.isLoggedIn = false;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private String encryptPassword(String password) {
        try {
            // 1. Create the Hasher
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // 2. Hash the password (returns bytes)
            byte[] hashedBytes = md.digest(password.getBytes());

            // 3. Convert bytes to String using Base64 (No loops!)
            return Base64.getEncoder().encodeToString(hashedBytes);

        } catch (Exception e) {
            return null;
        }
    }

    public boolean validatePassword(String inputPassword) {
        String encryptedInput = encryptPassword(inputPassword);
        return encryptedInput != null && encryptedInput.equals(this.password);
    }

    public static boolean isValidPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false; // <--- New Flag

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else hasSpecial = true; // <--- Assume anything else is special
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
