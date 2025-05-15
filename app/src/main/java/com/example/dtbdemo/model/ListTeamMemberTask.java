package com.example.dtbdemo.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ListTeamMemberTask",
        foreignKeys = {
                @ForeignKey(
                        entity = Task.class,
                        parentColumns = "id",
                        childColumns = "taskId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "memberId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)

public class ListTeamMemberTask {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private int taskId;
    @ColumnInfo
    private int memberId;
    public ListTeamMemberTask() {}
    public ListTeamMemberTask(int taskId, int memberId) {
        this.taskId = taskId;
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }
}
