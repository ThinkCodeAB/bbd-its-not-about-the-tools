# Behaviour-Driven Development - it's not about the tools

The example used in the presentation 30 November "Behaviour-Driven Development – it’s not the tools"

## Building

Maven is used for building

    mvn test
    
will run the example.

## The example - step by step

The domain we will be working with is the domain of a todo-ist. We will implement a tool for remembering what 
to do and be able to go back and verify that something wasn't forgotten.

This will be done driven from a concrete example formulated in Gherkin:

```
Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Malin want to save a note about an upcoming concert
    Given she must remember to book Nalen
    When she writes the note 
    Then should she be able to see the note
```

### Create a feature file for the example

Create the file 

    src/test/resources/se/thinkcode/notes/notes.feature

and add this content to it:

```
Feature: Handle notes

  Users want to save notes easily
  Notes should be saved automatically

  Scenario: Malin want to save a note about an upcoming concert
    Given she must remember to book Nalen
    When she writes the note
    Then should she be able to see the note
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
INFO] Surefire report directory: /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
UUU
Undefined scenarios:
se/thinkcode/notes/notes.feature:6 # Malin want to save a note about an upcoming concert

1 Scenarios (1 undefined)
3 Steps (3 undefined)
0m0.102s


You can implement missing steps with the snippets below:

@Given("she must remember to book Nalen")
public void she_must_remember_to_book_Nalen() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@When("she writes the note")
public void she_writes_the_note() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}

@Then("should she be able to see the note")
public void should_she_be_able_to_see_the_note() {
    // Write code here that turns the phrase above into concrete actions
    throw new cucumber.api.PendingException();
}


Tests run: 3, Failures: 0, Errors: 0, Skipped: 3, Time elapsed: 0.248 sec

Results :

Tests run: 3, Failures: 0, Errors: 0, Skipped: 3

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.327 s
``` 

We have a succesful build but Cucumber is telling us that we are missing something. It is telling us that we are 
missing the translation between the example in `Gherkin` and the programming language we are using, `Java`.

We must add this translation from `Gherkin`to code to be able to proceed.

### Implement the missing steps

Cucumber emitted some snippet suggestions that we can use as a starting point. They need a class to live in. 
Add the class `se.thinkcode.notes.NotesStep` in the file `src/test/java/se/thinkcode/notes/NotesStep.java` and paste 
the suggested snippets in to it.

The result when I did it looks like this:

```
package se.thinkcode.notes;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NotesStep {
    @Given("she must remember to book Nalen")
    public void she_must_remember_to_book_Nalen() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @When("she writes the note")
    public void she_writes_the_note() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }

    @Then("should she be able to see the note")
    public void should_she_be_able_to_see_the_note() {
        // Write code here that turns the phrase above into concrete actions
        throw new cucumber.api.PendingException();
    }
}
```

Build the project again

    mvn test

The result is a bit different this time compared to the last time:

```
[INFO] Surefire report directory: /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
P--
Pending scenarios:
se/thinkcode/notes/notes.feature:6 # Malin want to save a note about an upcoming concert

1 Scenarios (1 pending)
3 Steps (2 skipped, 1 pending)
0m0.098s

cucumber.api.PendingException: TODO: implement me
	at se.thinkcode.notes.NotesStep.she_must_remember_to_book_Nalen(NotesStep.java:11)
	at ✽.she must remember to book Nalen(se/thinkcode/notes/notes.feature:7)


Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.241 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.604 s
```

Cucumber is now telling us that there is a `Pending scenario`. We must implement this step before we can continue.

### Implement the pending `Given` step

The first step that is pending is where we prepare the system under test, SUT. That, we must start with the `Given` step.

In order to set up the system I need to create something that can receive a note that should be remembered. 

Implement the `Given` step like this:

```
    @Given("she must remember to book Nalen")
    public void she_must_remember_to_book_Nalen() {
        Memory memory = new Memory();
        Note note = new Note("Remember to book Nalen");
    }
```

Build again:

    mvn test

Building didn't really go that well:

```
[INFO] Compiling 2 source files to /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/test-classes
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,9] cannot find symbol
  symbol:   class Memory
  location: class se.thinkcode.notes.NotesStep
[ERROR] /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[10,29] cannot find symbol
  symbol:   class Memory
  location: class se.thinkcode.notes.NotesStep
[ERROR] /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[11,9] cannot find symbol
  symbol:   class Note
  location: class se.thinkcode.notes.NotesStep
[ERROR] /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[11,25] cannot find symbol
  symbol:   class Note
  location: class se.thinkcode.notes.NotesStep
[INFO] 4 errors 
```

The reason? We are missing two classes needed in the step.

The missing classes can be implemented like this:

First, the class `se.thinkcode.notes.Memory` in the file `src/main/java/se/thinkcode/notes/Memory.java`

```
package se.thinkcode.notes;

public class Memory {
}
```

And then the class `se.thinkcode.notes.Notes` in the file `src/main/java/se/thinkcode/notes/Notes.java`

```
package se.thinkcode.notes;

public class Note {
    public Note(String note) {
    }
}
```

Build again. What do you expect should happen this time?

    mvn test
    
I got something similar to this:

```
[INFO] Surefire report directory: /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
.P-
Pending scenarios:
se/thinkcode/notes/notes.feature:6 # Malin want to save a note about an upcoming concert

1 Scenarios (1 pending)
3 Steps (1 skipped, 1 pending, 1 passed)
0m0.098s

cucumber.api.PendingException: TODO: implement me
	at se.thinkcode.notes.NotesStep.she_writes_the_note(NotesStep.java:17)
	at ✽.she writes the note(se/thinkcode/notes/notes.feature:8)


Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.25 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.696 s
```    

The compilation problem is gone. There is a new step pending. It is time to implement the next step.

### Implement the pending `When` step

This is the step where an action will be done. This is the step where we will use our system.

in other words, this is where the note should be saved.

Unfortunately, this means that `memory` and `note` must be available. It also means that there is a saved method that must be implemented.

I will start with making `memory` and `note` fields in the step class. This will make them available in the `When` step.

The `Given`step changes into

```
    private Memory memory;
    private Note note;

    @Given("she must remember to book Nalen")
    public void she_must_remember_to_book_Nalen() {
        memory = new Memory();
        note = new Note("Remember to book Nalen");
    }
```

Next step is to use the fields in the `When` step:

```
    @When("she writes the note")
    public void she_writes_the_note() {
        memory.save(note);
    }
```

This forces me to implement a `save(note)` method in `Memory`

```
    public void save(Note note) {
        
    }
```

Build again. What do you expect will happen this time?


    mvn test

```
[INFO] Surefire report directory: /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
..P
Pending scenarios:
se/thinkcode/notes/notes.feature:6 # Malin want to save a note about an upcoming concert

1 Scenarios (1 pending)
3 Steps (1 pending, 2 passed)
0m0.097s

cucumber.api.PendingException: TODO: implement me
	at se.thinkcode.notes.NotesStep.should_she_be_able_to_see_the_note(NotesStep.java:25)
	at ✽.should she be able to see the note(se/thinkcode/notes/notes.feature:9)


Tests run: 1, Failures: 0, Errors: 0, Skipped: 1, Time elapsed: 0.25 sec

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 1

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.677 s
```

Our two first steps are now passing and we have a last step pending.

### Implement the pending `Then` step

We can see that the Memory class doesn't really do anything yet. This will be addressed in this last step when we check 
that the saved not is possible to see.

Implement the verification this this:

```
    @Then("should she be able to see the note")
    public void should_she_be_able_to_see_the_note() {
        Note actual = memory.getSavedNote();
        assertThat(actual, is(note));
    }
```

Build again. What will happen  this time?

    mvn test

```
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/src/test/java/se/thinkcode/notes/NotesStep.java:[27,29] cannot find symbol
  symbol:   method getSavedNote()
  location: variable memory of type se.thinkcode.notes.Memory
[INFO] 1 error
```

There is a compilation error and the method `getSavedNote()` must be implemented.

I implemented is as

```
    public Note getSavedNote() {
        return null;
    }    
```

Building again gives this result:

```
[INFO] Surefire report directory: /Users/tsu/projects/tsu/bdd-its-not-about-the-tools/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running se.thinkcode.notes.RunExamplesTest
..F
Failed scenarios:
se/thinkcode/notes/notes.feature:6 # Malin want to save a note about an upcoming concert

1 Scenarios (1 failed)
3 Steps (1 failed, 2 passed)
0m0.103s

java.lang.AssertionError: 
Expected: is <se.thinkcode.notes.Note@76329302>
     but: was null
	at org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:20)
	at org.junit.Assert.assertThat(Assert.java:956)
	at org.junit.Assert.assertThat(Assert.java:923)
	at se.thinkcode.notes.NotesStep.should_she_be_able_to_see_the_note(NotesStep.java:28)
	at ✽.should she be able to see the note(se/thinkcode/notes/notes.feature:9)


Tests run: 1, Failures: 1, Errors: 0, Skipped: 0, Time elapsed: 0.258 sec <<< FAILURE!
Malin want to save a note about an upcoming concert(Handle notes)  Time elapsed: 0.009 sec  <<< FAILURE!
java.lang.AssertionError: 
Expected: is <se.thinkcode.notes.Note@76329302>
     but: was null
	at org.hamcrest.MatcherAssert.assertThat(MatcherAssert.java:20)
	at org.junit.Assert.assertThat(Assert.java:956)
	at org.junit.Assert.assertThat(Assert.java:923)
	at se.thinkcode.notes.NotesStep.should_she_be_able_to_see_the_note(NotesStep.java:28)
	at ✽.should she be able to see the note(se/thinkcode/notes/notes.feature:9)


Results :

Failed tests:   Malin want to save a note about an upcoming concert(Handle notes): (..)

Tests run: 1, Failures: 1, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.836 s
```

This is telling us that the system is used. The return value doesn't match the expected valu when the automated test is executed.

The work in the test automation is done. We now need to fix the production code to save the note and return it properly. 
We will probably have a need for a database. The database implementation may be postponed a bit. We can probably be able 
to verify that saving and retrieving notes works without a database today. It is probably the work for tomorrow to add a database. Or next week.

The implementation of memory can be done like this:

```
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
```

A rather simplistic solution. But it is a first step. And no matter what we think, this example should probably always 
be supported by the system. The implementation of `Memory` will probably change. But this functionality should always be there.

There is one last thing that I want to do. I want to foresee that the communication with the system under test might 
be much more complicated.

The way to handle complexity in programming is more often than not to add an abstraction layer. That is.move the complexity a bit.

### Preparing for a more complicated communication with `Memory`

The API for `Memory` is likely to be changed. But the API the test automation framework, our steps, need wil probably 
not change. I will therefore introduce a helper between the steps and the system under test.

This will allow us to keep the steps simple even when we change the way the communication is done from an in memory solution to a web application using Selenium.

Add a new helper called `MemoryHelper` in a file `src/test/java/se/thinkcode/notes/MemoryHelper.java`  

The first thing to do is to update the `NotesStep` to use the helper. It may look like this:

```
package se.thinkcode.notes;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NotesStep {
    private MemoryHelper memoryHelper = new MemoryHelper();
    private Note note;

    @Given("she must remember to book Nalen")
    public void she_must_remember_to_book_Nalen() {
        note = new Note("Remember to book Nalen");
    }

    @When("she writes the note")
    public void she_writes_the_note() {
        memoryHelper.save(note);
    }

    @Then("should she be able to see the note")
    public void should_she_be_able_to_see_the_note() {
        Note actual = memoryHelper.getSavedNote();
        assertThat(actual, is(note));
    }
}
```

Then implement the memory helper like this:

```
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
```

Running Maven again should give us a successful build. Try and verify that it really works!

    mvn test
    
Is this last step really useful? The implementations in the helper and in the steps are almost identical.

The last step is useful when we extend this to a more complicated example. The steps are now separated from the 
implementation with a protecting layer in between. This allows the steps to focus on one thing, translating from a human 
readable form (`Gherkin`) to `Java` code.

