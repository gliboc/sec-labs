open AstL1

let buff = ref []
let c : string -> unit = 
    fun code ->
    buff := !buff @ [Printf.sprintf "%s\n" code]



let rec uml_of_term : term -> unit = function
    | Component c -> uml_of_component c 
    | TypeDecls ts -> 
        begin
        c("class Types {");
        uml_of_type_decls ts;
        c("}\n")
        end
    | Couple (t1, t2) -> (uml_of_term t1) ; (uml_of_term t2) 

and uml_of_component : component -> unit = 
    fun comp -> 
    begin 
    c(Printf.sprintf "class %s {" comp.name);
    uml_of_typesig comp.register_sig;
    c("}\n")
    end

and uml_of_typesig : typesig -> unit = function 
    | (id, t) -> c(Printf.sprintf "  + %s : %s" id @@ str_of_types t)

and str_of_types : types -> string = function 
    | TAtom a -> str_of_at a 
    | TArrow (t1, t2) -> 
        Printf.sprintf "%s -> %s" (str_of_types t1) @@ str_of_types t2 
    | TArray a -> "[" ^ (str_of_at a) ^ "]"

and str_of_at : atomic_type -> string = function 
    | TInt -> "Int"
    | TCustom id -> id

and uml_of_type_decls : type_decl list -> unit = function 
    | [] -> () 
    | d :: tl -> uml_of_type_decl d ; uml_of_type_decls tl 

and uml_of_type_decl : type_decl -> unit = function 
    | TAlias (id, t) -> 
        c(Printf.sprintf "  - type %s = %s" id (str_of_types t))
    | TUnion (id, conss) ->
        c(Printf.sprintf "  - type %s = %s" id (String.concat "|" conss))
    | TSet (id, ps) ->
        c(Printf.sprintf "  - type %s = Set(%s)" id 
            (String.concat "," (List.map str_of_primitive_expr ps)))
        
and str_of_primitive_expr = function 
    | Int i -> string_of_int i 
    | Sig Low -> "Low"
    | Sig High -> "High"



let transfoUML : term -> unit =
    fun term ->
    try let model_file = Sys.argv.(1) in 
        let oc = open_out @@ (List.hd (String.split_on_char '.' model_file)) ^ ".puml" in 
        begin
        c("@startuml\n");
        uml_of_term term;
        c("@enduml");
        let line = ref "" in 
        while !buff != [] do 
            line := List.hd (!buff);
            Printf.fprintf oc "%s" !line;
            buff := List.tl (!buff)
        done
        end
        
    with Invalid_argument x -> 
        print_endline x