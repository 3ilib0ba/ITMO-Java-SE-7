package commands;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import data.workwithrequest.ExecuteRequest;
import typesfiles.Flat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.NoSuchElementException;
import java.util.TreeMap;

/**
 * Class for reading *.json files and taking flats from it.
 */
public class ReadCommand {
    /**
     * method for getting a map ith flats
     * @param name name of file
     * @return map to merge with main MAP
     */
    public static TreeMap<Integer, Flat> readTheCollection(String name) {
        try {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(name));
            System.out.println(name);

            try (InputStreamReader isr = reader) {
                int ch;
                StringBuilder allText = new StringBuilder();
                while ((ch = isr.read()) != -1)
                    allText.append((char) ch);

                TreeMap<Integer, Flat> loadingCol;
                Type collectionType = new TypeToken<TreeMap<Integer, Flat>>() {
                }.getType();
                loadingCol = new Gson().fromJson(allText.toString(), collectionType);
                ExecuteRequest.answer.append("Collection loaded successfully!");
                return loadingCol;
            } catch (NoSuchElementException e) {
                throw new RuntimeException("the loaded file is empty!");
            } catch (JsonParseException | NullPointerException exception) {
                throw new RuntimeException("Wrong format in the file!");
            } catch (Exception e) {
                throw new RuntimeException("Error");
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        //return new TreeMap<>();
    }

    /*public static void allchecking(MyTreeMap map) {

    } */
}
