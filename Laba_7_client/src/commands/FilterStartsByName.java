package commands;

import collectionofflats.MyTreeMap;
import commands.exceptions.IllegalCommandException;

/**
 * Class with 'filter_starts_by_name' command. Show all flats with name starting with name
 */
public class FilterStartsByName {
    public FilterStartsByName(MyTreeMap map, String[] command) {
        if (command.length != 2) {
            throw new IllegalCommandException("Wrong format. Need -> 'filter_starts_by_name <name/of/flat>'. Replace 'spacebutton' to '/'");
        } else {
            String name = command[1];
            name = name.replace("/", " ");
            outputFlatsWithName(map, name);
        }

        HistoryCommand.addHistory("FilterStartsByName command...");
    }

    /**
     * function to display all flats with a given name at the beginning
     * @param map - MAP with flats
     * @param name - piece of name
     */
    private void outputFlatsWithName(MyTreeMap map, String name) {
        for (Integer keyNow : map.getMyMap().keySet()) {
            if (map.getMyMap().get(keyNow).getName().startsWith(name))
                System.out.println(map.getMyMap().get(keyNow));
        }
    }
}
