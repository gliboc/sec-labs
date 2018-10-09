package model;

import generator.Visitable;
import generator.Visitor;


public class SetActuator implements Visitable, NamedElement, Action {

    private String name;
    private Register guard;
    private Actuator target;
    private SIGNAL value;

    public SetActuator() {}

    public SetActuator(Register guard, Actuator target, SIGNAL value) {
        this.guard = guard;
        this.target = target;
        this.value = value;
    }

    public SetActuator(Actuator target, SIGNAL value) {
        this.target = target;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setGuard(Register guard) {
        this.guard = guard;
    }

    public Register getGuard() {
        return guard;
    }

	public void setValue(SIGNAL value) {
		this.value = value;
	}

	public SIGNAL getValue() {
		return value;
	}

	public Actuator getTarget() {
		return target;
	}

	public void setTarget(Actuator target) {
		this.target = target;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
