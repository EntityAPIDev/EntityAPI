If you feel that a specific feature or issue is not sufficiently addressed in EntityAPI, feel free to fork the repository and start hacking to your heart's content. We are always open to suggestions and improvements.

If you need any help or want to talk to me, come [join us in IRC - `#entityapi` @ `irc.esper.net`](http://webchat.esper.net/?channels=entityapi&prompt=1).

##Getting Started
To contribute changes to EntityAPI, first off make sure that you have a free [GitHub account](https://github.com/join). If you're familiar with Git and GitHub, skip straight over to my [general expectations](#general-expectations), [coding requirements](#code-requirements) and [Pull Request formatting expectations](#pull-request-formatting).

If you're not familiar with Git or GitHub, use the following guide to get started.
* Fork the repository on GitHub
* Push your code to the forked repo and submit a Pull Request
* Create a branch on your fork where you'll be making your changes, making sure that it is named appropriately
    * Creating a branch is as simple as doing the following:
        * `git branch branchName`
        * Then checkout the new branch with `git checkout branchName`
    * Check for unnecessary whitespace with git diff --check before committing.

##General Expectations
* *Make sure that*:
    * You follow the [coding requirements](#code-requirements) as outlined
    * Your commit messages are in the [appropriate format](#commit-messages)
    * Your Pull Request is formatted appropriately, according to our [guidelines](#pull-request-formatting).
    * Your contributions are **TESTED** and work as expected.

##Code Requirements
General guidelines:
* We generally follow the [Sun/Oracle coding standards](http://www.oracle.com/technetwork/java/javase/documentation/codeconvtoc-136057.html) and format.
* No tabs; only 4 spaces. Eclipse tends to like tabs, so make sure you fix that.
* No 'weird' mid-statement new lines or column limit.
* Any major additions or changes should have documentation provided in the Pull Request
* Imports should be nice and organised.

**Note: Pull Requests will _NOT_ be merged unless they follow these standards.**

### Fields
Field naming generally follows the Sun/Oracle standards, with lowerCamelCase applying to most instances. Hungarian notation should **never** be used

Static and final fields should **always** be UPPERCASE, where spaces are replaced with a "_".

```java
public class MyAwesomeClass {

    private final SuperCoolObject SOME_OBJECT;

    public MyAwesomeClass() {
        this.SOME_OBJECT = new SuperCoolObject();
    }
}
```

### Methods
Methods are always named in lowerCamelCase. 

e.g.
```java
public void doSomethingReallyCool() {
    // Do something cool
}
```

### Classes
Class names must be named using AlphaNumeric chars **_only_** in UpperCamelCase

e.g.
```java
public class MyAwesomeClass {

    public MyAwesomeClass() {
        
    }
}
```

### Other
For anything not specifically mentioned here, see the Oracle Documentation linked above.

##Commit Messages
Commit messages must be appropriate and abide by the following expectations:
* The first line should briefly explain what the commit is achieving and should include any associated ticket numbers from the Issue Tracker. Multiple tickets may be referenced in one commit message. Tickets may be referenced using the following keywords:
    * Fixes #1
* If necessary, the body of the commit message may be used to further elaborate the need for the change addressed in the commit.

##Pull Request Formatting

Pull Requests should contain a brief summary of what is being addressed. _**Pull Requests without a sufficient description will not be accepted (merged).**_