type actuator = {
    name: string; 
    pin: int
} [@@deriving make]

type actuators = actuator list


type pin = int
type signal = Low | High
[@@deriving show]


type action = {
    actuator: string; 
    value: signal; 
    values: signal list
} [@@deriving show,make]

type actions = action list
[@@deriving show]


type state = {
    name: string; 
    actions: actions; 
    next: string
} [@@deriving show,make]

type states = state list 

 
type t = App of string * actuators * state * states
[@deriving show]