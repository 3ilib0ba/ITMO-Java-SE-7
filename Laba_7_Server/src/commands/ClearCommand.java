package commands;

import collectionofflats.MyTreeMap;
import data.workwithrequest.ExecuteRequest;
import typesfiles.Flat;
import static main.Server.databaseManager;

import java.sql.SQLException;
import java.util.TreeMap;

/**
 * Class with 'clear' command. Clear all MAP.
 */
public class ClearCommand {
    public ClearCommand(MyTreeMap map) throws SQLException {
        map.getMyMap().clear();
        databaseManager.loadFullCollection(map);
        ExecuteRequest.answer.append("Now your objects have been deleted...");

        HistoryCommand.addHistory("Clear");
    }
}
