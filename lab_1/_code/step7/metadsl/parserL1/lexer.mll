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

let var = ['a'-'z']['0'-'9''a'-'z''A'-'Z''_']*'\''* as s 
let cons = ['A'-'Z']['0'-'9''a'-'z''A'-'Z''_']*'\''* as s 

rule token = parse 
    | [' ' '\t' ]               { token lexbuf }    
    | [ '\n' ]              { incr_linenum lexbuf; token lexbuf }
    | "#"                   { incr_linenum lexbuf; token lexbuf }
    | "int"                     { TINT }
    | "type"                    { TYPE }
    | "="                       { EQ }
    | ":"                       { SEMICOLON }
    | ","                       { COMMA }
    | "Set"                     { SET }
    | "{"                       { LBRACE }
    | "}"                       { RBRACE }
    | "|"                       { BAR } 
    | "["                       { LBRACKET }
    | "]"                       { RBRACKET }
    | "("                       { LPAR }
    | ")"                       { RPAR }
    | "class"                   { CLASS }  
    | "->"                      { ARROW }
    | eof                       { EOF }
    | ['0'-'9']* as n           { INT (int_of_string n) }
    | var                       { VAR (s) }
    | cons                      { IDENT (s) }
    | _                         { raise (Bad_token (Lexing.lexeme lexbuf)) }