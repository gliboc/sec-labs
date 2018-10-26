package generator;

import model.*;

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

		for (Register reg : app.getRegisters()) {
			c(String.format("%s = false;", reg.getName()));
		}
		c("");

		for (Producer prod : app.getProducers()) {
			prod.accept(this);
		}

		c("void turnOff() {");
		c("  for (int a = 1; a<=8) {");
		c("    digitalWrite(a, HIGH);");
		c("  }");
		c("}");
		c("");

		c("void displayDigit(int digit)");
		c("{");
		c("  turnOff();");
		c("  if(digit!=1 && digit != 4)");
		c("	   digitalWrite(1,LOW);");
		c("  if(digit != 5 && digit != 6)");
		c("	   digitalWrite(2,LOW);");
		c("  if(digit !=2)");
		c("    digitalWrite(3,LOW);");
		c("  if(digit != 1 && digit !=4 && digit !=7)");
		c("	   digitalWrite(4,LOW);");
		c("  if(digit == 2 || digit ==6 || digit == 8 || digit==0)");
		c("	   digitalWrite(5,LOW);");
		c("  if(digit != 1 && digit !=2 && digit!=3 && digit !=7)");
		c("	   digitalWrite(6,LOW);");
		c("  if (digit!=0 && digit!=1 && digit !=7)");
		c("	   digitalWrite(7,LOW);");
		c("}");

		// c("unsigned long previousMillis = 0;\n"); 

		// for (Sensor s : app.getSensors()) {
		// 	c(String.format("int %s_flag = LOW;", s.getName()));
		// }
		// c("");

		// for (Sensor s : app.getSensors()) {
		// 	c(String.format("void switch_%s() {", s.getName()));
		// 	c(String.format("  %s_flag = !%s_flag;", s.getName(), s.getName()));
		// 	c("}\n");
		// }

		c("// ########## INITIALIZATION #############\n");

		// SETUP
		c("void setup() {");
		c("  interrupts();");
		for (Actuator a : app.getActuators()) {
			a.accept(this);
		}
		c("}\n");

		// for (Sensor s : app.getSensors()) {
		// 	c(String.format("  pinMode(%d, INPUT_PULLUP); // %s [Sensor]", s.getPin(), s.getName()));
		// 	c(String.format("  attachInterrupt(digitalPinToInterrupt(%d), switch_%s, CHANGE);",
		// 		s.getPin(), s.getName()));
		// }
		// c("}\n");

		c("// ##### BOOLEAN REGISTERS \n");

		for (Register register: app.getRegisters()) {
			this.visit(register);
		}

		c("// ##### ELECTRONICS AND LOGICAL COMPONENTS\n");
		

		
	}

	@Override
	public void visit(Actuator actuator) {
		c(String.format("  pinMode(%d, OUTPUT); // %s [Actuator]", actuator.getPin(), actuator.getName()));
	}

	// @Override
	// public void visit(State state) {
	// 	c(String.format("void state_%s() {", state.getName()));

	// 	for (Action action : state.getActions()) {
	// 		action.accept(this);
	// 	}

	// 	for (Transition transition : state.getTransitions()) {
	// 		transition.accept(this);
	// 	}
	// 	c(String.format("  state_%s();", state.getName()));
	// 	c("}");
	// }

	@Override
	public void visit(SetActuator action) {
			
			SIGNAL value = action.getValue();
			Register guard = action.getGuard();

			if (guard != null) {
				c(String.format("  if (%s) {", guard.getName()));

				for (Actuator act : action.getTarget()) {
					int pin = act.getPin();
					c(String.format("    digitalWrite(%d,%s);", pin, value));
				}
				
				c("  }");
			}
	}


	// @Override
	// public void visit(Transition transition) {
	// 	c(String.format("  state_%s();", transition.getTarget().getName()));
	// }

	// @Override
	// public void visit(DelayedTransition transition) {
	// 	c("  unsigned long currentMillis = millis();");
	// 	c(String.format("  if ((unsigned long)(currentMillis - previousMillis) >= %s) {", transition.getDelay()));
	// 	c("    previousMillis = currentMillis;");
	// 	c(String.format("    state_%s();", transition.getTarget().getName()));
	// 	c("  }");
	// }

	// @Override
	// public void visit(SensorTransition transition) {
	// 	Sensor sensor = transition.getSensor();
	// 	String flagName = String.format("%s_flag", sensor.getName());
	// 	// c(String.format(" if(digitalRead(%d) == %s) {", sensor.getPin(),
	// 	// transition.getValue()));
	// 	// c(String.format(" state_%s();", transition.getTarget().getName()));
	// 	// c(" }");
	// 	c(String.format("  if (%s == %s) {", flagName, transition.getValue()));
	// 	c(String.format("    %s = !%s;", flagName, flagName));
	// 	c(String.format("    state_%s();", transition.getTarget().getName()));
	// 	c("  }");
		
	// }
	@Override 
	public void visit(Register register) {
		c(String.format("boolean %s = %s", register.getName(), register.getValue()));
	}

	@Override 
	public void visit(Producer producer) {
		producer.getTarget().accept(this);		
	}

	@Override 
	public void visit(Action action) {}

	@Override 
	public void visit(SetRegister setreg) {}



	@Override 
	public void visit(Consumer consumer) {
		c("// ####### MESSAGE FLAG NETWORK #######\n");
		c("// Message flags\n");

		for (Register reg : consumer.getMemory()) {
			this.visit(reg);
		}
		c("\n");
		
		c(String.format("void %s_push() {", consumer.getName()));
		for (SetActuator act : consumer.getSetActions()) {
			c(String.format("  if (digitalRead(%s) == HIGH) {", act.getGuard().getName()));
			c(String.format("%s = true;", act.getName()));
		}
	}
}
