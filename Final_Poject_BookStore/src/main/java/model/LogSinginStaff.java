/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name="LogSinginStaff")
public class LogSinginStaff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logID;
    private String ip;
    private LocalDateTime timein;
    @Nullable
    private LocalDateTime timeout;
    private String latitude;
    private String longitude;

    public LogSinginStaff() {

    }
    public LogSinginStaff(String ip, LocalDateTime timein, String latitude, String longitude) {
        this.logID = logID;
        this.ip = ip;
        this.timein = timein;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Long getLogID() {
        return logID;
    }
    public void setLogID(Long logID) {
        this.logID = logID;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDateTime getTimein() {
        return timein;
    }

    public LocalDateTime getTimeout() {
        return timeout;
    }

    public void setTimein(LocalDateTime timein) {
        this.timein = timein;
    }

    public void setTimeout(LocalDateTime timeout) {
        this.timeout = timeout;
    }
    



}
