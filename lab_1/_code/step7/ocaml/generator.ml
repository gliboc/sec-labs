
open Ast 
let oc = open_out "../output/main.c"
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
        c("}\n");

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
    c(Printf.sprintf "  digitalWrite(%d,%s);" action.actuator.pin (string_of_signal(action.value)))

and string_of_signal : signal -> string = function 
    | Low -> "SIGNAL.LOW"
    | High -> "SIGNAL.HIGH"

let write_code app =
    gen app;
    if Queue.is_empty code then
        prerr_endline "Error: the code queue was empty"
    else
    while not (Queue.is_empty code) do
        
        let line = Queue.pop code in
        (* print_endline line; *)
            output_string oc line;
            output_string oc "\n"
    done
    

(* test *)

let led : actuator = {name="led"; pin=13}
let switchOn : action = {actuator=led; value=High; values=[]}
let switchOff : action = {actuator=led; value=Low; values=[]}
let rec on : state = {name="on"; next=off; actions=[switchOn]}
and off : state = {name="off"; next=on; actions=[switchOff]}
let app : term = App ("test", [led], on, [on;off])

let () = write_code app