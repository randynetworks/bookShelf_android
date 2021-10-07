package com.rr.bookshelf;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {

    BookAdapter bookUnDoneAdapter;
    BookAdapter bookDoneAdapter;
    DatabaseReference database = FirebaseDatabase.getInstance("https://bookshelf-47530-default-rtdb.firebaseio.com/").getReference("Books");
    ArrayList<Book> bookUnDoneList;
    ArrayList<Book> bookDoneList;
    RecyclerView recycleViewUnDone;
    RecyclerView recycleViewDone;
    
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

        // UN DONE READ
        recycleViewUnDone = findViewById(R.id.recycleViewUnDone);
        recycleViewUnDone.setHasFixedSize(true);
        recycleViewUnDone.setLayoutManager(new LinearLayoutManager(this));

        bookUnDoneList = new ArrayList<>();
        bookUnDoneAdapter = new BookAdapter(bookUnDoneList, this);
        recycleViewUnDone.setAdapter(bookUnDoneAdapter);

        // DONE READ
        recycleViewDone = findViewById(R.id.recycleViewDone);
        recycleViewDone.setHasFixedSize(true);
        recycleViewDone.setLayoutManager(new LinearLayoutManager(this));

        bookDoneList = new ArrayList<>();
        bookDoneAdapter = new BookAdapter(bookDoneList, this);
        recycleViewDone.setAdapter(bookDoneAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren() ){
                    Book dataBook = dataSnapshot.getValue(Book.class);
                    if (dataBook.getComplete() == 0) {
                        bookUnDoneList.add(dataBook);
                    } else {
                        bookDoneList.add(dataBook);
                    }
                }
                bookUnDoneAdapter.notifyDataSetChanged();
                bookDoneAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}