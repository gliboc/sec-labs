let query : bool -> string -> unit = 
  fun debug file ->
  let ic = open_in file in 
  let stream = Lexing.from_channel ic in 
    let query = Parser.main Lexer.token stream in 
    begin
    if debug then Printf.printf "Your query: %s\n" (Ast.show_app query)
    end;
    Generator.write_code query

let debug = true
let _ = try
          query debug Sys.argv.(1)
        with _ ->
        print_endline "error : provide DSL file as argument"

(* let () = 
    let _ = print_string "Am there" in
    (* Taking the string given as a parameter or the program *)
    let ic = open_in "led.dsl" in 
    let lb = Lexing.from_channel ic in
    (* if you want to parse a file you should write :
       let ci = open_in filename in
       let lb = Lexing.from_channel ci in
    *)
    try
       let _ = Parser.main Lexer.token lb in
       Printf.printf "OK\n"
    with _ -> Printf.printf "Not OK\n" *)