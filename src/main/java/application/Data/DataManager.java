package application.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class DataManager {

    private List<String> arrayList;
    private Set<String> hashSet;
    private Map<String, String> hashMap;

    public DataManager() {
        arrayList = new ArrayList<>();
        hashSet = new HashSet<>();
        hashMap = new HashMap<>();
    }

    // List operations
    public void addToList(String item) {
        arrayList.add(item);
    }

    public void updateList(int index, String item) {
        if (index >= 0 && index < arrayList.size()) {
            arrayList.set(index, item);
        }
    }

    public void deleteFromList(int index) {
        if (index >= 0 && index < arrayList.size()) {
            arrayList.remove(index);
        }
    }

    public void clearList() {
        arrayList.clear();
    }

    public boolean findInList(String item) {
        return arrayList.contains(item);
    }

    public List<String> getArrayList() {
        return arrayList;
    }

    // Set operations
    public void addToSet(String item) {
        hashSet.add(item);
    }

    public void updateSet(String oldItem, String newItem) {
        if (hashSet.contains(oldItem)) {
            hashSet.remove(oldItem);
            hashSet.add(newItem);
        }
    }

    public void deleteFromSet(String item) {
        hashSet.remove(item);
    }

    public void clearSet() {
        hashSet.clear();
    }

    public boolean findInSet(String item) {
        return hashSet.contains(item);
    }

    public Set<String> getHashSet() {
        return hashSet;
    }

    // Map operations
    public void addToMap(String key, String value) {
        hashMap.put(key, value);
    }

    public void updateMap(String key, String value) {
        if (hashMap.containsKey(key)) {
            hashMap.put(key, value);
        }
    }

    public void deleteFromMap(String key) {
        hashMap.remove(key);
    }

    public void clearMap() {
        hashMap.clear();
    }

    public boolean findInMap(String key) {
        return hashMap.containsKey(key);
    }

    public Map<String, String> getHashMap() {
        return hashMap;
    }
}
