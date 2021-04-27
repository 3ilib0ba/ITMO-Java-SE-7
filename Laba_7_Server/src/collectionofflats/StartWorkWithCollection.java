package collectionofflats;

import java.util.Date;

/**
 * Main class -- start of working with collection
 * @author Evgeniy Ivanov, P3113
 */
public abstract class StartWorkWithCollection {
    public static Date dateOfInit;

    public static MyTreeMap initialization() {
        MyTreeMap myMap = new MyTreeMap(); // Создание TreeMap в которой будут обработаны элементы
        dateOfInit = new Date();
        return myMap;
        //myMap.addToTree(ReadCommand.readTheCollection("src/resourse/saves/FLAT.json"));
    }
}
