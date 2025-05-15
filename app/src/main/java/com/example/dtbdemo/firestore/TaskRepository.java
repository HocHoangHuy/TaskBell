package com.example.dtbdemo.firestore;

import android.util.Log;

import com.example.dtbdemo.model.Listt;
import com.example.dtbdemo.model.Task;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TaskRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "tasks";

    // ✅ Thêm Task
    public void addTask(Task task) {
        db.collection(COLLECTION).document(String.valueOf(task.getId()))
                .set(task)
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "Task added"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Add task failed", e));
    }

    // ✅ Cập nhật Task
    public void updateTask(Task task) {
        db.collection(COLLECTION).document(String.valueOf(task.getId()))
                .set(task) // set sẽ ghi đè, nên dùng được như update
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "Task updated"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Update task failed", e));
    }

    // ✅ Xóa Task theo ID
    public void deleteTaskById(String taskId) {
        db.collection(COLLECTION).document(taskId)
                .delete()
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "Task deleted"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete task failed", e));
    }

    // ✅ Xóa tất cả Task
    public void deleteAllTasks() {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }
                    Log.d("FIRESTORE", "All tasks deleted");
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete all tasks failed", e));
    }

    // ✅ Lấy tất cả Task
    public void getAllTasks(OnSuccessListener<List<Task>> listener) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(query -> {
                    List<Task> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) result.add(task);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get all tasks failed", e));
    }

    // ✅ Lấy Task theo ID
    public void getTaskById(String taskId, OnSuccessListener<Task> listener) {
        db.collection(COLLECTION).document(taskId)
                .get()
                .addOnSuccessListener(doc -> {
                    Task task = doc.toObject(Task.class);
                    listener.onSuccess(task);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get task by ID failed", e));
    }

    // ✅ Lấy Task theo ListId
    public void getTasksByListId(String listId, OnSuccessListener<List<Task>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("listId", listId)
                .get()
                .addOnSuccessListener(query -> {
                    List<Task> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Task task = doc.toObject(Task.class);
                        if (task != null) result.add(task);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get tasks by listId failed", e));
    }

    public void canEdit(String taskId, OnSuccessListener<Boolean> callback) {
        db.collection(COLLECTION).document(taskId).get()
                .addOnSuccessListener(taskDoc -> {
                    Task task = taskDoc.toObject(Task.class);
                    if (task == null || task.getListId() == 0) {
                        callback.onSuccess(false);
                        return;
                    }

                    String listId = String.valueOf(task.getListId());
                    db.collection("lists").document(listId).get()
                            .addOnSuccessListener(listDoc -> {
                                if (!listDoc.exists()) {
                                    callback.onSuccess(false);
                                    return;
                                }

                                // Chuyển về Listt
                                Listt listt = listDoc.toObject(Listt.class);
                                String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                boolean canEdit = listt != null && currentUserId.equals(listt.getCreatorId());
                                callback.onSuccess(canEdit);
                            })
                            .addOnFailureListener(e -> {
                                Log.e("FIRESTORE", "Get list failed", e);
                                callback.onSuccess(false);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e("FIRESTORE", "Get task failed", e);
                    callback.onSuccess(false);
                });
    }
}
