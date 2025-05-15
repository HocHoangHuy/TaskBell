package com.example.dtbdemo.model;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
        tableName = "Reminder",
        foreignKeys = @ForeignKey(
                entity = Task.class,
                parentColumns = "id",
                childColumns = "taskId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Reminder {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "remindTime")
    private Date remindTime;

    @ColumnInfo(name = "taskId")
    private int taskId;

    // Constructor
    public Reminder() {}
    public Reminder(Date remindTime, int taskId) {
        this.remindTime = remindTime;
        this.taskId = taskId;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
