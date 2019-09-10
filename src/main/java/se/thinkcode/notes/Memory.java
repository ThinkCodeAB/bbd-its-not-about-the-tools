package se.thinkcode.notes;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Note> notes = new ArrayList<>();

    public void save(Note note) {
        notes.add(note);
    }

    public List<Note> getNotes() {
        return notes;
    }
}
