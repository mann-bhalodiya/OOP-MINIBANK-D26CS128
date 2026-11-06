
package practical_3;

public class CardDriver {

    public static void main(String[] args) {

        // Array to store the cards one by one
        Card[] cards = new Card[5];

        // Cards that we want to add to the array
        Card[] newCards = {
                new Card("Ace", "Spades"),
                new Card("King", "Hearts"),
                new Card("Queen", "Diamonds"),
                new Card("Ace", "Spades"),
                new Card("Jack", "Clubs")
        };

        // Keeps track of how many cards have been added
        int count = 0;

        // Add cards one at a time and check for duplicates
        for (Card current : newCards) {

            boolean duplicate = false;

            // Compare the current card with all previously added cards
            for (int i = 0; i < count; i++) {

                if (current.equals(cards[i])) {
                    duplicate = true;
                    break;
                }
            }

            // If a duplicate is found, display it and stop checking
            if (duplicate) {
                System.out.println("Duplicate found: " + current);
                break;
            }

            // Add the card only if it is not already present
            cards[count] = current;
            count++;
        }
    }
}
