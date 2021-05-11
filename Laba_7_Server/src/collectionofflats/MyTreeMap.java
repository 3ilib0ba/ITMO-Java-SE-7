package collectionofflats;

import typesfiles.Flat;

import java.util.TreeMap;

/**
 * Class that implements a storage with objects
 */
public class MyTreeMap {
    private final TreeMap<Integer, Flat> MyMap;
    public static int ID_MAX = 1;

    public MyTreeMap() {
        MyMap = new TreeMap<>();
    }

    /**
     * function for add Flat to map
     * @param key - key of new object
     * @param flat - new flat
     */
    public void addFlat(Integer key, Flat flat) {
        try {
            MyMap.put(key, flat);
            ID_MAX++;
        } catch (NullPointerException e) {
            System.out.println("Error NullPointerException");
        }
    }

    /**
     * function for getting map
     * @return map with objects
     */
    public TreeMap<Integer, Flat> getMyMap() {
        return MyMap;
    }

    /**
     * function to add a TreeMap(from file) to main MAP
     * @param addMap - merge with this map
     */
    public void addToTree(TreeMap<Integer, Flat> addMap) {
        for (Integer key : addMap.keySet()) {
            addMap.get(key).setId(ID_MAX++);
            addFlat(key, addMap.get(key));
        }
        //System.out.println("добавлена мапа:\n" + addMap.toString());
    }
}