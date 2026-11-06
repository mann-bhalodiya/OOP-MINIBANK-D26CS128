
package practical_3;

import java.util.Objects;

public class Card {

    // Private variables to store the card's rank and suit
    private String rank;
    private String suit;

    // Constructor to initialize the card details
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // Return the card details in a readable format
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    // Check whether two cards have the same rank and suit
    @Override
    public boolean equals(Object obj) {

        // Both references point to the same object
        if (this == obj) {
            return true;
        }

        // Check for null or objects belonging to another class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Convert the Object reference into a Card reference
        Card c = (Card) obj;

        // Two cards are equal only if both rank and suit match
        return Objects.equals(this.rank, c.rank)
                && Objects.equals(this.suit, c.suit);
    }

    // Generate a hash code using rank and suit
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
