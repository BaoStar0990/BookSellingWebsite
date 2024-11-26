package model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

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
    protected LocalDate birthday;
    protected String salt;
    
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
    public User(int id, String username, String password, String fullName, int age, String numberPhone, String email, LocalDate birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthday = birthday;
    }
    public User(String username, String password, String fullName, int age, String numberPhone, String email, LocalDate birthday) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthday = birthday;
    }
    public User(String username, String password, String fullName, int age, String numberPhone, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
    }
    public User(String username, String password, String fullName,
            String numberPhone, String email, LocalDate dob) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthday = dob;
    }
    public User(String username, String password, String salt, String fullName,
            String numberPhone, String email, LocalDate dob) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.fullName = fullName;
        this.age = age;
        this.numberPhone = numberPhone;
        this.email = email;
        this.birthday = dob;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public String getSalt() {
        return salt;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
    @PrePersist
    @PreUpdate
    public void calculateAge() {
        if (birthday != null) {
            this.age = Period.between(birthday, LocalDate.now()).getYears();
        }
    }
}
