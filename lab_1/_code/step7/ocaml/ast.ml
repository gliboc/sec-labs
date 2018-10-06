type actuator = {name: string; pin: int}
type actuators = actuator list

type pin = int
type signal = Low | High

type action = {actuator: actuator; value: signal; values: signal list}
type actions = action list

type state = {name: string; actions: actions; next: state}
type states = state list 
 
type term = App of string * actuators * state * states
