package com.example.dtbdemo.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dtbdemo.model.User;

import java.util.List;

@Dao
public interface UserDAO {

    // Lấy tất cả user
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Query("SELECT * FROM User WHERE id = :userId LIMIT 1")
    User getListById(int userId);

    // Thêm user mới
    @Insert
    void insert(User... users);

    // Cập nhật thông tin user
    @Update
    void update(User... users);

    // Xóa user
    @Delete
    void delete(User... users);

    // Tuỳ chọn thêm: Xóa theo ID
    @Query("DELETE FROM User WHERE id = :userId")
    void deleteById(int userId);
}
