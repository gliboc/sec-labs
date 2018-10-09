# Step 3

*Beforehand*: When we did this step, the code proposed was using the 
Producer/Consumer implementation of a finite state machine.
Even though it seemed a bit contradictory to the proposed model,
after thinking about it we figured that we could see this the Producer/Consumer
as an implementation to finite-state-automata and implemented it this way.
The questions that follow have therefore been answered with the Producer/Consumer
approach in mind (ii), and then answered with the functional model (i).


i) The code in `functional-version` is a re-implementation of this step, using 
the functional paradigm where states call each others. We tweak this a little
by accepting that a state calls itself with different arguments, instead of
defining all different states as separate functions.

ii) The code in `producer-consumer-version` is that first implementation. 
Since it comes second conceptually, we denote it with ii).


&nbsp;

    - Does introducing a convention solve the readability issue?
    
    - How to extend an app with a new feature? 
      Does the approach prevent one to perform invasive changes 
      in the existing behavior to introduce a new one?
    
    - How to extend the code so that to support new features, e.g., 
      memory-less tasks, state-full tasks, different frequencies?

&nbsp;
&nbsp;
&nbsp;

- i) The readability improves in the part of the state functions because 
  the behaviour can now be immediately identified as a state-machine 
  is now immediate to see - at least for someone who is used to using these.
  However, the setup steps and different functions manipulating the led and 
  the 7seg are difficult to understand without context.
- ii) The concept of finite state machine helps to understand easily the states of the components,
  but the implementation, with FLAGS in order to run different FSM in parallel, makes
  the code hard to comprehend.
- i) We can either add one state and transitions to it, which is quite non-invasive
  and easy to make. But more probably, it will be necessary to modify most of 
  the states in order to introduce new features - which will be invasive and 
  might break previous behaviour.
- ii) In order to add new features, it is easy to add new corresponding `consumer` and `producer`
  functions and just add them to the execution loop. The separation between inputs (consumers) and 
  actions (producers) allows to perform changes to previous behaviors in a relatively safe way.
- i) Memory-less tasks require to add a similar branching option at the end of every
  state function, which branches to the state executing the memory-less task when triggered.
  This is painful, because every state needs to have this piece of code.
  It is possible to pass an argument `state` at every branching which would hold
  several state variables; and use it to execute state-full tasks.
  Different frequencies are a bit tricky. However, it is possible to implement 
  them if by using a single timer, and by passing a state containing timestamps
  of the last transition of every single finite-state-automaton being executed.
  This approach will be implemented (and more thoroughly described) in step4.
- ii) It is possible and easy to simply add new flags and message producers / consumers and 
  run them in parallel to the other FSMs. This covers the case of memory-less tasks 
  (no FLAGS) and state-full tasks (use all defined FLAGS). The case of different frequencies
  does not seem obvious to implement, because the algorithm which runs all the FSMs in 
  parallel is a simple `while loop` i.e. there is no real concept of temporality inside
  our code except for the `delay` between each loop. In order to unify FSMs running on 
  different frequencies, a temporal model, one with timers, may be required.

