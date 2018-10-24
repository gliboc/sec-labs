open Angstrom



(* ---------------------- *)

let () =
  begin
    let file = ref None in
    let speclist = [] in
    Arg.parse speclist (fun x -> file := Some x) "enter metamodel filename";
    match !file with
    | Some name -> print_endline name
    | None -> ()
  end

(* ---------------------- *)




type identifier = string

type atomic_type =
  | TInt
  | TCustom of identifier

type typesig =
  | TAtom of atomic_type
  | TArrow of typesig * typesig

(* truc : bidule -> machin *)
type definition = identifier * typesig

(*
 * type Machin = Int 
 * type Machin = Low | High
 * type Machin = Set(1, 2, 3, 4)
 *)
type type_decl =
  | TAlias of typesig
  | TUnion of identifier list
  | TSet of expr list


(*
 * type truc = truc
 * class truc { defs }
 * union truc {}
 *)
type component =
  | CType of identifier * type_decl
  | CClass of identifier * definition list
  | CUnion of 

type constructor = identifier * (identifier * identifier) list

let parse file = 

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

