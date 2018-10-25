%{
open AstL1
%}

%token APPLICATION
%token ACTUATOR
%token <int> INT
%token <AstL1.signal> SIGNAL
%token <string> IDENT VAR
%token COMMA SEMICOLON
%token LBRACE RBRACE LBRACKET RBRACKET LPAR RPAR
%token SET TINT
%token LOW HIGH
%token ARROW BAR EQ
%token EOF
%token TYPEDECL CLASS TYPE

%start main
%type <AstL1.term> main

%% 

main:
    | t=term EOF                           { t }
    | t1=term m=main                    { Couple (t1, m) }

term: 
    | ts=type_decls                     { TypeDecls ts }
    | c=component                       { Component c }         


type_decls:
    | t=type_decl                       { [t] }
    | t=type_decl ts=type_decls         { t :: ts }

type_decl:
    | TYPE id=IDENT EQ t=types          { TAlias (id, t) }
    | TYPE id=IDENT EQ t=type_union       { TUnion (id, t) }
    | TYPE id=IDENT EQ 
        SET LPAR ps=primitive_exprs RPAR  { TSet (id, ps) }

types:
    | LBRACKET a=atomic_type RBRACKET   { TArray a }
    | a=atomic_type                     { TAtom a }
    | t1=types ARROW t2=types           { TArrow (t1, t2) }                                

atomic_type:
    | TINT                              { TInt }
    | s=IDENT                           { TCustom s }

type_union:
    | id=IDENT                          { [id] }
    | BAR? id=IDENT BAR ids=type_union          { id :: ids }

primitive_exprs:
    | p=primitive_expr                             { [p] }
    | p1=primitive_expr COMMA ps=primitive_exprs   { p1 :: ps }

primitive_expr:
    | i=INT                                { Int i }
    | s=SIGNAL                             { Sig s }

component:
    | CLASS n=IDENT LBRACE
        t=typesig RBRACE   { make_component ~name:n ~register_sig:t }

typesig:
    | v=VAR SEMICOLON t=types      { (v, t) }




