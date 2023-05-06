package com.example.mynote.activities;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynote.R;
import com.example.mynote.entities.Note;
import com.example.mynote.listeners.NoteListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<Note> notes;
    private NoteListener noteListener;
    private Timer timer;
    private  List<Note> notesSource;


    public NoteAdapter(List<Note> notes, NoteListener noteListener)
    {
        this.notes = notes;
        this.noteListener=noteListener;
        notesSource=notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_container_note,
                        parent,
                        false
                )
        );
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder,int position) {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListener.onNoteClicked(notes.get(position),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static  class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textSubTitle, textDatetime,textWeb;
        LinearLayout layoutNote;
        RoundedImageView imageNote;

         NoteViewHolder(@NonNull View itemView) {super(itemView);
            textTitle=itemView.findViewById(R.id.textTitle);
            textSubTitle=itemView.findViewById(R.id.textSubtitle);
            textDatetime=itemView.findViewById(R.id.textDateTime);
            layoutNote=itemView.findViewById(R.id.layoutNote);
            imageNote= itemView.findViewById(R.id.imageNote);
            textWeb=itemView.findViewById(R.id.textWebURL);
        }
        void setNote(Note note){
            textTitle.setText((note.getTitle()));
            if(note.getSubTitle().trim().isEmpty()) {
                textSubTitle.setVisibility(View.GONE);
            }else{
                textSubTitle.setText(note.getSubTitle());
            }
            textDatetime.setText(note.getDateTime());
            GradientDrawable gradientDrawable =(GradientDrawable) layoutNote.getBackground();
            if(note.getColor()!=null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            }else{
                gradientDrawable.setColor(Color.parseColor("#333333"));
            }

           /* if(note.getImagePath()!=null){
                imageNote.setImageBitmap(BitmapFactory.decodeFile(note.getImagePath()));
                imageNote.setVisibility(View.VISIBLE);
            }else{
                imageNote.setVisibility(View.GONE);
            }*/
        }
    }
    //search
    public void searchNotes(final String searchKeyword ){
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchKeyword.trim().isEmpty()) {
                    notes = notesSource;
                } else {
                    ArrayList<Note> temp = new ArrayList<>();
                    for (Note note : notesSource) {
                        if (note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || note.getSubTitle().toLowerCase().contains(searchKeyword.toLowerCase())
                                || note.getNoteText().toLowerCase().contains(searchKeyword.toLowerCase())) {
                            temp.add(note);
                        }
                    }
                    notes = temp;
                }

                new Handler(Looper.getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },500);
    }
    public void cancleTimer(){
        if(timer!= null){
            timer.cancel();
        }
    }
}
