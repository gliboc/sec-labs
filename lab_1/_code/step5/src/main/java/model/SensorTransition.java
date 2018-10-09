package model;

import generator.Visitable;
import generator.Visitor;
import model.Transition;

public class SensorTransition extends Transition {

	private SIGNAL value;
	private Sensor sensor;

  public SensorTransition() {}

  public void setValue(SIGNAL value) {
    this.value = value;
  }

  public SIGNAL getValue() {
    return value;
  }

	public void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}

	public Sensor getSensor() {
		return sensor;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
