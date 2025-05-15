package com.example.dtbdemo.firestore;

import android.util.Log;

import com.example.dtbdemo.model.Listt;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "lists";

    // ✅ Thêm list
    public void addList(Listt list) {
        db.collection(COLLECTION).document(String.valueOf(list.getId()))
                .set(list)
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "list added"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Add list failed", e));
    }

    // ✅ Cập nhật list
    public void updateList(Listt list) {
        db.collection(COLLECTION).document(String.valueOf(list.getId()))
                .set(list) // set sẽ ghi đè, nên dùng được như update
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "list updated"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Update list failed", e));
    }

    // ✅ Xóa list theo ID
    public void deleteListById(String listId) {
        db.collection(COLLECTION).document(listId)
                .delete()
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "list deleted"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete list failed", e));
    }

    // ✅ Xóa tất cả list
    public void deleteAllLists() {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }
                    Log.d("FIRESTORE", "All list deleted");
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete all list failed", e));
    }

    // ✅ Lấy tất cả list
    public void getAllList(OnSuccessListener<List<Listt>> listener) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(query -> {
                    List<Listt> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Listt list = doc.toObject(Listt.class);
                        if (list != null) result.add(list);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get all lists failed", e));
    }

    // ✅ Lấy list theo ID
    public void getListById(String listId, OnSuccessListener<Listt> listener) {
        db.collection(COLLECTION).document(listId)
                .get()
                .addOnSuccessListener(doc -> {
                    Listt list = doc.toObject(Listt.class);
                    listener.onSuccess(list);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get list by ID failed", e));
    }

    // ✅ Lấy list theo UserId
    public void getListByUserId(String userId, OnSuccessListener<List<Listt>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("listId", userId)
                .get()
                .addOnSuccessListener(query -> {
                    List<Listt> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        Listt list = doc.toObject(Listt.class);
                        if (list != null) result.add(list);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get list by userId failed", e));
    }
}
