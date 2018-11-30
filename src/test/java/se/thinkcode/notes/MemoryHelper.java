package se.thinkcode.notes;

public class MemoryHelper {
    private Memory memory = new Memory();

    public void save(Note note) {
        memory.save(note);
    }

    public Note getSavedNote() {
        return memory.getSavedNote();
    }
}
