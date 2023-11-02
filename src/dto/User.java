package dto;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class User {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Expense> getLendToMap() {
        return lendToMap;
    }

    Map<String,Expense> lendToMap;

    public User(String name, Map<String, Expense> lendToMap) {
        this.name = name;
        this.lendToMap = lendToMap;
    }
}
