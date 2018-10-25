let query : string -> unit = 
    fun file ->
    let ic = open_in file in 
    let stream = Lexing.from_channel ic in 
    let query = Parser.main Lexer.token stream in 
    print_endline (AstL1.show_term query)

let debug = true
let _ = try
        query Sys.argv.(1)
        with e ->
            raise e