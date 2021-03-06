# ArduinoML Project

  * M2 IF, ENS Lyon
  * Guillaume Duboc, [email](mailto:guillaume.duboc@ens-lyon.fr)
  * Lucas Escot, [email](mailto:lucas.escot@ens-lyon.fr)
  * Version: 2018.10.26

## Description

This repository contains the work we did for the Lab1 of the course [sec-labs](github.com/mosser/sec-labs).
In `lab_1`, the files `readme.md` and `readme.pdf` contain all our answers to the questions.
Each step in `lab1/_code` has a `readme.md` file which contains a description of our work on the step 
and eventually some help in running it. 

Here follows the `readme`'s of the two steps we went further in: steps 4 and 7.

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


## Step 7

In this step we first implemented a parser and code generation for 
a custom DSL suited to our tasks. This operation was a bit tedious, 
especially since some parts - the parser and the code generation - were
repetitive to implement. Furthermore, it seemed brittle as the only 
actual description of our custom, apart from the UML diagram describing 
it, resided in the parser itself.

Therefore, because there was redundancy in the writing of the parser of 
a language, because we wanted to be able to specify a DSL using a
precise language that would help in handling it internally, we embarked 
on writing a chain of meta-tools in order to handle custom DSLs. Especially:
- A specification language L1, which allows to describe the
syntax of a DSL. For example, this language would allow to 
define the Producer/Consumer meta-model, as well as the one with State Machines.
- A parser/lexer for this language, which we call G0. The output of G0 is 
a typed term in OCaml which describes a DSL syntax which we call L2.
It can be used for several purposes:
    - First, to _help the parsing of L2_ by using an abstract parser such
      as Angstrom, with the use of functors. 
    - Second, to _help the code generation_ which we call G2 by specifying
      which terms we should be able to visit and the relation between them. For both these 
      tasks, in our proof of concept here, we are able to define the ".mli" 
      type signatures of the modules that would implement parsing and 
      code generation, but we believe it is possible to go much further.
Because we want to be able to reason and visualize our meta-model,
from this domain specific modeling language L1 we build a projection 
`SeeUML` towards PlantUML diagrams. 
This is a very simple UML diagram produced this way from the `simple.dsl` app.

![Simple UML example](https://github.com/gliboc/sec-labs/blob/master/lab_1/figs/simple.png)

In order to refine it, 
we would need to implement constructs with type with `exists v, P` in our specification
language. If an object has this type, an arrow --|> would appear in the UML
diagram between him and its abstract class. We would implement 
relations too, to have possibly many more relations on the visualization !
The file `simplerarduino.mdsl` show what would be our meta-DSL language eventually,
but it is not supported by our implementation as of now.

Finally, this straightforward execution can be abstracted into the 
meta-model of DSL generation, which we tried to sum up in this UML 
diagram - this time, handwritten ! Usually, UML diagram which contains
all the informations on a system are difficult to explore. But having
different UML diagrams for each abstraction level does not seem so bad.

![A meta-model of DSL generation](https://github.com/gliboc/sec-labs/blob/master/lab_1/figs/metadsl.png)


### Lexer/parser written with Ocamllex and Menhir and code generation

This step is in the directory `ocaml`.
It requires, OCaml, as well as Menhir and Dune (`opam install menhir dune`).

#### File description
    - `parser.mly` and `lexer.mll` : lexer/parser with Menhir
    - `generator.ml` : code generation
    - `main.ml` : main interface
    - `led.dsl` : our application written in the simple custom DSL

Run it with `dune exec ./main.exe led.dsl` to see the code generated in the
`output` directory.




## References

- [Practicing Domain-Specific Languages: From Code to Models](https://hal.archives-ouvertes.fr/hal-01865448/document), L. Gonnord and S. Mosser.

