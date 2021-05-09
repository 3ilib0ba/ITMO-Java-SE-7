package commands;

import collectionofflats.MyTreeMap;
import commands.exceptions.ExitException;
import commands.exceptions.IllegalCommandException;
import commands.exceptions.InvalidArgExcaption;
import commands.exceptions.RecursionException;
import data.netdata.Request;

import java.lang.ref.ReferenceQueue;
import java.util.Scanner;

/**
 * Class with execute all program and all command from console.
 */
public class Execute {
    public Execute(boolean isFromFile, Scanner scanner) {
        execute(isFromFile, scanner);
    }

    /**
     * function for defining a command and its execution
     *
     * @param isFromFile - var to check about execute_script command
     * @param   - MAP with objects
     * @param SCANNER    - mod of program
     * @throws ExitException if was invoke exit command
     */
    public static Request execute(boolean isFromFile, Scanner SCANNER)
            throws ExitException {
        String command, execCom;
        String[] commands;

        do {
            System.out.print("Enter the command: ");

            if (!SCANNER.hasNextLine()) {
                System.out.println("ending");
                throw new RuntimeException("waiting a command...");
            }
            command = SCANNER.nextLine(); // ожидание новой команды
            if (isFromFile)
                System.out.println(command);

            commands = command.toLowerCase().split(" ");
            execCom = commands[0];

            switch (execCom) {

                case "help":
                    if (commands.length == 1) {
                        return new Request("help", "");
                    } else {
                        throw new IllegalCommandException("Unknown help_<...> command");
                    }

                case "info":
                    if (commands.length == 1) {
                        return new Request("info", "");
                    } else {
                        throw new IllegalCommandException("Unknown info_<...> command");
                    }

                case "show":
                    if (commands.length == 1) {
                        return new Request("show", "");
                    } else {
                        throw new IllegalCommandException("Unknown show_<...> command");
                    }

                case "insert":
                    if (commands.length == 1) {
                        throw new RuntimeException("wait KEY");
                    } else if (commands.length >= 3) {
                        throw new IllegalCommandException("Wrong format. Need -> 'insert <key>'");
                    } else {
                        try {
                            Integer newKey = Integer.parseInt(commands[1]);
                            return new Request("insert", newKey.toString(),
                                    new CommandInsert().execute(newKey, new Scanner(System.in), false));
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("type key - Integer");
                        }
                    }


                case "update":
                    if (commands.length == 1) {
                        System.out.println("wait id");
                    } else if (commands.length >= 3) {
                        throw new IllegalCommandException("Wrong format. Need -> 'update <id>'");
                    } else {
                        try {
                            Integer idUpd = Integer.parseInt(commands[1]);
                            return new Request("update", idUpd.toString(),
                                    new CommandInsert().execute(idUpd, new Scanner(System.in), true));
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("type of id - int");
                        }
                    }
                    break;

                case "remove_key":
                    if (commands.length != 2) {
                        throw new IllegalCommandException("Wrong format. Need -> 'remove_key <KEY>'");
                    } else {
                        try {
                            Integer newKey = Integer.parseInt(commands[1]);
                            return new Request("remove_key", newKey.toString());
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("key of object - Integer");
                        }
                    }

                case "remove_lower":
                    if (commands.length != 2) {
                        throw new IllegalCommandException("Wrong format 'remove lower <numeric>'");
                    } else {
                        try {
                            Long comp = Long.parseLong(commands[1]);
                            return new Request("remove_lower", comp.toString());
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("min square, type - long");
                        }
                    }


                case "clear":
                    if (commands.length == 1) {
                        return new Request("clear", "");
                    } else {
                        throw new IllegalCommandException("Unknown clear_<...> command");
                    }
                /*
                case "save":
                    if (commands.length == 1) {
                        SaveCommand.startSaveFile(map.getMyMap(), SCANNER);
                    } else {
                        throw new IllegalCommandException("Unknown save_<...> command");
                    }
                    break;
                */

                case "execute_script":
                    if (commands.length == 2) {
                        try {
                            return new Request(commands[0], commands[1]);
                        } catch (RecursionException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format");
                    }


                case "history":
                    if (commands.length == 1) {
                        return new Request(command, "");
                    } else {
                        throw new IllegalCommandException("Unknown history_<...> command");
                    }

                case "replace_if_lowe":
                    if (commands.length == 3) {
                        try {
                            Integer newKey = Integer.parseInt(commands[1]);
                            Long newArea = Long.parseLong(commands[2]);
                            return new Request(command, newKey.toString() + " " + newArea);

                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("\tkey type - Integer,\n" + "\tarea type - long");
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format. Need -> 'replace_if_lowe <key> <long>'");
                    }

                case "min_by_id":
                    return new Request(command, "");

                case "count_less_than_number_of_bathrooms":
                    if (commands.length == 2) {
                        try {
                            Integer numBath = Integer.parseInt(commands[1]);
                            return new Request(command, numBath.toString());
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("number of bathrooms -> int");
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format. Need -> 'count_less_than_number_of_bathrooms <NUMBER>'");
                    }


                case "load":
                    if (commands.length == 2) {
                        String path = commands[1];
                        return new Request("load", path);
                    } else {
                        throw new IllegalCommandException("Wrong format");
                    }

                case "filter_starts_by_name":
                    return new Request(command, commands[1]);

                case "exit":
                    throw new ExitException();

                default:
                    throw new IllegalCommandException("Command not found");
            }
        }
        while (true);
    }
}
