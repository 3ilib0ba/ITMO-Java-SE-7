package commands;

import collectionofflats.MyTreeMap;
import data.workwithrequest.ExecuteRequest;
import typesfiles.Flat;

import java.util.Map;

/**
 * Class with 'count_less_than_number_of_bathrooms' command. Output number of flats with number of bathrooms less than given
 */
public class CountLess {
    public CountLess(MyTreeMap map, int number) {
        int counter = 0;

        for (Map.Entry<Integer, Flat> entry : map.getMyMap().entrySet()) {
            if (entry.getValue().getNumberOfBathrooms() < number)
                counter++;
        }
        ExecuteRequest.answer.append(counter).append(" objects with less number of bathrooms");

        HistoryCommand.addHistory("count_less_than_number_of_bathrooms");
    }
}