let rec f n =
  match n with
  | 0 -> 1
  | _ -> n * f (n-1)


let rec repl debug () =
  let _ = print_string "|> " in
  let stream = Lexing.from_string (read_line ()) in
  let _ =
    let query = Parser.main Lexer.token stream in
    begin
    if debug then Printf.printf "Your query: %s\n" (Ast.show_app query)
    end
  in repl debug ()


let treat file = 
  let ic = open_in file in 
  let stream = Lexing.from_channel ic in 
    let query = Parser.main Lexer.token stream in 
    begin
    if true then Printf.printf "Your query: %s\n" (Ast.show_app query)
    end;
    Generator.write_code query


(* let _ = repl true () *)
let _ = treat "led.dsl"

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