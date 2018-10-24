%{
open Ast
%}

%token APPLICATION
%token ACTUATOR
%token <int> LOCATION
%token INITIAL
%token <string> ID IDCOLON
%token LBRACKET RBRACKET
%token IS
%token EOL EOF
%token LOW HIGH
%token GOTO

%start main
%type <Ast.app> main

%% 

main:
    | APPLICATION n=ID LBRACKET 
        a=actuators i=initial s=states 
        RBRACKET EOF                                         { make_app ~name:n ~actuators:a ~initial:i ~states:(i :: s) }

actuators:
    | a=actuator                                             { [a] }
    | a=actuator EOL actuas=actuators                        { a :: actuas }

actuator:
    | ACTUATOR id=IDCOLON pin=LOCATION                       { make_actuator ~name:id ~pin:pin }

states:
    | s=state                                                { [s] }
    | s=state ss=states                                      { s :: ss }


initial:
    | INITIAL s=state                                        { s }

state:
    | name=ID LBRACKET acts=actions GOTO next=ID RBRACKET    { make_state ~name:name ~actions:acts ~next:next }


actions:
    | a=action                                               { [a] }
    | a=action EOL acts=actions                              { a :: acts }

action:
    | r=ID IS v=signal                                       { make_action ~actuator:r ~value:v ~values:[] () }

signal:
    | LOW                                                    { Low }
    | HIGH                                                   { High }