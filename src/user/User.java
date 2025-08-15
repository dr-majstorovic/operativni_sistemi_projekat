package user;

public class User {
    private String username;
    private String password;
    private int level;
    private String group;
    /*
    User accounts can have 3 levels of privileges:
     0 - administrator can run all the commands and has privileges over all files and directories
     1 - user can create files and processes, but cannot delete admin files
     2 - guest can do basic operations only (read, run process)
    */

    public User(String username, String password, int level, String group){
        this.username = username;
        this.password = password;
        this.level = level;
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getLevel() {
        return level;
    }

    public String getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return username + ":" + password + ":" + level + ":" + group;
    }
}
