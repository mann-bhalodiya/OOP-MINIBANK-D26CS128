
package practical_1;

import java.util.Scanner;

public class VendingMachine {

    enum Coins {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            final int itemPrice = 15;
            int receivedAmount = 0;
            
            while (receivedAmount < itemPrice) {
                
                System.out.println("Enter coin (ONE, TWO, FIVE, TEN):");
                
                String input = sc.nextLine().toUpperCase();
                
                try {
                    Coins coin = Coins.valueOf(input);
                    
                    receivedAmount = switch (coin) {
                        case ONE -> receivedAmount + 1;
                        case TWO -> receivedAmount + 2;
                        case FIVE -> receivedAmount + 5;
                        case TEN -> receivedAmount + 10;
                    };
                    
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid coin!");
                }
                
                System.out.println("Item Price: " + itemPrice + " cents");
                System.out.println("Received Amount: " + receivedAmount + " cents");
            }
            
            System.out.println("Item dispensed!");
            System.out.println("Change: " + (receivedAmount - itemPrice));
        }
    }
}
