package com.example.dtbdemo.firestore;

import android.util.Log;

import com.example.dtbdemo.model.ListTeamMember;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ListTeamMemberRepository {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String COLLECTION = "ltms";

    // ✅ Thêm ltm
    public void addListTeamMember(ListTeamMember ltm) {
        db.collection(COLLECTION).document(String.valueOf(ltm.getId()))
                .set(ltm)
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltm added"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Add ltm failed", e));
    }

    // ✅ Cập nhật ltm
    public void updateListTeamMember(ListTeamMember ltm) {
        db.collection(COLLECTION).document(String.valueOf(ltm.getId()))
                .set(ltm) // set sẽ ghi đè, nên dùng được như update
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltm updated"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Update ltm failed", e));
    }

    // ✅ Xóa ltm theo ID
    public void deleteListTeamMemberById(String ltmId) {
        db.collection(COLLECTION).document(ltmId)
                .delete()
                .addOnSuccessListener(unused -> Log.d("FIRESTORE", "ltm deleted"))
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete ltm failed", e));
    }

    // ✅ Xóa tất cả ltms
    public void deleteAllListTeamMember() {
        db.collection(COLLECTION).get()
                .addOnSuccessListener(query -> {
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        doc.getReference().delete();
                    }
                    Log.d("FIRESTORE", "All ltm deleted");
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Delete all ltms failed", e));
    }

    // ✅ Lấy tất cả ListTeamMember
    public void getAllListTeamMember(OnSuccessListener<List<ListTeamMember>> listener) {
        db.collection(COLLECTION)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMember> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMember ltm = doc.toObject(ListTeamMember.class);
                        if (ltm != null) result.add(ltm);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get all ltms failed", e));
    }

    // ✅ Lấy ltm theo ID
    public void getListTeamMemberById(String ltmId, OnSuccessListener<ListTeamMember> listener) {
        db.collection(COLLECTION).document(ltmId)
                .get()
                .addOnSuccessListener(doc -> {
                    ListTeamMember ltm = doc.toObject(ListTeamMember.class);
                    listener.onSuccess(ltm);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltm by ID failed", e));
    }

    // ✅ Lấy ltm theo listId
    public void getListTeamMemberByTaskId(String listId, OnSuccessListener<List<ListTeamMember>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("listId", listId)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMember> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMember ltm = doc.toObject(ListTeamMember.class);
                        if (ltm != null) result.add(ltm);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltm by listId failed", e));
    }

    // ✅ Lấy ltm theo userId
    public void getListTeamMemberByUserId(String userId, OnSuccessListener<List<ListTeamMember>> listener) {
        db.collection(COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .addOnSuccessListener(query -> {
                    List<ListTeamMember> result = new ArrayList<>();
                    for (DocumentSnapshot doc : query.getDocuments()) {
                        ListTeamMember ltm = doc.toObject(ListTeamMember.class);
                        if (ltm != null) result.add(ltm);
                    }
                    listener.onSuccess(result);
                })
                .addOnFailureListener(e -> Log.e("FIRESTORE", "Get ltm by userId failed", e));
    }
}
