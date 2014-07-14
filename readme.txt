
Vise - (c) 2006 Michael Feathers 

This is an alpha release.  I'll have more notes and examples shortly.

The best way to understand Vise is to run ViseExampleTest in the vise.examples 
package.  If you are having any trouble running that test, make sure that you 
have write access to your current working directory.  Currently, Vise writes 
all of its .vise files to that location.  

ViseExampleTest gives you the template that you need to write your own 
Vise-enabled tests.  You need to override setUp and tearDown in your testcase 
like this:


public class ViseExampleTest extends TestCase {
	protected void setUp() {
		Vise.openSection(getClass().getName() + "." + getName());
	}
	
	protected void tearDown() {
		Vise.closeSection();
	}
	...	
}

It isn't strictly necessary to use getClass and getName in the call to 
Vise.openSecton, however, Vise needs to create a file for each method.  Using 
the class name and test method name together is a rather easy way to do this.  
The argument passed to openSection should be String which, when appended with 
".vise", conforms to the syntax for file names on your system.


FAQ
---

Question: How do I start over again if I don't like what I've recorded?
--------

Answer:
------

Essentially, there are two ways.  You can call Vise.release() to delete all 
of the .vise files that you have in your working directory, or you can just 
open up your working directory and delete all of them or some of them as you 
see fit.  

On my machine, I have the following files in my working directory after I 
run ViseExampleTest:

examples.ViseExampleTest.testAddItem.vise
examples.ViseExampleTest.testAddAnotherItem.vise

The files are binary, so there isn't you can do with them.  But you can 
choose to delete one or both to record your values.


Question: Why doesn't Vise use Java 5?
--------

Answer: 
------
I compiled in 1.4.2 because I want this capability available to as many 
users as possible.  I'm actually thinking about compiling it for 1.3.1 
in the next release.


Question:  How can I see what the Vise holds? 
-------- 

Answer:
------

There is a method on Vise called inspect.  If you run it in a test, it 
will throw an exception that contains the current contents of the Vise as 
its message string.

This is very convenient for users of Eclipse and other IDEs with built in 
JUnit support. The message just shows up in the test failures window.


Question: I'd like to store vise files to a different location.
--------

Answer:
------

By default, Vise stores vise files to the current working directly.  However, you
can change this by suppling an argument to the FileBasedStore class.
Go to the intialization line in the Vise class:

public static ViseMechanics mechanics = new DefaultViseMechanics(new FileBasedStore());

and alter it:

public static ViseMechanics mechanics = new DefaultViseMechanics(new FileBasedStore("./mydir"));






Answer:
Support, Feedback & Suggestions: mfeathers@objectmentor.com



