package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleModel {
    private int id;
    private int masterId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    // Конструкторы
    public ScheduleModel() {}

    public ScheduleModel(int userId, LocalDate date, LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        this.masterId = userId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Геттеры и сеттеры
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMasterId() {
        return masterId;
    }

    public void setMasterId(int userId) {
        this.masterId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Дополнительные методы
    @Override
    public String toString() {
        return date + " " + startTime + "-" + endTime + (isAvailable ? " (доступно)" : " (занято)");
    }
}