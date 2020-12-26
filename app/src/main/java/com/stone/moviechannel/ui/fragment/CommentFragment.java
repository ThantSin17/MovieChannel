package com.stone.moviechannel.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stone.moviechannel.R;
import com.stone.moviechannel.adapter.CommentAdapter;
import com.stone.moviechannel.data.Comment;
import com.stone.moviechannel.databinding.FragmentCommentBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


public class CommentFragment extends Fragment {

    private FragmentCommentBinding binding;
    private FirebaseAuth mAuth;
    private CommentAdapter adapter;
    private String userId;
    private String postId, userName;
    private DatabaseReference mDatabaseComments;
    private DatabaseReference userReference;
    private FirebaseUser currentUser;
    private List<Comment> comments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public CommentFragment(String postId) {

        this.postId = postId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCommentBinding.bind(view);

        //initialize firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        mDatabaseComments = FirebaseDatabase.getInstance()
                .getReference().child("MovieChannel").child("Comments")
                .child(postId);
        if (currentUser!=null) {
            userReference = FirebaseDatabase.getInstance()
                    .getReference().child("MovieChannel").child("Users")
                    .child(mAuth.getCurrentUser().getUid());
            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    userName = (String) snapshot.child("name").getValue();
                    mDatabaseComments.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            comments = new ArrayList<>();

                            for (DataSnapshot ds : snapshot.getChildren()) {
                                Comment comment = ds.getValue(Comment.class);
                                comments.add(comment);

                            }
                            adapter.setComments(comments);
                            binding.commentCount.setText("( "+ comments.size()+" )");
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{

            mDatabaseComments.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments = new ArrayList<>();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Comment comment = ds.getValue(Comment.class);
                        comments.add(comment);

                    }
                    adapter.setComments(comments);
                    binding.commentCount.setText("( "+ comments.size()+" )");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        //bind recycler adapter
        adapter = new CommentAdapter(getContext());
        binding.rcComment.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rcComment.setHasFixedSize(true);
        binding.rcComment.setAdapter(adapter);


        binding.btnSend.setOnClickListener(view1 -> {
            if (currentUser==null){
                Toast.makeText(getActivity(), "Please Login first ", Toast.LENGTH_SHORT).show();
            }else {
                Comment comment = new Comment();
                comment.userId = currentUser.getUid();
                comment.userName = currentUser.getDisplayName();
                comment.photo = currentUser.getPhotoUrl().toString();
                comment.content = binding.commentContent.getText().toString();
                comment.time = System.currentTimeMillis();

                DatabaseReference newComment = mDatabaseComments.push();
                newComment.setValue(comment)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    binding.commentContent.setText("");
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.getRoot().destroyDrawingCache();
    }
}