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

    public User(String name, Map<String, Expense> lendToMap, Map<String, Expense> owesToMap) {
        this.name = name;
        this.lendToMap = lendToMap;
        this.owesToMap = owesToMap;
    }

    public Map<String, Expense> getOwesToMap() {
        return owesToMap;
    }

    public void setOwesToMap(Map<String, Expense> owesToMap) {
        this.owesToMap = owesToMap;
    }

    Map<String,Expense> owesToMap;
}
