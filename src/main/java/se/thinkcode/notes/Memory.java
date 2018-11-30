package se.thinkcode.notes;

public class Memory {
    private Note note;

    public void save(Note note) {
        this.note = note;
    }

    public Note getSavedNote() {
        return note;
    }
}
