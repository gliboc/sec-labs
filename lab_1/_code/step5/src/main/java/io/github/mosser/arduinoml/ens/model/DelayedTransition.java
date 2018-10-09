package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.Visitable;
import io.github.mosser.arduinoml.ens.generator.Visitor;
import io.github.mosser.arduinoml.ens.model.Transition;

public class DelayedTransition extends Transition {

	private int delay;

  public DelayedTransition() {}

  public void setDelay(int delay) {
    this.delay = delay;
  }

  public int getDelay() {
    return delay;
  }

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

}
