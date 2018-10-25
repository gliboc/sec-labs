(* ---------------------- *)

(* let () =
  begin
    let file = ref None in
    let speclist = [] in
    Arg.parse speclist (fun x -> file := Some x) "enter metamodel filename";
    match !file with
    | Some name -> print_endline name
    | None -> ()
  end *)

(* ---------------------- *)

type signal = Low | High
[@@deriving show]

type primitive_expr =
  | Int of int 
  | Sig of signal
[@@deriving show]

type identifier = string
[@@deriving show]

type atomic_type =
  | TInt
  | TCustom of identifier
[@@deriving show]

type types =
  | TAtom of atomic_type
  | TArrow of types * types
  | TArray of atomic_type
[@@deriving show]

(* truc : bidule -> machin *)
type typesig = identifier * types
[@@deriving show]

(*
 * type Machin = Int 
 * type Machin = Low | High
 * type Machin = Set(1, 2, 3, 4)
 *)
type type_decl =
  | TAlias of identifier * types
  | TUnion of identifier * (identifier list)
  | TSet of identifier * (primitive_expr list)
[@@deriving show]


(*
 * type truc = truc
 * class truc { defs }
 * union truc {}
 *)
type component = {
  name : identifier ;
  register_sig : typesig ;  
  } [@@deriving show,make]
  (* | CType of identifier * type_decl
  | CClass of identifier * definition list
  | CUnion of component * component  *)

type constructor = identifier * (identifier * identifier) list
[@@deriving show]

type term = 
  | Component of component
  | TypeDecls of type_decl list
  | Couple of term * term
[@@deriving show]

(* let parse file =  *)
(* let ( *> ) param1 param2 =
    calc_something param1 param2

module L1 = struct
  let is_space =
    function | ' ' | '\t' -> true | _ -> false

  let is_eol =
    function | '\r' | '\n' -> true | _ -> false

  let is_digit =
    function | '0' .. '9' -> true | _ -> false

  let parens p = char '(' *> p <* char ')'
  let bracketed p = char '[' *> p <* char ']'
  let square_bracketed p = char '[' *> p <* char ']'
  let integer = take_while1 is_digit >>| int_of_string
end
 *)
