package com.example.mynote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.mynote.entities.Note;

import java.util.List;

@SuppressWarnings("ALL")
@Dao
public interface NoteDao {
    @Query("Select * from notes order by id desc")
    List<Note> getAllNote();
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);
    @Delete
    void deleteNote(Note note);
}
