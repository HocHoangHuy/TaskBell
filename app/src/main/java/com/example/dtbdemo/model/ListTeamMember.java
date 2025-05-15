package com.example.dtbdemo.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ListTeamMember",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "memberId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Listt.class,
                        parentColumns = "id",
                        childColumns = "listId",
                        onDelete = ForeignKey.CASCADE
                )
        }
)

public class ListTeamMember {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo
    private int listId;
    @ColumnInfo
    private int memberId;
    public ListTeamMember() {}
    public ListTeamMember(int listId, int memberId) {
        this.listId = listId;
        this.memberId = memberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberid(int memberId) {
        this.memberId = memberId;
    }
}
