package com.example.todoapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<Note> noteList;
    private Context context;
    private OnItemClickListener itemClickListener;

    public RecyclerViewAdapter(List<Note> noteList, Context context) {
        this.noteList = noteList;
        this.context = context;
        Log.d("TAG", "RecyclerViewAdapter: constructor: noteList: " + noteList);

    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Создаём новый элемент пользовательского интерфейса
        // Через Inflater
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_line_note, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        Log.d("TAG", "RecyclerViewAdapter: onCreateViewHolder: holder: " + holder);
        return holder;
    }

    // Заменить данные в пользовательском интерфейсе
    // Вызывается менеджером
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        // Получить элемент из источника данных (БД, интернет...)
        // Вынести на экран, используя ViewHolder
        Log.d("TAG", "RecyclerViewAdapter: onBindViewHolder.");

        holder.tv_note_title.setText(noteList.get(position).getTitle());
        holder.tv_note_date.setText(noteList.get(position).getDateTime().toString());

    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "RecyclerViewAdapter: getItemCount: noteList.size(): " + noteList.size());
        return noteList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder { // ссылка на one_line_note

        private final TextView tv_note_title;
        private final TextView tv_note_date;
        private final CardView cardNoteView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("TAG", "RecyclerViewAdapter: public class MyViewHolder:");

            tv_note_title = itemView.findViewById(R.id.tv_one_line_note_title);
            tv_note_date = itemView.findViewById(R.id.tv_one_line_note_data);
            cardNoteView = itemView.findViewById(R.id.cv_one_line_note);

            cardNoteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });

        }
    }
}
