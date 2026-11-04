
package practical_1;

import java.util.Scanner;

record Vehicle(String number, String type) {}

public class TollBooth {

    static Scanner sc = new Scanner(System.in);

    enum Numbers {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

        final int value;

        Numbers(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        int total = 0;

        while (true) {

            String input = sc.next().toLowerCase();

            if (input.equals("done")) {
                break;
            }

            Numbers no = Numbers.valueOf(input.toUpperCase());

            String type = sc.next().toLowerCase();

            Vehicle v = new Vehicle(no.name(), type);

            int toll = switch (v.type()) {

                case "bike" -> {
                    bikeCount += no.value;
                    yield 20 * no.value;
                }

                case "car" -> {
                    carCount += no.value;
                    yield 50 * no.value;
                }

                case "truck" -> {
                    truckCount += no.value;
                    yield 150 * no.value;
                }

                default -> {
                    System.out.println("Invalid vehicle type!");
                    yield 0;
                }
            };

            total += toll;
        }

        System.out.println("Total toll: " + total);

        String frequent = "bike";
        int max = bikeCount;

        if (carCount > max) {
            max = carCount;
            frequent = "car";
        }
        if (truckCount > max) {
            frequent = "truck";
        }

        System.out.println("Most frequent: " + frequent);

        sc.close();
    }
}
