import javafx.util.Pair;

import java.util.Scanner;

import static com.sun.org.apache.xml.internal.security.utils.resolver.ResourceResolver.register;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static boolean isFinished = false;

    public static void main(String[] args) {
        while (!isFinished) {
            Pair<String, String> commandParts = getCommandParts();
            String commandName = commandParts.getKey();
            String commandData = commandParts.getValue();

            switch (commandName) {
                case "register":
                    Utility.register(commandData);
                    break;
                case "addProject":
                    Utility.addProject(commandData);
                    break;
                case "bid":
                    Utility.bid(commandData);
                    break;
                case "auction":
                    Utility.auction(commandData);
                    isFinished = true;
                    break;
            }
        }
    }
    private static Pair<String, String> getCommandParts() {
        String command = scanner.nextLine();
        int spaceIndex = command.indexOf(" ");
        return new Pair<>(command.substring(0, spaceIndex), command.substring(spaceIndex));
    }
}
