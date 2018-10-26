

## Step 7 

    - Who is the intended user ? What about the tooling
    associated to the language.

    - More generally, what is the cost of such an approach ?

    - To what extent is the language fragile to the introduction
    of new features ?

    - What is the relation ship between the meta-model and 
    the grammar ?

    - How to validate that the defined syntax is the 
    right one ?

&nbsp;
&nbsp;
&nbsp;

- The user can be anyone targetted by the person who designed 
this language. However, the tooling requires a tech-savy person
who is experienced in one or two domains:
computer science, in order to write a parser and some code 
generation, and the target domain where this language will be used.
This could also be several persons gathering their knowledge together.

- The cost of this approach requires to think about a usable and 
useful lanugage syntax for manipulating the targetted domain.
This is mostly a reflexion so it is hard to quantify it. After that,
the implementation needs to be written entirely before any work is done.
This last part is straightforward and a bit redundant, but it can be 
lenghty too.
So this approach starts slow, which is to be put in balance with the
time gained by using a DSL in the long term.

- Quite fragile because the parser has to be modifier, which
exposes to shift/reduce conflics and other joys. There is no guarantee
that a small language extension that may appear in a lot of places does
not greatly raise the difficulty of the parsing.

- The only relationship is in the mind of the person who implemented 
the parser. This, with the redundant feeling of the implementation of 
the parser, led us to consider a more tight bound between the meta-model
and the grammar: what if the meta-model defined partially or entirely the 
grammar ?

- One way is to evaluate this is to empirically measure how long it is to 
do a usual domain task in this new DSL. Other qualities of GPL apply as 
well: readability, less surprisingness...