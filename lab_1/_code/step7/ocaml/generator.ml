
open Ast 
let oc = open_out "test.c"
let code = Queue.create ()
let c s = Queue.add s code


let rec gen : term -> unit = fun t ->
    match t with
    | App (name, actuators, initial, states) -> 

        c("// C code generated from an object model");
		c(Printf.sprintf "// Application name: %s\n" name);
		c("#include <avr/io.h>");
		c("#include <util/delay.h>");
		c("#include <Arduino.h>");
		c("#include <fsm.h>");
		c("");
		c("void setup(){");

        gen_actuators(actuators);
        gen_states(states);
        
		c("int main(void) {");
		c("  setup();");
		c(Printf.sprintf "  state_%s();" initial.name);
		c("  return 0;");
		c("}");

and gen_actuators : actuators -> unit = function 
    | [] -> ()
    | act :: acts -> gen_actuator act ; gen_actuators acts   

and gen_actuator : actuator -> unit = fun actuator ->
    c(Printf.sprintf "  pinMode(%d, OUTPUT); // %s [Actuator]" actuator.pin actuator.name)

and gen_states : states -> unit = function
    | [] -> ()
    | s :: states -> gen_state s ; gen_states states

and gen_state : state -> unit = fun s ->
        c(Printf.sprintf "void state_%s() {" s.name);
        gen_actions(s.actions);
		c("  _delay_ms(1000);");
		c(Printf.sprintf "  state_%s();" s.next.name);
		c("}");

and gen_actions : actions -> unit = function 
    | [] -> ()
    | action :: actions -> gen_action action ; gen_actions actions 

and gen_action : action -> unit = fun action ->
    c(Printf.sprintf "  digitalWrite(%d,%d);" action.actuator.pin (int_of_signal(action.value)))

and int_of_signal : signal -> int = function 
    | Low -> 0
    | High -> 1

