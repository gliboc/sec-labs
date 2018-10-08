{
  open Parser  
  exception Bad_token of string

let incr_linenum lexbuf =
    let pos = lexbuf.Lexing.lex_curr_p in
    lexbuf.Lexing.lex_curr_p <- {pos with
        Lexing.pos_lnum = pos.Lexing.pos_lnum + 1;
        Lexing.pos_bol = pos.Lexing.pos_cnum;
    }
}

rule token = parse 
    | [' ' '\t' ]                                           { token lexbuf }    
    | [ '\n' ]                                              { incr_linenum lexbuf; token lexbuf }
    | "application"                                         { APPLICATION }
    | "{"                                                   { LBRACKET }
    | "}"                                                   { RBRACKET }
    | "is"                                                  { IS }
    | "actuator"                                            { ACTUATOR }
    | "->"                                                  { INITIAL }
    | "low" | "LOW"                                         { LOW }
    | "high" | "HIGH"                                       { HIGH }
    | "goto"                                                { GOTO }
    | eof                                                   { EOF }
    | ['0'-'9']* as n                                       { LOCATION (int_of_string n) }
    | ['a'-'z']['0'-'9''a'-'z''A'-'Z''_']*'\''* +':' as s   { IDCOLON (List.hd (String.split_on_char ':' s)) }
    | ['a'-'z']['0'-'9''a'-'z''A'-'Z''_']*'\''* as s        { ID (s) }
    | _                                                     { raise (Bad_token (Lexing.lexeme lexbuf)) }