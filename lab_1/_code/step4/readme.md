## Step 4

In this step, we tried to push the Finite-State-Machine 
model with states as functions calling themselves to its limits.
In order to do see, we implemented the composition of automaton 
in order to define two Fsm, one for the led and one for the 7seg, 
and run them in parallel. Although we acknowledge the possibility
of different composition operations, we did not implement them 
as we had no use for them here.

Our implementation was made difficult by two desires:
- We wanted to be able to use the Arduino processors' `interrupts` in order
to push our button and have the system react to it instantly.
- We wanted to be able to define different types of transitions:
    - `DelayedTransition.java` is an automaton transition with
    a delay that can be set.
    - `SensorTransition.java` is an event-triggered transition.
    - `Transition.java` in an instantaneous transition.

To have the `interrupts` working, and therefore the button,
working, the button has to be plugged on the PIN2 of the Arduino board due to Arduino internals. 
The branching is defined in `Led.java`.

The code produced is long by design (we wanted to implement
every branching for every state in a transparent way), so the
reasoning and debugging now really takes place only on the meta-model and code generation levels.

![FSM meta-model](https://github.com/gliboc/sec-labs/blob/master/lab_1/figs/model_step4.png)

## How to compile?

```
mvn clean package
```

## How to generate the code from the model?

```
mvn -q exec:java
```

## Use the code

From `output` directory: 

`make` puis `sudo make upload`

