
## Step 5

    - Compare how this modeling solution and the previous
    one match the domain, especially regarding expressiveness
    and scalability.

    - What is the cost (e.g., modeling, code generation) 
    of a new feature for the developer?

    - What about scalability of the modeling paradigm itself?

&nbsp;
&nbsp;
&nbsp;

- It is more convenient to match the domain according
to components (FSM) or functionalities (Producer/Consumers)
and it is more scalable too, as if there had been three states
without composition implementing the product by hand 
would have been very tedious.

- The cost is to write the interface of the new Java classes 
and the corresponding Visitors. It is not costly, and straightforward
to implement.

- The paradigm lacks abstraction: if there are too many functionalities
or components interact in weird ways, the DSL itself will be very redundant.
Higher abstractions would be needed later on.