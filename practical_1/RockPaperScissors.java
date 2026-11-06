package practical_1;

import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {

    // All possible moves that can be selected in the game
    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    // Returns 1 if player A wins, -1 if player B wins, and 0 for a tie
    static int winner(Move a, Move b) {

        // Check which moves can be defeated by move A
        boolean aWins = switch (a) {

            case ROCK -> b == Move.SCISSORS || b == Move.LIZARD;

            case PAPER -> b == Move.ROCK || b == Move.SPOCK;

            case SCISSORS -> b == Move.PAPER || b == Move.LIZARD;

            case LIZARD -> b == Move.PAPER || b == Move.SPOCK;

            case SPOCK -> b == Move.ROCK || b == Move.SCISSORS;
        };

        // Check for a tie first
        if (a == b) {
            return 0;
        }

        // Return the result based on which move wins
        return aWins ? 1 : -1;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        // Keep track of the player's and computer's scores
        int playerScore = 0;
        int computerScore = 0;

        // Play a total of 5 rounds
        for (int i = 1; i <= 5; i++) {

            System.out.println("\n----- Round " + i + " -----");

            // Computer randomly selects one of the five moves
            Move computerMove = Move.values()[random.nextInt(Move.values().length)];

            // Read the player's move
            System.out.println("Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK):");

            Move playerMove;

            // Keep asking until the player enters a valid move
            while (true) {
                try {
                    playerMove = Move.valueOf(sc.nextLine().trim().toUpperCase());
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid move! Please try again:");
                }
            }

            // Display both the player's and computer's moves
            System.out.println("Your move: " + playerMove);
            System.out.println("Computer's move: " + computerMove);

            // Compare both moves and decide the round winner
            int result = winner(playerMove, computerMove);

            if (result == 1) {
                System.out.println("You win this round!");
                playerScore++;
            }
            else if (result == -1) {
                System.out.println("Computer wins this round!");
                computerScore++;
            }
            else {
                System.out.println("It's a tie!");
            }

            // Display the current score after every round
            System.out.println("Score: You " + playerScore + " - " + computerScore + " Computer");
        }

        // Display the final result after all five rounds
        System.out.println("\n===== FINAL RESULT =====");

        if (playerScore > computerScore) {
            System.out.println("You win " + playerScore + "–" + computerScore);
        }
        else if (computerScore > playerScore) {
            System.out.println("Computer wins " + computerScore + "–" + playerScore);
        }
        else {
            System.out.println("The game is a tie! " + playerScore + "–" + computerScore);
        }
        sc.close();
    }
}
