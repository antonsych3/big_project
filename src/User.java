import java.io.Serializable;

public class User implements Serializable {
    private Long id;
    private String login, passwod, role;

    public Long getId() {
        return id;
    }

    public User() {
    }

    public User(String login, String passwod) {
        this.login = login;
        this.passwod = passwod;
    }

    public User(Long id, String login, String passwod, String role) {
        this.id = id;
        this.login = login;
        this.passwod = passwod;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswod() {
        return passwod;
    }

    public void setPasswod(String passwod) {
        this.passwod = passwod;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", passwod='" + passwod + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
