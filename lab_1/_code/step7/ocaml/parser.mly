%{
open Ast
%}

%token APPLICATION
%token ACTUATOR
%token <int> LOCATION SIGNAL
%token INITIAL
%token COLON
%token <string> ID
%token LBRACKET RBRACKET
%token IS
%token EOF EOL

%start main
%type <Ast.t> main

%% 

main:
    | q = app  EOF                                    { q }

app:
    | APPLICATION name=ID actuas=actuators i=initial s=states    { App ( name, actuas, i, i :: s ) }
 
actuators:
    |                                                   { [] }
    | a=actuator                                        { [a] }
    | a=actuator EOL actuas=actuators                       { a :: actuas }

actuator:
    | ACTUATOR id=ID COLON pin=LOCATION                 { make_actuator id pin }

states:
    | s=state                                           { [s] }
    | s=state EOL ss=states                             { s :: ss }


initial:
    | INITIAL name=ID LBRACKET acts=actions next=ID RBRACKET  { make_state name acts next }

state:
    | name=ID LBRACKET acts=actions next=ID RBRACKET          { make_state name acts next }


actions:
    | a=action                                               { [a] }
    | a=action EOL acts=actions                                { a :: acts }

action:
    | receiver=ID IS value=SIGNAL                            { (receiver, value) }