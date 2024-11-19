package Utils.authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static boolean isEmailByRegex(String email) {
        // Define the regular expression for a valid email
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" // local part before @
                + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,7}$"; // domain part

        // Compile the regex
        Pattern pattern = Pattern.compile(regex);

        // Check if the email matches the regex
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
