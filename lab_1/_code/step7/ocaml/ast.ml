type actuator = string * int
type actuators = actuator list

type pin = int

type action = string * pin
type actions = action list

type state = Init of string * actions * state
type states = state list
 
type term = App of string * actuators * states
