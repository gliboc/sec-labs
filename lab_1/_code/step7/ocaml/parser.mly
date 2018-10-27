%{ open Ast %}

%token APPLICATION
%token ACTUATOR
%token SENSOR
%token IF
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
        a=actuators s=sensors i=initial st=states 
        RBRACKET EOF                                         { make_app ~name:n 
                                                                        ~actuators:a 
                                                                        ~sensors:s
                                                                        ~initial:i 
                                                                        ~states:(i :: st) }

actuators:
    | a=actuator                                             { [a] }
    | a=actuator EOL actuas=actuators                        { a :: actuas }

actuator:
    | ACTUATOR id=IDCOLON pin=LOCATION                       { make_actuator ~name:id ~pin:pin }

sensor:
    | SENSOR id=IDCOLON pin=LOCATION                         { make_sensor ~name:id ~pin:pin }

sensors:
    | s=sensor                                               { [s] }
    | s=sensor EOL ss=sensors                                { s :: ss }

states:
    | s=state                                                { [s] }
    | s=state ss=states                                      { s :: ss }

initial:
    | INITIAL s=state                                        { s }

state:
    | name=ID LBRACKET acts=actions GOTO next=ID RBRACKET    { make_state ~name:name ~actions:acts ~next:next () }
    | name=ID LBRACKET acts=actions IF sensor=ID IS v=signal GOTO inext=ID GOTO next=ID RBRACKET    { make_state ~name:name ~actions:acts ~next:next ~inext:(sensor, v, inext) () }


actions:
    | a=action                                               { [a] }
    | a=action EOL acts=actions                              { a :: acts }

action:
    | r=ID IS v=signal                                       { make_action ~actuator:r ~value:v ~values:[] () }

signal:
    | LOW                                                    { Low }
    | HIGH                                                   { High }
