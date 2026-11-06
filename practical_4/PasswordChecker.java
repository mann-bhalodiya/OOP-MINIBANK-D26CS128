package practical_4;

public class PasswordChecker {

    public static boolean[] checkRules(String pw) {

        boolean[] rules = new boolean[4];

        // Rule 1: Length should be at least 8
        rules[0] = pw.length() >= 8;

        // Rule 2: Contains an uppercase letter
        rules[1] = pw.matches(".*[A-Z].*");

        // Rule 3: Contains a digit
        rules[2] = pw.matches(".*[0-9].*");

        // Rule 4: Contains a special character
        rules[3] = pw.matches(".*[^a-zA-Z0-9].*");

        return rules;
    }

    public static String strength(String pw) {

        boolean[] rules = checkRules(pw);

        int count = 0;

        for (boolean rule : rules) {
            if (rule) {
                count++;
            }
        }

        if (count <= 1) {
            return "Weak";
        } else if (count <= 3) {
            return "Medium";
        } else {
            return "Strong";
        }
    }
}
