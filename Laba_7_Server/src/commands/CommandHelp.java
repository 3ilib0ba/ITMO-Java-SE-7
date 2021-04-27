package commands;

import data.workwithrequest.ExecuteRequest;

/**
 * Class with 'help' command. Output help about commands.
 */
public class CommandHelp {
    public static void helpCommand() {
        ExecuteRequest.answer.append("list of commands:" +
                "\n\thelp: show all commands" +
                "\n\tinfo: show information of TreeMap" +
                "\n\tshow: show all collection" +
                "\n\tinsert <KEY>: add new object to collection" +
                "\n\tupdate <ID>: update object with id" +
                "\n\tremove_key <key>: delete object with this key" +
                "\n\tclear: clear collection" +
                "\n\tsave: saving collection in file" +
                "\n\texecute_script <FileName>: executing file" +
                "\n\tremove_lower <AREA>: delete object if it area less than AREA" +
                "\n\thistory: show last 7 commands" +
                "\n\treplace_if_lowe <KEY, AREA>: replace obj if it area less than AREA with this KEY" +
                "\n\tmin_by_id: show object with min id" +
                "\n\tcount_less_than_number_of_bathrooms <NUMBER>: show count of objects with number of bathrooms less than NUMBER" +
                "\n\tfilter_starts_by_name <STARTofNAME>: show all objects with name starting like as STARTofNAME" +

                "\n\texit: exit from program" +
                "\n");

        HistoryCommand.addHistory("help");
    }
}
