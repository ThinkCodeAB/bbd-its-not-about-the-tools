package se.thinkcode.notes;

import java.util.Objects;

public class Note {
    private String task;

    public Note(String task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(task, note.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(task);
    }
}
