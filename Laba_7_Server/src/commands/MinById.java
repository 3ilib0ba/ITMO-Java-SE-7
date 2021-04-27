package commands;

import commands.exceptions.NullMapException;
import data.workwithrequest.ExecuteRequest;
import typesfiles.Flat;

import java.util.Map;
import java.util.TreeMap;

/**
 * Class with 'min_by_id' command. Search and show flat with min id.
 */
public class MinById {
    public MinById(TreeMap<Integer, Flat> map) {
        try {
            Integer keyOfMinFlatById = searchMin(map);
            ExecuteRequest.answer.append(map.get(keyOfMinFlatById));
        } catch (NullMapException e) {
            ExecuteRequest.answer.append(e.getMessage());
        }

        HistoryCommand.addHistory("min by id");
    }

    /**
     * method for search key of flat(in the MAP) with min id
     * @param map - Where flat will be searching
     * @return key of object with min value
     * @throws NullMapException if collection is empty
     */
    private Integer searchMin(TreeMap<Integer, Flat> map)
            throws NullMapException{
        Integer keyNow = null;
        Integer keyOfMin = null;
        int idOfMin = 0;
        for (Map.Entry<Integer, Flat> entry : map.entrySet()) {
            if (keyNow == null) {
                keyNow = entry.getKey();
                idOfMin = entry.getValue().getId();
                keyOfMin = keyNow;
            } else {
                if (idOfMin > entry.getValue().getId()) {
                    keyNow = entry.getKey();
                    idOfMin = entry.getValue().getId();
                    keyOfMin = keyNow;
                }
            }
        }
        if (keyOfMin == null) {
            throw new NullMapException("Your collection is empty");
        }
        return keyOfMin;
    }
}
