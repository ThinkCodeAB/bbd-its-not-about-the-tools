package se.thinkcode.notes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteSteps {
    private Memory memory;

    @Given("we are out of food for the cats")
    public void we_are_out_of_food_for_the_cats() {
        memory = new Memory();
    }

    @When("Thomas writes a note about buying cat food")
    public void thomas_writes_a_note_about_buying_cat_food() {
        Note note = new Note("Buy cat food");
        memory.save(note);
    }

    @Then("should his todo list contain buy cat food")
    public void should_his_todo_list_contain_buy_cat_food() {
        Note expected = new Note("Buy cat food");

        List<Note> actual = memory.getNotes();

        assertThat(actual).containsExactly(expected);
    }
}
