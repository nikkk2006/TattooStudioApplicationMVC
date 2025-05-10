package org.example.model;


public class AppointmentModel {
    private int id;
    private int clientId;
    private int masterId;
    private int scheduleId;
    private String status;

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public int getMasterId() { return masterId; }
    public void setMasterId(int masterId) { this.masterId = masterId; }
    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}