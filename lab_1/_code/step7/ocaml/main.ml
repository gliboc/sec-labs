let rec f n =
  match n with
  | 0 -> 1
  | _ -> n * f (n-1)

let () = 
  let ic = open_in "led.dsl" in 
  let stream = Lexing.from_channel ic in 
  let query = Parser.main Lexer.token stream
  in Printf.printf "%s" (Ast.show query)