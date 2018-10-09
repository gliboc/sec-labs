package model;

import generator.Visitable;
import generator.Visitor;
import model.Transition;

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
