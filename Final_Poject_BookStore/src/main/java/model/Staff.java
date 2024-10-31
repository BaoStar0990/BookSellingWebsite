package model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.io.Serializable;
@Entity
@Table(name="Staff")
public class Staff extends User implements Serializable {
    private Double salary;
//    public Staff() {
//        super();
//    }
//    public Staff(int id, String username, String password,String fullName, int age, String numberPhone, String email,Double salary) {
//        super(id,username,password,  fullName,  age,  numberPhone,  email);
//        this.salary = salary;
//    }
    
    public Staff() {
    }

    public Staff(int id, String username, String password, String fullName, int age, String numberPhone, String email, Double salary ) {
        super(id, username, password, fullName, age, numberPhone, email);
        this.salary = salary;
    }

    public Staff(String username, String password, String fullName, int age, String numberPhone, String email, Double salary) {
        super(username, password, fullName, age, numberPhone, email);
        this.salary = salary;
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
}
