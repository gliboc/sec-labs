package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.Visitable;
import io.github.mosser.arduinoml.ens.generator.Visitor;
import io.github.mosser.arduinoml.ens.model.Transition;

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
