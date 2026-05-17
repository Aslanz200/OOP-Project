package kz.kbtu.services;

import kz.kbtu.exceptions.AuthException;
import kz.kbtu.models.users.User;
import kz.kbtu.patterns.Database;
import kz.kbtu.patterns.EventManager;

public class AuthService {

    public User login(String username, String password) throws AuthException {
        User user = Database.getInstance().findUser(username)
                .orElseThrow(() -> new AuthException("Unknown user: " + username));
        if (!user.login(password)) {
            throw new AuthException("Wrong password for " + username);
        }
        EventManager.getInstance().notify("User " + user.fullName() + " logged in");
        return user;
    }

    public void logout(User user) {
        if (user == null) return;
        user.logout();
        EventManager.getInstance().notify("User " + user.fullName() + " logged out");
    }
}
