package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.*;

import java.util.ArrayList;
import java.util.List;

public class App implements NamedElement, Visitable {

	private String name;
	private List<Actuator> actuators = new ArrayList<Actuator>();
	private List<Sensor> sensors = new ArrayList<Sensor>();
	private List<Fsm> fsm = new ArrayList<Fsm>();
	// private List<State> states = new ArrayList<State>();
	private State initial;

  public App() {}

  public App(String name) {
    this.name = name;
  }

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	public List<Actuator> getActuators() {
		return actuators;
	}


	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setBricks(List<Actuator> actuators) {
		this.actuators = actuators;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}

	// public List<State> getStates() {
	// 	return states;
	// }

	// public void setStates(List<State> states) {
	// 	this.states = states;
	// }

	public void setFsm(List<Fsm> fsm) {
		this.fsm = fsm;
	}
	
	public List<Fsm> getFsm() {
		return fsm;
	}

	public State getInitial() {
		return initial;
	}

	public void setInitial(State initial) {
		this.initial = initial;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
