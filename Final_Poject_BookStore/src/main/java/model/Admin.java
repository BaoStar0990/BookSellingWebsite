package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.lang.reflect.Type;

@Entity
@Table(name = "Admin")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private boolean status;

    public Admin(String username, String password, boolean status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public Admin() {

    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
