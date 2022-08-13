package com.example.todoapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.data.CardSource;
import com.example.todoapp.data.NoteCard;
import com.example.todoapp.ui.OnItemClickListener;

import java.text.SimpleDateFormat;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    //private List<Note> noteList;
    private CardSource noteCardSource;
    private Context context;
    private OnItemClickListener itemClickListener;
    private Fragment fragment;
    private int menuPosition;

    public RecyclerViewAdapter(CardSource noteCardSource, Fragment fragment) {
        this.noteCardSource = noteCardSource;
        this.fragment = fragment;
        Log.d("TAG", "RecyclerViewAdapter: constructor: noteList: " + noteCardSource);
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getMenuPosition() {
        return menuPosition;
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

        holder.setData(noteCardSource.getNoteCard(position));
    }

    @Override
    public int getItemCount() {
        Log.d("TAG", "RecyclerViewAdapter: getItemCount: noteList.size(): " + noteCardSource.size());
        return noteCardSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder { // ссылка на one_line_note

        private TextView tv_note_id;
        private TextView tv_note_title;
        private TextView getTv_note_description;
        private TextView tv_note_date;
        private CardView cardNoteView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("TAG", "RecyclerViewAdapter: public class MyViewHolder:");

            tv_note_title = itemView.findViewById(R.id.tv_one_line_note_title);
            tv_note_date = itemView.findViewById(R.id.tv_one_line_note_data);
            cardNoteView = itemView.findViewById(R.id.cv_one_line_note);

            // Обработчик нажатий на этом ViewHolder
            cardNoteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, position);
                    }
                }
            });

            registerContextMenu(itemView);

    /* // НУЖНО - ЕСЛИ НАДО ОТКРЫВАТЬ ТАКОЕ МЕНЮ НА КЛИКАБЕЛЬНОМ ЭЛЕМЕНТЕ
            cardNoteView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(20, 20);
                    return true;                }            });*/
        }

        private void registerContextMenu(View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        menuPosition = getLayoutPosition();
                        //Toast.makeText(v.getContext(), "pos - " + menuPosition, Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        @SuppressLint("SimpleDateFormat")
        public void setData(NoteCard noteCard) {
            tv_note_title.setText(noteCard.getTitle());
            tv_note_date.setText(new SimpleDateFormat("dd-MM-yy").format(noteCard.getDateTime()));
        }
    }
}