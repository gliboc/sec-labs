
let () =
    let file = Sys.argv.(1) in 
    let term = L1.Query.query file in 
    L1.SeeUML.transfoUML term
