let rec f n =
  match n with
  | 0 -> 1
  | _ -> n * f (n-1)

let () = 
  print_endline @@ "Hello, World!";
  print_char 'a';
  (* let (x : bytes) = "101010" in print_bytes x; *) 
  (* Note : bytes are mutable strings from newer versions*)
  print_float 0.1;
  print_int 2;
  print_string "309FIZJID";
  print_newline ();
  prerr_int 0;
  print_int @@ f 20;
  print_newline ()