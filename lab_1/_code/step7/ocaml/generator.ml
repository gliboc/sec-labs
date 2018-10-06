
open Ast 
let oc = open_out "test.c"
let code = Queue.create ()
let c s = Queue.add s code


let rec gen t = 
    match t with
    | App (name, actuators, actions) -> 
        c("// C code generated from an object model");
		c(Printf.sprintf "// Application name: %s\n" name);
		c("#include <avr/io.h>");
		c("#include <util/delay.h>");
		c("#include <Arduino.h>");
		c("#include <fsm.h>");
		c("");
		c("void setup(){");

        gen_actuators(actuators);
        gen_actions(actions);

and gen_actuators = function 
    | [] -> ()
    | act :: acts -> gen_actuator act ; gen_actuators acts   

and gen_actuator act = 
    c(Printf.sprintf "  pinMode(%d, OUTPUT); // %s [Actuator]" (snd act) (fst act));

and gen_actions = function 
    | [] -> ()
    | action :: actions -> gen_action action ; gen_actions actions 

and gen_actions action =
    c(Prinft.sprintf("  digitalWrite(%d,%s);",action.getActuator().getPin(),action.getValue()));


