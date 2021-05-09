package data.netdata;

import java.io.Serializable;

public class ClientIdentificate implements Serializable {
    private String login;
    private String password;

    public ClientIdentificate(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
