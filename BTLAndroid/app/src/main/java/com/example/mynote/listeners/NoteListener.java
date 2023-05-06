package com.example.mynote.listeners;

import com.example.mynote.entities.Note;

public interface NoteListener {
    void onNoteClicked(Note note, int position);
}
