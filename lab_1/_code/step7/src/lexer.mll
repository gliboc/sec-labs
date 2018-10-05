{
    open Parser
    exception Bad_token of string
}

rule token = parse 
    | [' ' '\t' '\n' ]  { token lexbuf }
    | "application"     { APPLICATION }
    | "actuator"        { ACTUATOR }
    | "->"              { INITIAL }
    | eof        { EOF }
    | _          { raise (Bad_token (Lexing.lexeme lexbuf)) }

and read_string buf = parse
  | '"'        { STRING (Buffer.contents buf) }
  | "\\\\"     { Buffer.add_char buf '\\'; read_string buf lexbuf }
  | "\\t"      { Buffer.add_char buf '\t'; read_string buf lexbuf }
  | "\\\""     { Buffer.add_char buf '"'; read_string buf lexbuf }
  | "\\r"      { Buffer.add_char buf '\r'; read_string buf lexbuf }
  | "\\n"      { Buffer.add_char buf '\n'; read_string buf lexbuf }
  | [^ '"' '\\' ] {Buffer.add_string buf (Lexing.lexeme lexbuf); read_string buf lexbuf }
  | _          { raise (Bad_token ("Illegal string character: " ^ Lexing.lexeme lexbuf)) }
  | eof        { raise (Bad_token ("String not terminated")) }
