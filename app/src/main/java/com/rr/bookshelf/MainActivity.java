package com.rr.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BookAdapter bookAdapter;
    DatabaseReference database;
    ArrayList<Book> bookList;
    RecyclerView recycleViewUnDone;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        Button btnTambah = findViewById(R.id.btnTambah);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toInput = new Intent(getBaseContext(), Input.class);
                startActivity(toInput);
            }
        });


        recycleViewUnDone = findViewById(R.id.recycleViewUnDone);
        database = FirebaseDatabase.getInstance("https://bookshelf-47530-default-rtdb.firebaseio.com/").getReference("Books");
        recycleViewUnDone.setHasFixedSize(true);
        recycleViewUnDone.setLayoutManager(new LinearLayoutManager(this));

        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter( bookList, this);
        recycleViewUnDone.setAdapter(bookAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookList = new ArrayList<>();
                for (DataSnapshot i : snapshot.getChildren()){
                    Book singleBook = i.getValue(Book.class);
//                    singleBook.setId(i.getKey());
                    System.out.println(singleBook);
                    bookList.add(singleBook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}