package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "Admin")
public class Admin extends User implements Serializable {
    public Admin() {
        super();
    }
    public Admin(String username, String password) {
        super(username, password);
    }
}
