package commands;

import collectionofflats.MyTreeMap;
import commands.exceptions.ExitException;
import commands.exceptions.IllegalCommandException;
import commands.exceptions.InvalidArgExcaption;
import commands.exceptions.RecursionException;
import data.dao.exceptions.InvalidPasswordException;
import data.netdata.ClientIdentificate;
import typesfiles.Flat;

import static main.Server.databaseManager;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Class with execute all program and all command from console.
 */
public class Execute {
    public Execute(boolean isFromFile, MyTreeMap map, Scanner scanner, ClientIdentificate client) {
        execute(isFromFile, map, scanner, null, client);
    }

    /**
     * function for defining a command and its execution
     *
     * @param isFromFile - var to check about execute_script command
     * @param map        - MAP with objects
     * @param SCANNER    - mod of program
     * @throws ExitException if was invoke exit command
     */
    public static void execute(boolean isFromFile, MyTreeMap map, Scanner SCANNER,
                               Flat addingFlat, ClientIdentificate client)
            throws ExitException {

        // Trying to add a new client or find his id
        try {
            client.setIdOfClient(databaseManager.findUser(client.getLogin(), client.getPassword()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidPasswordException e) {
            throw e;
        }
        if (client.idOfClient == null) {
            try {
                databaseManager.insertNewClient(client);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        String command, execCom;
        String[] commands;

        do {
            System.out.print("Enter the command: ");

            if (!SCANNER.hasNextLine()) {
                System.out.println("ending");
                return;
            }
            command = SCANNER.nextLine(); // ожидание новой команды
            if (isFromFile)
                System.out.println(command);

            commands = command.toLowerCase().split(" ");
            execCom = commands[0];

            switch (execCom) {

                case "help":
                    if (commands.length == 1) {
                        CommandHelp.helpCommand();
                    } else {
                        throw new IllegalCommandException("Unknown help_<...> command");
                    }
                    break;

                case "info":
                    if (commands.length == 1) {
                        CommandInfo.infoCommand(map);
                    } else {
                        throw new IllegalCommandException("Unknown info_<...> command");
                    }
                    break;

                case "show":
                    if (commands.length == 1) {
                        CommandShow.showCommand(map);
                    } else {
                        throw new IllegalCommandException("Unknown show_<...> command");
                    }
                    break;

                case "insert":
                    if (commands.length == 1) {
                        System.out.println("wait KEY");
                    } else if (commands.length >= 3) {
                        throw new IllegalCommandException("Wrong format. Need -> 'insert <key>'");
                    } else {
                        try {
                            databaseManager.insertNewFlat(addingFlat, client);

                            Integer newKey = Integer.parseInt(commands[1]);
                            if (isFromFile) {
                                new CommandInsert(newKey, map, false, SCANNER);
                            } else {
                                System.out.println("not from file");
                                new CommandInsert(newKey, map, false, addingFlat);
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("type key - Integer");
                        }
                    }
                    break;

                case "update":
                    if (commands.length == 1) {
                        System.out.println("wait id");
                    } else if (commands.length >= 3) {
                        throw new IllegalCommandException("Wrong format. Need -> 'update <id>'");
                    } else {
                        try {
                            int idUpd = Integer.parseInt(commands[1]);
                            try {
                                databaseManager.updateFlatById(addingFlat, client, idUpd);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            if (isFromFile) {
                                new CommandUpdate(idUpd, map, SCANNER);
                            } else {
                                new CommandUpdate(idUpd, map, addingFlat);
                            }
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
                            new RemoveByKey(map.getMyMap(), newKey);
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("key of object - Integer");
                        }
                    }
                    break;
                case "remove_lower":
                    if (commands.length != 2) {
                        throw new IllegalCommandException("Wrong format 'remove lower <numeric>'");
                    } else {
                        try {
                            long comp = Long.parseLong(commands[1]);
                            new RemoveByLower(map.getMyMap(), comp);
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("min square, type - long");
                        }
                    }
                    break;

                case "clear":
                    if (commands.length == 1) {
                        try {
                            databaseManager.deleteByClient(map, client);
                            new ClearCommand(map);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } else {
                        throw new IllegalCommandException("Unknown clear_<...> command");
                    }
                    break;

                case "save":
                    if (commands.length == 1) {
                        SaveCommand.startSaveFile(map.getMyMap(), SCANNER);
                    } else {
                        throw new IllegalCommandException("Unknown save_<...> command");
                    }
                    break;

                case "execute_script":
                    if (commands.length == 2) {
                        try {
                            new ExecuteScript(map, commands[1], client);
                        } catch (RecursionException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format");
                    }
                    break;

                case "history":
                    if (commands.length == 1) {
                        HistoryCommand.printHistory();
                    } else {
                        throw new IllegalCommandException("Unknown history_<...> command");
                    }
                    break;

                case "replace_if_lowe":
                    if (commands.length == 3) {
                        try {
                            Integer newKey = Integer.parseInt(commands[1]);
                            long newArea = Long.parseLong(commands[2]);
                            new ReplaceByKeyLowe(map.getMyMap(), newKey, newArea);
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("\tkey type - Integer,\n" + "\tarea type - long");
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format. Need -> 'replace_if_lowe <key> <long>'");
                    }
                    break;

                case "min_by_id":
                    new MinById(map.getMyMap());
                    break;

                case "count_less_than_number_of_bathrooms":
                    if (commands.length == 2) {
                        try {
                            int numBath = Integer.parseInt(commands[1]);
                            new CountLess(map, numBath);
                        } catch (NumberFormatException e) {
                            throw new InvalidArgExcaption("number of bathrooms -> int");
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format. Need -> 'count_less_than_number_of_bathrooms <NUMBER>'");
                    }
                    break;
                case "load":
                    if (commands.length == 2) {
                        String path = commands[1];
                        System.out.println(path);
                        try {
                            map.addToTree(ReadCommand.readTheCollection(path));
                        } catch (Exception e) {
                            throw new InvalidArgExcaption("Exit from LOADcommand with some exception");
                        }
                    } else {
                        throw new IllegalCommandException("Wrong format.");
                    }
                    break;
                case "filter_starts_by_name":
                    new FilterStartsByName(map, commands);
                    break;

                case "exit":
                    throw new ExitException();
                default:
                    throw new IllegalCommandException("Command not found");
            }
        }
        while (true);
    }
}
