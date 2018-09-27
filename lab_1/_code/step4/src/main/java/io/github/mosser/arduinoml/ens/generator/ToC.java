package io.github.mosser.arduinoml.ens.generator;

import io.github.mosser.arduinoml.ens.model.*;

public class ToC extends Visitor<StringBuffer> {

	private final static String CURRENT_STATE = "current_state";

	public ToC() {
		this.code = new StringBuffer();
		this.headers = new StringBuffer();
	}

	private void c(String s) {
		code.append(String.format("%s\n", s));
	}

	private void h(String s) {
		headers.append(String.format("%s\n", s));
	}

	@Override
	public void visit(App app) {
		c("// C code generated from an object model");
		c(String.format("// Application name: %s\n", app.getName()));
		c("#include <avr/io.h>");
		c("#include <util/delay.h>");
		c("#include <Arduino.h>");
		c("#include <fsm.h>");
		c("");


		for (Sensor s : app.getSensors()) {
			c(String.format("int %s_flag = LOW;", s.getName()));
		}
		c("");

		for (Sensor s : app.getSensors()) {
			c(String.format("void switch_%s() {", s.getName()));
			c(String.format("  %s_flag = !%s_flag;", s.getName(), s.getName()));
			c("}\n");
		}

		// SETUP
		c("void setup(){");
		c("  interrupts();");
		for (Actuator a : app.getActuators()) {
			a.accept(this);
		}

		for (Sensor s : app.getSensors()) {
			c(String.format("  pinMode(%d, INPUT_PULLUP); // %s [Sensor]", s.getPin(), s.getName()));
			c(String.format("  attachInterrupt(digitalPinToInterrupt(%d), switch_%s, CHANGE);",
				s.getPin(), s.getName()));
		}
		c("}\n");
		
		Fsm funion = app.getFsm().get(0);

		for (Fsm fsm : app.getFsm()) {
			funion = funion.fusion(funion, fsm);
		}

		for (State state : funion.getStates()) {
			h(String.format("void state_%s();", state.getName()));
			state.accept(this);
		}
		c("");

		if (app.getInitial() != null) {
			c("int main(void) {");
			c("  setup();");
			c(String.format("  state_%s();", app.getInitial().getName()));
			// c("  return 0;");
			c("}");
		}
	}

	@Override
	public void visit(Actuator actuator) {
		c(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
	}

	@Override
	public void visit(Sensor sensor) {
		c(String.format("  pinMode(%d, INPUT); // %s [Sensor]", sensor.getPin(), sensor.getName()));
	}

	@Override
	public void visit(State state) {
		c(String.format("void state_%s() {", state.getName()));

		for (Action action : state.getActions()) {
			action.accept(this);
		}

		for (Transition transition : state.getTransitions()) {
			transition.accept(this);
		}

		c("}");
	}

	@Override
	public void visit(Action action) {
		for (int i = 0; i < action.getActuators().size(); i++) { 
			int pin = action.getActuators().get(i).getPin();
			SIGNAL value;

			if (action.getValues().size() < action.getActuators().size()) {
				value = action.getValue();
			}
			else {
				value = action.getValues().get(i);
			}
			
			c(String.format("  digitalWrite(%d,%s);", pin, value));
		}
	}

	@Override
	public void visit(Transition transition) {
		c(String.format("  state_%s();", transition.getTarget().getName()));
	}

	@Override
	public void visit(DelayedTransition transition) {
		c(String.format("_delay_ms(%d);", transition.getDelay()));
		c(String.format("  state_%s();", transition.getTarget().getName()));
	}

	@Override
	public void visit(SensorTransition transition) {
		Sensor sensor = transition.getSensor();
		String flagName = String.format("%s_flag", sensor.getName());
		// c(String.format(" if(digitalRead(%d) == %s) {", sensor.getPin(),
		// transition.getValue()));
		// c(String.format(" state_%s();", transition.getTarget().getName()));
		// c(" }");
		c(String.format("  if (%s == %s) {", flagName, transition.getValue()));
		c(String.format("    %s = !%s;", flagName, flagName));
		c(String.format("    state_%s();", transition.getTarget().getName()));
		c("  }");
		
	}

}
