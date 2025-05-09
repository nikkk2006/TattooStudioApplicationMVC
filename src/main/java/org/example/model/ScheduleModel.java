package org.example.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleModel {
    private int id;
    private int masterId;
    private String masterName; // Добавлено новое поле
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isAvailable;

    // Конструкторы
    public ScheduleModel() {}

    public ScheduleModel(int masterId, String masterName, LocalDate date,
                         LocalTime startTime, LocalTime endTime, boolean isAvailable) {
        this.masterId = masterId;
        this.masterName = masterName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isAvailable = isAvailable;
    }

    // Геттеры и сеттеры
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMasterId() { return masterId; }
    public void setMasterId(int masterId) { this.masterId = masterId; }

    public String getMasterName() { return masterName; }
    public void setMasterName(String masterName) { this.masterName = masterName; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    // Форматированный вывод для таблицы
    public Object[] toTableRow() {
        return new Object[] {
                masterName,
                date.toString(),
                startTime.toString(),
                endTime.toString(),
                isAvailable
        };
    }

    @Override
    public String toString() {
        return masterName + ": " + date + " " + startTime + "-" + endTime +
                (isAvailable ? " (доступно)" : " (занято)");
    }
}