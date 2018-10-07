
type actuator = {
    name: string; 
    pin: int
} [@@deriving show,make]

type actuators = actuator list
[@@deriving show]

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
[@@deriving show]
 
type app = {
    name:string;
    actuators: actuators;
    initial: state;
    states: states
} [@@deriving show,make]








