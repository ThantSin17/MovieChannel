package com.stone.moviechannel.model;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.concurrent.Executor;

import androidx.annotation.Nullable;

public class FireStoreModel {
    private static FireStoreModel INSTANCE;
    private static FirebaseFirestore db;

    public static FireStoreModel getINSTANCE() {
        if (INSTANCE==null){
            INSTANCE=new FireStoreModel();
            db=FirebaseFirestore.getInstance();
        }
        return INSTANCE;
    }
    public void getViewer(final Context context, int id){
        db.collection("Movie")
                .whereEqualTo("id",id)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error!=null){
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        assert value != null;
                        for (QueryDocumentSnapshot snapshot:value){
                            Toast.makeText(context, snapshot.getString("title"), Toast.LENGTH_SHORT).show();
                        }



                    }
                });
    }
}
