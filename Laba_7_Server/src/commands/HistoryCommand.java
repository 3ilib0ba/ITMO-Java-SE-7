package commands;

import data.workwithrequest.ExecuteRequest;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class with 'history' command. Show the last 7 commands.
 */
public abstract class HistoryCommand {
    public static final int HISTORYSIZE = 7;

    public static LinkedList<String> historyList = new LinkedList<>();

    /**
     * function for add to list with last commands last command
     * @param nameOfCommand - name of command
     */
    public static void addHistory(String nameOfCommand){
        if (historyList.size() < HISTORYSIZE) {
            historyList.add(nameOfCommand);
        } else {
            historyList.removeFirst();
            historyList.addLast(nameOfCommand);
        }
    }

    /**
     * function for print last 7 commands.
     */
    public static void printHistory() { // Метод для выполнения команды history
        addHistory("history");
        Iterator<String> iterator = historyList.iterator();
        int counter = 1;

        while (iterator.hasNext()) {
            ExecuteRequest.answer.append(counter++).append(") ").append(iterator.next()).append("\n");
        }
    }
}
