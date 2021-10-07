package com.rr.bookshelf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
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
