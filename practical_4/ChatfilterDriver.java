package practical_4;

import java.util.Scanner;

public class ChatfilterDriver {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] logs = {
            "10:05:alice:Hello there",
            "10:06:bob:How are you?",
            "10:07:riya:Hello everyone",
            "10:08:john:Good morning",
            "10:09:aman"
        };

        System.out.print("Enter keyword: ");
        String keyword = sc.nextLine();

        String report = ChatFilter.filterLogs(logs, keyword);

        System.out.println(report);

        sc.close();
    }
}
