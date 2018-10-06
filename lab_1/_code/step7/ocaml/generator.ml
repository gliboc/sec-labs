
open Ast 
let oc = open_out "test.c"
let code = Queue.create ()
let c s = Queue.add s code


let gen t = 
    match t with
    | App (name, actuators, _) -> 
        c("// C code generated from an object model");
		c(Printf.sprintf "// Application name: %s\n" name);
		c("#include <avr/io.h>");
		c("#include <util/delay.h>");
		c("#include <Arduino.h>");
		c("#include <fsm.h>");
		c("");
		c("void setup(){");

