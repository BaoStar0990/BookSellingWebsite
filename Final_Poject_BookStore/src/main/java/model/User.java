package model;

import jakarta.persistence.*;

import java.io.Serializable;

@MappedSuperclass // Mỗi class khi map xuống nó sẽ khong có class cha mà chỉ có class con https://techmaster.vn/posts/36499/hibernate-inheritance-mapping
public abstract class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh
    protected int id;
    protected String username;
    protected String password;
    protected String fullName;
    protected int age;
    protected String numberPhone;
    protected String email;
    public User() {

    }
    public User(int id, String username, String password, String fullName, int age, String numberPhone, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
    }
    public User(String username, String password, String fullName, int age, String numberPhone, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getNumberPhone() {
        return numberPhone;
    }
    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
