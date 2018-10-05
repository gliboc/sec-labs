type app = App 

type actuator = string * int
type actuators = actuator list

type action = Action of string * int
type actions = action list

type state = Init of string * actions * state
type states = state list
