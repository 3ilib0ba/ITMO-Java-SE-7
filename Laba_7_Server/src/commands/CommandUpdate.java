package commands;

import commands.exceptions.InvalidArgExcaption;
import collectionofflats.MyTreeMap;
import data.workwithrequest.ExecuteRequest;
import typesfiles.Flat;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Class with 'update' command. Search flat with given id and replace it to new flat.
 */
public class CommandUpdate {
    public CommandUpdate(int idUpd, MyTreeMap map, Scanner scanner){
        try {
            Integer keyOfUpd = checkId(idUpd, map.getMyMap());
            System.out.println(keyOfUpd);
            new CommandInsert(keyOfUpd, map, true, scanner);
        } catch (InvalidArgExcaption e) {
            ExecuteRequest.answer.append(e.getMessage());
        }
    }

    public CommandUpdate(int idUpd, MyTreeMap map, Flat addingFlat) {
        try {
            Integer keyOfUpd = checkId(idUpd, map.getMyMap());
            System.out.println(keyOfUpd);
            new CommandInsert(keyOfUpd, map, true, addingFlat);
        } catch (InvalidArgExcaption e) {
            ExecuteRequest.answer.append(e.getMessage());
        }
    }

    /**
     * function for checking given id to existing in the MAP.
     * @param searchId - given id
     * @param map - given MAP to search
     * @return id of object if it in the collection
     * @throws InvalidArgExcaption if id hadn't found
     */
    private Integer checkId(int searchId, TreeMap<Integer, Flat> map)
            throws InvalidArgExcaption {
        for (Map.Entry<Integer, Flat> entry : map.entrySet()) {
            int id = entry.getValue().getId();
            if (id == searchId)
                return entry.getKey();
        }
        throw new InvalidArgExcaption("the given id was not found in the collection, there is nothing to update");
    }
}
