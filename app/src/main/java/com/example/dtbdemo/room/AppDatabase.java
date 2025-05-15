package com.example.dtbdemo.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.dtbdemo.model.Reminder;
import com.example.dtbdemo.model.Task;
import com.example.dtbdemo.model.User;

import com.example.dtbdemo.model.Listt;

@Database(entities = {User.class, Listt.class, Task.class, Reminder.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDAO userDAO();
    public abstract ListDAO listDAO();
    public abstract TaskDAO taskDAO();
    public abstract ReminderDAO reminderDAO();

    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "users")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}
