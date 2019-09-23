package com.pedoran.posttest_002.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pedoran.posttest_002.R;
import com.pedoran.posttest_002.entity.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteVH> {
    Context context;
    List<Note> noteList;
    UserActionListener listener;

    public NoteAdapter(Context context, List<Note> noteList, UserActionListener listener) {
        this.context = context;
        this.noteList = noteList;
        this.listener = listener;
    }

    public interface UserActionListener{
        void onUserClick(Note note);
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        NoteVH nvh = new NoteVH(v);
        return nvh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        final Note catat = noteList.get(position);
        holder.tvTimeStamp.setText(catat.getTimestamp());
        holder.tvTitle.setText(catat.getTitle());
        holder.tvContent.setText(catat.getContent());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onUserClick(catat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class NoteVH extends RecyclerView.ViewHolder{
        TextView tvTimeStamp,tvTitle,tvContent;

        public NoteVH(@NonNull View itemView) {
            super(itemView);
            tvTimeStamp = itemView.findViewById(R.id.tvNoteDate);
            tvTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvContent = itemView.findViewById(R.id.tvNoteContent);
        }
    }
}
