package com.example.dtbdemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "Listt",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "creatorId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Listt{
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "creatorId")
    private int creatorId;

    @ColumnInfo(name = "isTeam")
    private boolean isTeam;

    // Constructor
    public Listt() {}
    public Listt(String name, int creatorId, boolean isTeam) {
        this.name = name;
        this.creatorId = creatorId;
        this.isTeam = isTeam;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public boolean isTeam() {
        return isTeam;
    }

    public void setTeam(boolean team) {
        isTeam = team;
    }
}
