package commands;

import typesfiles.Flat;

import java.util.*;

/**
 * it's a 'remove_lower' command. Removing all flats from MAP with area less than given
 */
public class RemoveByLower {
    public RemoveByLower(Map<Integer, Flat> map, long areaAtr) {
        List<Integer> keysRemoved = new LinkedList<>();

        map.forEach((key, value) -> {
            long b = value.getArea() - areaAtr;
            if (b < 0) {
                keysRemoved.add(key);
            }
        });

        Iterator<Integer> iterator = keysRemoved.iterator();
        while (iterator.hasNext()) {
            map.remove(iterator.next());
        }

        HistoryCommand.addHistory("remove lower <...>");
    }
}
