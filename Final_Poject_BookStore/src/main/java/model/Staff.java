package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="Staff")
public class Staff extends User implements Serializable {
    private Double salary;
    private boolean status;
    
    public Staff() {
    }

    public Staff(int id, String username, String password, String fullName, int age, String numberPhone, String email, Double salary, LocalDate dob ) {
        super(id, username, password, fullName, age, numberPhone, email, dob);
        this.salary = salary;
    }

    public Staff(String username, String password, String fullName, int age, String numberPhone, String email, Double salary, LocalDate dob) {
        super(username, password, fullName, age, numberPhone, email, dob);
        this.salary = salary;
    }

    public Staff(String username, String password, String fullName, int age, String numberPhone, String email, Double salary) {
        super(username, password, fullName, age, numberPhone, email);
        this.salary = salary;
    }
    public Staff(int id, String username, String password, String fullName, int age, String numberPhone, String email, Double salary, LocalDate dob,boolean status) {
        super(id, username, password, fullName, age, numberPhone, email, dob);
        this.salary = salary;
        this.status = status;
    }

    public Staff(Double salary, String username, String password) {
        super(username, password);
        this.salary = salary;

    }
    
    public Double getSalary() {
        return salary;
    }
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
