package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.Visitable;
import io.github.mosser.arduinoml.ens.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Action implements Visitable {

	private List<SIGNAL> values = new ArrayList<SIGNAL>();
	private List<Actuator> actuators =  new ArrayList<Actuator>();
	private SIGNAL value;

  public Action() {}

  public Action(List<Actuator> actuators, List<SIGNAL> values) {
    this.actuators = actuators;
    this.values = values;
  }

  public Action(List<Actuator> actuators, SIGNAL value) {
    this.actuators = actuators;
    this.value = value;
  }

	public List<SIGNAL> getValues() {
		return values;
	}

	public void setValues(List<SIGNAL> values) {
		this.values = values;
	}

	public void setValue(SIGNAL value) {
		this.value = value;
	}

	public SIGNAL getValue() {
		return value;
	}

	public List<Actuator> getActuators() {
		return actuators;
	}

	public void setActuator(List<Actuator> actuators) {
		this.actuators = actuators;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
