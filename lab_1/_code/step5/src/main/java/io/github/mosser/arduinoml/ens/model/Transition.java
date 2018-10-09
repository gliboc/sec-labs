package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.Visitable;
import io.github.mosser.arduinoml.ens.generator.Visitor;

public class Transition implements Visitable {

  private State target;

  public Transition() {}

  public void setTarget(State target) {
    this.target = target;
  }

	public State getTarget() {
		return target;
	}
	

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
