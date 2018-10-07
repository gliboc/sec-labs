
open Ast 
let oc : out_channel = open_out "../output/main.c"
let code : string Queue.t = Queue.create ()
let c : string -> unit = fun s -> Queue.add s code

exception Actuator_not_found

let rec find_actuator : actuators -> string -> (actuator, exn) result = fun acts name ->
    match acts with
        | [] -> Error Actuator_not_found
        | a :: tl -> if a.name = name then Ok a else find_actuator tl name 
        

let rec gen : t -> unit = fun t ->
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

        gen_states actuators states;
        
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

and gen_states : actuators -> states -> unit = fun actuators -> function
    | [] -> ()
    | s :: states -> gen_state actuators s ; gen_states actuators states

and gen_state : actuators -> state -> unit = fun actuators s ->
        c(Printf.sprintf "void state_%s() {" s.name);
        gen_actions actuators s.actions;
		c("  _delay_ms(1000);");
		c(Printf.sprintf "  state_%s();" s.next.name);
		c("}");

and gen_actions : actuators -> actions -> unit = fun actuators -> function 
    | [] -> ()
    | action :: actions -> gen_action actuators action ; gen_actions actuators actions 

and gen_action : actuators -> action -> unit = fun actuators action ->
    match find_actuator actuators action.actuator with
    | Ok actuator -> 
        c(Printf.sprintf "  digitalWrite(%d,%s);" actuator.pin (string_of_signal(action.value)))
    | Error e -> raise e

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
let switchOn : action = {actuator="led"; value=High; values=[]}
let switchOff : action = {actuator="led"; value=Low; values=[]}
let rec on : state = {name="on"; next=off; actions=[switchOn]}
and off : state = {name="off"; next=on; actions=[switchOff]}
let app : term = App ("test", [led], on, [on;off])

let () = write_code app