package model;

import generator.Visitable;
import generator.Visitor;

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
