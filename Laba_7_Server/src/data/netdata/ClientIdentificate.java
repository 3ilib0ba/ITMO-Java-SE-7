package data.netdata;

public class ClientIdentificate {
    private String login;
    private String password;
    public Integer idOfClient;

    public ClientIdentificate(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setIdOfClient(Integer id) {
        idOfClient = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
