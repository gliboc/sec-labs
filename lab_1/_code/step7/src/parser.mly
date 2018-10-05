%token APPLICATION
%token ACTUATOR
%token <int> LOCATION SIGNAL
%token STATE
%token ACTION
%token NEXT
%token INITIAL
%token COLON
%token <string> ID

%start main
%type <Ast.t> main

%% 

main:
    | q = app EOF                                       { q }

app:
    | APPLICATION as=actuators s=states    { App( as, s ) }

actuators:
    |                                                   { [] }
    | a=actuator                                        { [a] }
    | a=actuator EOL as=actuators                       { a :: as }

actuator:
    | ACTUATOR id=ID COLON pin=LOCATION                 { (id, pin) }

states:
    | s=state                                           { [s] }
    | s=state EOL ss=states                             { s :: ss }

state:
    | INITIAL name=ID '{' acts=actions n=next '}'       { Init (name, actions, n) }
    | name=ID '{' acts=actions n=next '}'               { State (name, actions, n) }

actions:
    | a=action                                          { [a] }
    | a=action EOL as=actions                           { a :: as }

action:
    | receiver=ID 'is' value=SIGNAL                     { Action (receiver, value) }