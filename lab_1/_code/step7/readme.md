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

Finally, this straightforward execution can be abstracted into the 
meta-model of DSL generation, which we tried to sum up in this UML 
diagram - this time, handwritten ! Usually, UML diagram which contains
all the informations on a system are difficult to explore. But having
different UML diagrams for each abstraction level does not seem so bad.

![A meta-model of DSL generation](http://github.com/gliboc/sec-labs/lab_1/figs/metadsl.png)


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