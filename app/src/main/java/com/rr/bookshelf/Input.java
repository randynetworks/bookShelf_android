package com.rr.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class Input extends AppCompatActivity {

    EditText title;
    EditText author ;
    EditText publisher ;
    EditText year;
    Switch id_done;
    Button btnSimpan;
    Book Newbook;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_input);



        // List variable of book in layout activity
        btnSimpan = findViewById(R.id.btnSimpan);
        title = findViewById(R.id.id_title);
        author = findViewById(R.id.id_author);
        publisher = findViewById(R.id.id_publisher);
        year = findViewById(R.id.id_year);
        id_done = findViewById(R.id.id_done);

        // action for back to home
        Button btnKembali = findViewById(R.id.btnKembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome = new Intent(getBaseContext(), MainActivity.class);
                startActivity(toHome);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String setTitle = title.getText().toString().trim();
                String setAuthor = author.getText().toString().trim();
                String setPublisher = publisher.getText().toString().trim();
                String setYear = year.getText().toString().trim();
                int setComplete = id_done.isChecked() ? 1 : 0;

                System.out.println(setTitle);
                System.out.println(setAuthor);
                System.out.println(setPublisher);
                System.out.println(setYear);
                System.out.println(setComplete);

                Newbook = new Book();
                Newbook.setTitle(setTitle);
                Newbook.setAuthor(setAuthor);
                Newbook.setPublisher(setPublisher);
                Newbook.setYear(setYear);
                Newbook.setComplete(setComplete);

                rootNode = FirebaseDatabase.getInstance("https://bookshelf-47530-default-rtdb.firebaseio.com/");
                reference = rootNode.getReference("Books");
                reference.child(generateID(10)).setValue(Newbook);
                Toast.makeText(Input.this,"Buku Baru berhasil di tambah!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(Input.this, MainActivity.class));
                finish();
            }
        });



    }

    public static String generateID(int len) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
                +"lmnopqrstuvwxyz!@#$%&";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }



}