# Behaviour-Driven Development - it's not about the tools

The example used in the presentation "Behaviour-Driven Development – it’s not the tools"

## Building

Maven is used for building

    mvn test
    
will run the example.

## The example - step by step

The domain we will be working with is the domain of a todo-list. We will implement a tool for remembering what 
to do and be able to go back and verify that something wasn't forgotten.

This will be done driven from a concrete example formulated in Gherkin:

```
Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Thomas must remember to buy cat food
    Given we are out of food for the cats
    When Thomas writes a note about buying cat food
    Then should his todo list contain buy cat food
```

### Create a feature file for the example

Create the file 

    src/test/resources/se/thinkcode/notes/notes.feature

and add this content to it:

```
Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Thomas must remember to buy cat food
    Given we are out of food for the cats
    When Thomas writes a note about buying cat food
    Then should his todo list contain buy cat food
```

Build the example using Maven with

    mvn test
    
The project will be build but the example is not executed. Why?

We are missing a runner class.

### Add an example runner

The example can be executed using `JUnit` if a test class is added.

Add a file `src/test/java/se/thinkcode/notes/RunExamplesTest.java` with this content:

```
package se.thinkcode.notes;

import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
public class RunExamplesTest {
}
```    
    
Build the example again:

    mvn test

The result will be similar to this:

```
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ bdd-its-not-about-the-tools ---
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest

There were undefined steps. You can implement missing steps with the snippets below:

@Given("we are out of food for the cats")
public void we_are_out_of_food_for_the_cats() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@When("Thomas writes a note about buying cat food")
public void thomas_writes_a_note_about_buying_cat_food() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@Then("should his todo list contain buy cat food")
public void should_his_todo_list_contain_buy_cat_food() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

Tests run: 3, Failures: 0, Errors: 0, Skipped: 3, Time elapsed: 0.234 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 3

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.564 s
[INFO] Finished at: 2019-09-09T11:02:07+02:00
[INFO] Final Memory: 18M/304M
[INFO] ------------------------------------------------------------------------
tsu-mbp:bdd-its-not-about-the-tools tsu$ 
``` 

We have a successful build but Cucumber is telling us that we are missing something. It is telling us that we are 
missing the translation between the example in `Gherkin` and the programming language we are using, `Java`.

We must add this translation from `Gherkin`to code to be able to proceed.

### Implement the missing steps

Cucumber emitted some snippet suggestions that we can use as a starting point. They need a class to live in. 
Add the class `se.thinkcode.notes.NotesStep` in the file `src/test/java/se/thinkcode/notes/NotesStep.java` and paste 
the suggested snippets in to it.

The result when I did it looks like this:

```
package se.thinkcode.notes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NotesStep {
    @Given("we are out of food for the cats")
    public void we_are_out_of_food_for_the_cats() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("Thomas writes a note about buying cat food")
    public void thomas_writes_a_note_about_buying_cat_food() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("should his todo list contain buy cat food")
    public void should_his_todo_list_contain_buy_cat_food() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
```

Build the project again

    mvn test

The result is a bit different this time compared to the last time:

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.234 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.286 s
[INFO] Finished at: 2019-09-09T11:07:58+02:00
[INFO] Final Memory: 12M/309M
[INFO] ------------------------------------------------------------------------
```

Looks good, no failures. But there is a skipped test. Let's examine it a bit more.

I will run the test class from Idea and see what happens.

```
cucumber.api.PendingException: TODO: implement me


	at se.thinkcode.notes.NotesStep.we_are_out_of_food_for_the_cats(NotesStep.java:11)
	at ✽.we are out of food for the cats(classpath:se/thinkcode/notes/notes.feature:7)
```

This is different, Cucumber throws a `cucumber.api.PendingException: TODO: implement me` 
We must implement this step before we can continue.

### Implement the pending `Given` step

The first step that is pending is where we prepare the system under test, SUT. 
That is, we must start with the `Given` step.

In order to set up the system I need to create something that can receive a note that 
should be remembered. 

Implement the `Given` step like this:

```
    @Given("we are out of food for the cats")
    public void we_are_out_of_food_for_the_cats() {
        Memory memory = new Memory();
    }
```

Build again:

    mvn test

Building didn't really go that well:

```
[INFO] Compiling 2 source files to /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/test-classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,9] cannot find symbol
  symbol:   class Memory
  location: class se.thinkcode.notes.NotesStep
[ERROR] /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,29] cannot find symbol
  symbol:   class Memory
  location: class se.thinkcode.notes.NotesStep
[INFO] 2 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 0.927 s
[INFO] Finished at: 2019-09-09T11:15:42+02:00
[INFO] Final Memory: 16M/298M
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:testCompile (default-testCompile) on project bdd-its-not-about-the-tools: Compilation failure: Compilation failure: 
[ERROR] /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,9] cannot find symbol
[ERROR]   symbol:   class Memory
[ERROR]   location: class se.thinkcode.notes.NotesStep
[ERROR] /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,29] cannot find symbol
[ERROR]   symbol:   class Memory
[ERROR]   location: class se.thinkcode.notes.NotesStep
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
```

The reason? We are missing one class needed in the step.

The missing class can be implemented like this:

First, the class `se.thinkcode.notes.Memory` in the file `src/main/java/se/thinkcode/notes/Memory.java`

```
package se.thinkcode.notes;

public class Memory {
}
```

Build again. What do you expect should happen this time?

    mvn test
    
I got something similar to this:

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.238 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.725 s
[INFO] Finished at: 2019-09-09T11:17:46+02:00
[INFO] Final Memory: 19M/304M
[INFO] ------------------------------------------------------------------------
```    

The compilation problem is gone. There is a new skipped test. It is time to implement the next step.

### Implement the pending `When` step

This is the step where an action will be done. This is the step where we will use our system.

In other words, this is where a note should be properly saved.

Unfortunately, this means that `memory` must be available. It also means that there must be 
something tha can hold a note and be saved that must be implemented.

I will start with making `memory` a field in the step class. This will it them available 
in the `When` step.

The `Given`step changes into
```
    private Memory memory;

    @Given("we are out of food for the cats")
    public void we_are_out_of_food_for_the_cats() {
        memory = new Memory();
    }

```

Next step is to create a note and save it in the `When` step:

```
    @When("Thomas writes a note about buying cat food")
    public void thomas_writes_a_note_about_buying_cat_food() {
        Note note = new Note("Buy cat food");
        memory.save(note);
    }
```

This forces me to implement a class `Note` that can hold tasks

```
package se.thinkcode.notes;

public class Note {
    public Note(String task) {
        throw new RuntimeException("Not yet implemented");
    }
}

```
It also forces me to implement a `save(note)` method in `Memory`

```
    public void save(Note note) {
        throw new RuntimeException("Not yet implemented");
    }
```

Build again. What do you expect will happen this time?

    mvn test

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.248 sec <<< FAILURE!
Thomas must remember to buy cat food(Handle notes)  Time elapsed: 0.006 sec  <<< ERROR!
java.lang.RuntimeException: Not yet implemented
	at se.thinkcode.notes.Note.<init>(Note.java:5)
	at se.thinkcode.notes.NotesStep.thomas_writes_a_note_about_buying_cat_food(NotesStep.java:17)
	at ✽.Thomas writes a note about buying cat food(classpath:se/thinkcode/notes/notes.feature:8)


Results :

Tests in error: 
  Thomas must remember to buy cat food(Handle notes): Not yet implemented

Tests run: 1, Failures: 0, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.604 s
[INFO] Finished at: 2019-09-09T11:29:01+02:00
[INFO] Final Memory: 19M/305M
[INFO] ------------------------------------------------------------------------
```

The production code actually have to do something. Let's start with the class `Note` like this:

```
package se.thinkcode.notes;

public class Note {
    private String task;

    public Note(String task) {
        this.task = task;
    }
}
```

It may need additional fields in the future, but its a good start.

Next is the database, `Memory`:

```
package se.thinkcode.notes;

import java.util.ArrayList;
import java.util.List;

public class Memory {
    private List<Note> notes = new ArrayList<>();

    public void save(Note note) {
        notes.add(note);
    }
}
```

An in memory database should be enough at the moment.

Running

    mvn test

should tells us that the last part is still skipped.

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.263 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.667 s
[INFO] Finished at: 2019-09-09T11:34:39+02:00
[INFO] Final Memory: 19M/305M
[INFO] ------------------------------------------------------------------------
```

Running from Idea tells me that there is a last step pending:

```
cucumber.api.PendingException: TODO: implement me


	at se.thinkcode.notes.NotesStep.should_his_todo_list_contain_buy_cat_food(NotesStep.java:24)
	at ✽.should his todo list contain buy cat food(classpath:se/thinkcode/notes/notes.feature:9)
```

### Implement the pending `Then` step

This is the step where we check that the desired behaviour is properly implemented. 
The way to do this is to ask the memory if the expected note is saved or not.

It can be implemented like this:

```
    @Then("should his todo list contain buy cat food")
    public void should_his_todo_list_contain_buy_cat_food() {
        Note expected = new Note("Buy cat food");

        List<Note> notes = memory.getNotes();

        assertThat(notes).containsExactly(expected);
    }
```

I implement the missing method `getNotes()` like this:

```
    public List<Note> getNotes() {
        throw new RuntimeException("Not yet implemented");
    }
```

Build again. What will happen  this time?

    mvn test

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 0.249 sec <<< FAILURE!
Thomas must remember to buy cat food(Handle notes)  Time elapsed: 0.005 sec  <<< ERROR!
java.lang.RuntimeException: Not yet implemented
	at se.thinkcode.notes.Memory.getNotes(Memory.java:14)
	at se.thinkcode.notes.NotesStep.should_his_todo_list_contain_buy_cat_food(NotesStep.java:29)
	at ✽.should his todo list contain buy cat food(classpath:se/thinkcode/notes/notes.feature:9)


Results :

Tests in error: 
  Thomas must remember to buy cat food(Handle notes): Not yet implemented

Tests run: 1, Failures: 0, Errors: 1, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.277 s
[INFO] Finished at: 2019-09-09T12:21:57+02:00
[INFO] Final Memory: 12M/309M
[INFO] ------------------------------------------------------------------------
```

Since I just made the code compile, I get the feedback that there are things that need to 
be properly implemented.


I implemented is as

```
    public List<Note> getNotes() {
        return notes;
    }
```

Building again gives this result:

```
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.337 sec <<< FAILURE!
Thomas must remember to buy cat food(Handle notes)  Time elapsed: 0.097 sec  <<< FAILURE!
java.lang.AssertionError: 
Expecting:
  <[se.thinkcode.notes.Note@6f96c77]>
to contain exactly (and in same order):
  <[se.thinkcode.notes.Note@3ba9ad43]>
but some elements were not found:
  <[se.thinkcode.notes.Note@3ba9ad43]>
and others were not expected:
  <[se.thinkcode.notes.Note@6f96c77]>

	at se.thinkcode.notes.NotesStep.should_his_todo_list_contain_buy_cat_food(NotesStep.java:31)
	at ✽.should his todo list contain buy cat food(classpath:se/thinkcode/notes/notes.feature:9)


Results :

Failed tests:   Thomas must remember to buy cat food(Handle notes): (..)

Tests run: 1, Failures: 1, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.825 s
[INFO] Finished at: 2019-09-09T12:23:57+02:00
[INFO] Final Memory: 20M/303M
[INFO] ------------------------------------------------------------------------
```

This is telling me that the system is used. It also tells me that the note found can't 
be compared with the note expected.

The missing part may be implemented as

```
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(task, note.task);
    }
```

Run the build again and see if it passes this time.

    mvn test
    
I got:

```
$ mvn test
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building bdd-its-not-about-the-tools 1.0.0
[INFO] ------------------------------------------------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ bdd-its-not-about-the-tools ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] skip non existing resourceDirectory /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/src/main/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:compile (default-compile) @ bdd-its-not-about-the-tools ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ bdd-its-not-about-the-tools ---
[WARNING] Using platform encoding (UTF-8 actually) to copy filtered resources, i.e. build is platform dependent!
[INFO] Copying 1 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.8.1:testCompile (default-testCompile) @ bdd-its-not-about-the-tools ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 2 source files to /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ bdd-its-not-about-the-tools ---
[INFO] Surefire report directory: /Users/tsu/Dropbox/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.307 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.716 s
[INFO] Finished at: 2019-09-09T12:29:33+02:00
[INFO] Final Memory: 20M/305M
[INFO] ------------------------------------------------------------------------
```    
    
This is a working build! We have successfully been able to take a first step 
in implementing a todo-list.

The work in the test automation is done. We now need to fix the production code to 
make sure notes can be compared.

We will probably have a need for a database in the future. The database implementation 
may be postponed a bit. We can probably be able to verify that saving and retrieving 
notes works without a database at the moment. It is probably the work for tomorrow to 
add a database. Or next week.

## Looking ahead

There is one last thing that I want to do. I want to foresee that the communication with 
the system under test might be much more complicated.

The way to handle complexity in programming is more often than not to add an abstraction layer.

There is a quote from David Wheeler that says:
"All problems in computer science can be solved by another level of indirection"
  
That is, move the complexity a bit to a place where it is easier to deal with.

### Preparing for a more complicated communication with `Memory`

The API for `Memory` will probably change. The API the test framework will use will probably 
not have to change. I will therefore introduce a helper between the steps and 
the system under test.

This will allow us to keep the steps simple even when we change the way the communication 
is done from an in memory solution to a web application using Selenium.

Add a new class called `MemoryDelegator` in a file 
`src/test/java/se/thinkcode/notes/MemoryDelegator.java`  

Next step is to update the `NotesStep` to use the delegator. It may look like this:

```
package se.thinkcode.notes;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotesStep {
    private MemoryDelegator delegator;
    
    @Given("we are out of food for the cats")
    public void we_are_out_of_food_for_the_cats() {
        delegator = new MemoryDelegator();
    }

    @When("Thomas writes a note about buying cat food")
    public void thomas_writes_a_note_about_buying_cat_food() {
        Note note = new Note("Buy cat food");
        delegator.save(note);
    }

    @Then("should his todo list contain buy cat food")
    public void should_his_todo_list_contain_buy_cat_food() {
        Note expected = new Note("Buy cat food");

        List<Note> notes = delegator.getNotes();

        assertThat(notes).containsExactly(expected);
    }
}
```

Then implement the memory delegator like this:

```
package se.thinkcode.notes;

import java.util.List;

public class MemoryDelegator {
    private Memory memory;

    public MemoryDelegator() {
        this.memory = new Memory();
    }

    public void save(Note note) {
        memory.save(note);
    }

    public List<Note> getNotes() {
        return memory.getNotes();
    }
}
```

Running Maven again should give us a successful build. Try and verify that it really works!

    mvn test
    
Is this last step really useful? The implementations in the delegator and in the steps 
are almost identical.

The last step is useful when we extend this to a more complicated example. The steps are 
now separated from the implementation with a protecting layer in between. This allows 
the steps to focus on one thing, translating from a human readable form 
(`Gherkin`) to `Java` code.
