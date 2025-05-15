package com.example.dtbdemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dtbdemo.model.Listt;
import java.util.List;

@Dao
public interface ListDAO {

    @Query("SELECT * FROM Listt")
    List<Listt> getAllLists();

    @Query("SELECT * FROM Listt WHERE id = :listId LIMIT 1")
    Listt getListById(int listId);

    @Insert
    void insertList(Listt... lists);

    @Update
    void updateList(Listt... lists);

    @Delete
    void deleteList(Listt... lists);

    @Query("DELETE FROM Listt WHERE id = :listId")
    void deleteListById(int listId);
}
