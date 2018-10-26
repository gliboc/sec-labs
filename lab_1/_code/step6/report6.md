# Step 6

    - Who is the intended user for such a language?

    - What is the cost of reusing this existing DSL for 
    the developer in terms of code?

    - What is the cost of adding a new task of our domain?

    - Was is the cost of adding a new hardware target?

    - The Lustre language comes with its own ecosystem 
    (test, formal verification), what are the generic properties 
    we can imagine to prove from our domain?

&nbsp;
&nbsp;
&nbsp;

<br/><br/>
<br/><br/>


- Lustre is clearly designed for users that want to work at 
the conceptual level of the program. The level of abstraction 
entirely gets rid of the underlying platform we are developing for. 
There are no longer pins, frequencies and signals. 
The behavior is only described in terms of dataflow 
and synchronous programming.

- Because of such an abstract description of application behaviors, code is highly reusable.

- However, the costly part remains bridging the gap between this 
abstract description and the specific implementation for our current 
domain. As have been experienced in this step of the Lab, this process
is quite tedious. Coding the intended high-level behavior in lustre is a 
really smooth experience, whereas glueing the generated C code from lustre 
with the domain-specific code can prove to be a tad painful. 
To be more specific to the question, adding a new task to the domain requires one to:
    - define the new abstract behavior in the lustre code
    - generate the new C code
    - implement the newly defined C routines to take into account the abstract behavior.
    - bridge that with the hardware lib.

- Adding another hardware target does not require one to fiddle with lustre anymore. 
The only thing that needs to be modified is the part of the C library which provides 
an high-level interface to the hardware. My personal assessment is that it is relatively easy to do.

- Working with a language with such an ecosystem can prove to be very useful when you 
are trying to prove properties on the execution of your program. One could for example 
prove that two values are forever alternating, and do this formally, without having to consider hardware.