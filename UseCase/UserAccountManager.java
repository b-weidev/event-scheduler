package UseCase;

import java.util.List;

public interface UserAccountManager {

    public void createUser(String username, String password);

    public boolean checkUserExistence(String username);

    public List<String> getUsers();

}
