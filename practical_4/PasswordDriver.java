package practical_4;

public class PasswordDriver {

    public static void main(String[] args) {

        String[] passwords = {
            "abc",
            "Abcd1234!",
            "abcdefgh",
            "Abcdefgh",
            "Abcdef12"
        };

        for (String pw : passwords) {

            boolean[] rules = PasswordChecker.checkRules(pw);

            System.out.println("\nPassword: " + pw);

            System.out.println("Length >= 8: " + rules[0]);
            System.out.println("Uppercase: " + rules[1]);
            System.out.println("Digit: " + rules[2]);
            System.out.println("Special Character: " + rules[3]);

            System.out.println("Strength: " + PasswordChecker.strength(pw));
        }
    }
}
