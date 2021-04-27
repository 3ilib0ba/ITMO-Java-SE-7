package commands;

import commands.exceptions.RecursionException;
import collectionofflats.MyTreeMap;

import java.io.*;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * Class with 'execute_script' command. Execute script(file with command) with given name.
 */
public class ExecuteScript {
    public static TreeSet<File> openFiles = new TreeSet<>();

    public ExecuteScript(MyTreeMap map, String path)
            throws RecursionException {
        try {
            File thisScript = new File(path);
            if (openFiles.contains(thisScript)) {
                throw new RecursionException("It's recursion...Ending this script...");
            } else {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path));
                openFiles.add(thisScript);

                System.out.println(path);

                int nowChar;
                StringBuilder allText = new StringBuilder();
                while ((nowChar = inputStreamReader.read()) != -1)
                    allText.append((char) nowChar);

                Scanner scanOfExecuteFile = new Scanner(allText.toString());


                HistoryCommand.addHistory("execute_script START");
                new Execute(true, map, scanOfExecuteFile);
                HistoryCommand.addHistory("execute_script END");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found...");
        } catch (IOException e) {
            throw new RuntimeException("IOException");
        }
    }
}
