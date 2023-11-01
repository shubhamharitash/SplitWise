package repository;

import dto.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
   public static Map<String, User> stringUserMap;
   public static List<User> userList;

    public  UserRepository() {
        stringUserMap = new HashMap<>();
        userList = new ArrayList<>();
    }

    public static Map<String, User> getStringUserMap() {
        return stringUserMap;
    }

    public static List<User> getUserList() {
        return userList;
    }
}
