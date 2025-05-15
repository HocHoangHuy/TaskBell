package com.example.dtbdemo.firestore;

import android.util.Log;

import com.example.dtbdemo.model.Reminder;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ReminderRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "reminders";

    // ✅ Thêm reminder
    public void addReminder(Reminder reminder) {
        db.collection(COLLECTION).document(String.valueOf(reminder.getId()))
                .set(reminder)
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "reminder added"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Add reminder failed", e));
    }

    // ✅ Cập nhật reminder
    public void updateReminder(Reminder reminder) {
        db.collection(COLLECTION).document(String.valueOf(reminder.getId()))
                .set(reminder) // set sẽ ghi đè, nên dùng được như update
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "reminder updated"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Update reminder failed", e));
    }

    // ✅ Xóa reminder theo ID
    public void deleteReminderById(String reminderId) {
        db.collection(COLLECTION).document(reminderId)
                .delete()
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "reminder deleted"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete reminder failed", e));
    }

    // ✅ Xóa tất cả reminder
    public void deleteAllReminders() {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }
                    Log.d("FIRESTORE", "All reminder deleted");
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete all reminder failed", e));
    }

    // ✅ Lấy tất cả reminder
    public void getAllReminder(OnSuccessListener<List<Reminder>> listener) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(query -> {
                    List<Reminder> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Reminder reminder = doc.toObject(Reminder.class);
                        if (reminder != null) result.add(reminder);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get all reminders failed", e));
    }

    // ✅ Lấy reminder theo ID
    public void getReminderById(String reminderId, OnSuccessListener<Reminder> listener) {
        db.collection(COLLECTION).document(reminderId)
                .get()
                .addOnSuccessListener(doc -> {
                    Reminder reminder = doc.toObject(Reminder.class);
                    listener.onSuccess(reminder);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get reminder by ID failed", e));
    }

    // ✅ Lấy reminder theo taskId
    public void getReminderByTaskId(String taskId, OnSuccessListener<List<Reminder>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("taskId", taskId)
                .get()
                .addOnSuccessListener(query -> {
                    List<Reminder> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Reminder reminder = doc.toObject(Reminder.class);
                        if (reminder != null) result.add(reminder);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get reminder by taskId failed", e));
    }
}
