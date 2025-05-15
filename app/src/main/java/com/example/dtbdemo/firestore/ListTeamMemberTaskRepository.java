package com.example.dtbdemo.firestore;

import android.util.Log;

import com.example.dtbdemo.model.ListTeamMemberTask;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListTeamMemberTaskRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "ltmts";

    // ✅ Thêm ltmt
    public void addListTeamMemberTask(ListTeamMemberTask ltmt) {
        db.collection(COLLECTION).document(String.valueOf(ltmt.getId()))
                .set(ltmt)
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltmt added"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Add ltmt failed", e));
    }

    // ✅ Cập nhật ltmt
    public void updateListTeamMemberTask(ListTeamMemberTask ltmt) {
        db.collection(COLLECTION).document(String.valueOf(ltmt.getId()))
                .set(ltmt) // set sẽ ghi đè, nên dùng được như update
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltmt updated"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Update ltmt failed", e));
    }

    // ✅ Xóa ltmt theo ID
    public void deleteListTeamMemberTaskById(String ltmtId) {
        db.collection(COLLECTION).document(ltmtId)
                .delete()
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltmt deleted"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete ltmt failed", e));
    }

    // ✅ Xóa tất cả ltmts
    public void deleteAllListTeamMemberTask() {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }
                    Log.d("FIRESTORE", "All ltmt deleted");
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete all ltmt failed", e));
    }

    // ✅ Lấy tất cả ListTeamMemberTask
    public void getAllListTeamMemberTask(OnSuccessListener<List<ListTeamMemberTask>> listener) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMemberTask> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMemberTask ltmt = doc.toObject(ListTeamMemberTask.class);
                        if (ltmt != null) result.add(ltmt);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get all ltmts failed", e));
    }

    // ✅ Lấy ltmt theo ID
    public void getListTeamMemberTaskById(String ltmtId, OnSuccessListener<ListTeamMemberTask> listener) {
        db.collection(COLLECTION).document(ltmtId)
                .get()
                .addOnSuccessListener(doc -> {
                    ListTeamMemberTask ltmt = doc.toObject(ListTeamMemberTask.class);
                    listener.onSuccess(ltmt);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltmt by ID failed", e));
    }

    // ✅ Lấy ltmt theo taskId
    public void getListTeamMemberTaskByTaskId(String taskId, OnSuccessListener<List<ListTeamMemberTask>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("taskId", taskId)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMemberTask> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMemberTask ltmt = doc.toObject(ListTeamMemberTask.class);
                        if (ltmt != null) result.add(ltmt);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltmt by taskId failed", e));
    }

    // ✅ Lấy ltmt theo userId
    public void getListTeamMemberTaskByUserId(String userId, OnSuccessListener<List<ListTeamMemberTask>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMemberTask> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMemberTask ltmt = doc.toObject(ListTeamMemberTask.class);
                        if (ltmt != null) result.add(ltmt);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltmt by userId failed", e));
    }
}
