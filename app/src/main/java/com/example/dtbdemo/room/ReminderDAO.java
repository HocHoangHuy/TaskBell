package com.example.dtbdemo.room;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dtbdemo.model.Reminder;

import java.util.List;

@Dao
public interface ReminderDAO {

    @Query("SELECT * FROM Reminder")
    List<Reminder> getAllReminders();

    @Query("SELECT * FROM Reminder WHERE id = :reminderId LIMIT 1")
    Reminder getReminderById(int reminderId);

    @Query("SELECT * FROM Reminder WHERE taskId = :taskId")
    List<Reminder> getReminderByTaskId(int taskId);

    @Insert
    void insertReminder(Reminder... reminders);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Reminder> reminders);

    @Update
    void updateReminder(Reminder... reminders);

    @Delete
    void deleteReminder(Reminder... reminders);

    @Query("DELETE FROM Reminder WHERE id = :reminderId")
    void deleteReminderById(int reminderId);
}
