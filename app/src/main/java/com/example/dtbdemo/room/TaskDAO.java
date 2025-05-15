package com.example.dtbdemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dtbdemo.model.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM Task")
    List<Task> getAllTasks();

    @Query("SELECT * FROM Task WHERE id = :taskId LIMIT 1")
    Task getTaskById(int taskId);

    @Query("SELECT * FROM Task WHERE listId = :listId")
    List<Task> getTaskByListId(int listId);

    @Insert
    void insertTask(Task... tasks);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Task> tasks);

    @Update
    void updateTask(Task... tasks);

    @Delete
    void deleteTask(Task... tasks);

    @Query("DELETE FROM Task WHERE id = :taskId")
    void deleteTaskById(int taskId);
}
