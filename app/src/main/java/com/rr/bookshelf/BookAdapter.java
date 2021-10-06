package com.rr.bookshelf;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.bookHolder> {
    ArrayList<Book> bookList;
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

    @Override
    public void onBindViewHolder(@NonNull bookHolder holder, int position) {
        Book data = bookList.get(position);
        holder.title.setText(data.getTitle());
        holder.author.setText(data.getAuthor());
        holder.publisher.setText(data.getPublisher());
        holder.year.setText(data.getYear());
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class bookHolder extends RecyclerView.ViewHolder {
        TextView title, author, publisher, year;
        CardView card_item;
        public bookHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            author = itemView.findViewById(R.id.textAuthor);
            publisher = itemView.findViewById(R.id.textPublisher);
            year = itemView.findViewById(R.id.textYear);
            card_item = itemView.findViewById(R.id.card_item);
        }
    }
}
