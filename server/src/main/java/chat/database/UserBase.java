package chat.database;

import chat.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserBase {
    private static List<User> usersBase;

    public static synchronized List<User> getInstance() {
        if (usersBase == null) {
            usersBase = new ArrayList<>();
        }
        return usersBase;
    }
}
