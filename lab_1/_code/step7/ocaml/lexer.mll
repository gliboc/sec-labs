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
    | [' ' '\t' ]  { token lexbuf }
    | '\n' {incr_linenum lexbuf; token lexbuf}
 	     	   	           (* token: appel récursif *)
                                   (* lexbuf: argument implicite
                                      associé au tampon où sont
                                      lus les caractères *)
    | '\n'              { EOL }
    | "application"     { APPLICATION }
    | "{"               { LBRACKET }
    | "}"               { RBRACKET }
    | "is"              { IS }
    | "actuator"        { ACTUATOR }
    | "->"              { INITIAL }
    | eof        { EOF }
    | _          { raise (Bad_token (Lexing.lexeme lexbuf)) }