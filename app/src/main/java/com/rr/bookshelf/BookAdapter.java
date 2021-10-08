package com.rr.bookshelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.bookHolder> {
    ArrayList<Book> bookList;
    DatabaseReference database = FirebaseDatabase.getInstance("https://bookshelf-47530-default-rtdb.firebaseio.com/").getReference("Books");
    Context context;

    public BookAdapter(ArrayList<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @NonNull
    @Override
    public bookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new bookHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull bookHolder holder, int position) {
        Book data = bookList.get(position);
        holder.title.setText(data.getTitle());
        holder.author.setText(data.getAuthor());
        holder.publisher.setText(data.getPublisher());
        holder.year.setText(data.getYear());


        if (data.getComplete() == 1) {
            holder.doneOrUnDone.setText("Belum Beres");
        } else {
            holder.doneOrUnDone.setText("Udah Beres");
        }

        holder.card_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.card_item.setBackgroundColor(Color.rgb(220, 53, 69));
                holder.title.setTextColor(Color.rgb(255,255,255));
                holder.author.setTextColor(Color.rgb(255,255,255));
                holder.publisher.setTextColor(Color.rgb(255,255,255));
                holder.year.setTextColor(Color.rgb(255,255,255));


                holder.doneOrUnDone.setVisibility(View.GONE);
                holder.btnEdit.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.text_info.setVisibility(View.VISIBLE);
                return true;
            }
        });

        holder.card_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.card_item.setBackgroundColor(Color.rgb(255, 204, 0));
                holder.title.setTextColor(Color.rgb(15,52,146));
                holder.author.setTextColor(Color.rgb(15,52,146));
                holder.publisher.setTextColor(Color.rgb(15,52,146));
                holder.year.setTextColor(Color.rgb(15,52,146));
                holder.doneOrUnDone.setVisibility(View.VISIBLE);
                holder.btnEdit.setVisibility(View.VISIBLE);
                holder.btnDelete.setVisibility(View.GONE);
                holder.text_info.setVisibility(View.GONE);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.child(data.getId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent main = new Intent(context, MainActivity.class);
                        context.startActivity(main);
                        Toast.makeText(context,"Buku berhasil di hapus!", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        holder.doneOrUnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int complete;
                if (data.getComplete() == 1) {
                    complete = 0;
                } else {
                    complete = 1;
                }
                database.child(data.getId()).child("complete").setValue(complete).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent main = new Intent(context, MainActivity.class);
                        context.startActivity(main);
                        Toast.makeText(context,"Buku berhasil di pindahkan!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendBook = new Intent(context, Input.class);
                sendBook.putExtra("id", data.getId());
                sendBook.putExtra("year", data.getYear());
                sendBook.putExtra("title", data.getTitle());
                sendBook.putExtra("author", data.getAuthor());
                sendBook.putExtra("complete", data.getComplete());
                sendBook.putExtra("publisher", data.getPublisher());

                sendBook.putExtra("label", "Edit Buku!");
                sendBook.putExtra("textButton", "Edit Buku");

                context.startActivity(sendBook);
            }
        });

    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class bookHolder extends RecyclerView.ViewHolder {
        TextView title, author, publisher, year, text_info;
        Button doneOrUnDone, btnEdit, btnDelete;
        LinearLayout card_item;
        public bookHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            author = itemView.findViewById(R.id.textAuthor);
            publisher = itemView.findViewById(R.id.textPublisher);
            year = itemView.findViewById(R.id.textYear);
            doneOrUnDone = itemView.findViewById(R.id.doneOrUnDone);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            card_item = itemView.findViewById(R.id.card_item);
            text_info = itemView.findViewById(R.id.text_info);
        }
    }
}
