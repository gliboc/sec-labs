let query  = 
    fun file ->
    let ic = open_in file in 
    let stream = Lexing.from_channel ic in 
    let query = Parser.main Lexer.token stream in 
    begin
    print_endline (AstL1.show_term query);
    query
    end

let debug = true
let _ = try
        query Sys.argv.(1)
        with e ->
            raise e