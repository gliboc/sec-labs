package model;

import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;




public class SetActuator implements Visitable, NamedElement, Action {

    private String name;
    private Register guard;
    private List<Actuator> target = new ArrayList<Actuator>();
    private SIGNAL value;

    public SetActuator() {}

    public SetActuator(Register guard, List<Actuator>  target, SIGNAL value) {
        this.guard = guard;
        this.target = target;
        this.value = value;
    }

    public SetActuator(List<Actuator>  target, SIGNAL value) {
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

	public List<Actuator>  getTarget() {
		return target;
	}

	public void setTarget(List<Actuator>  target) {
		this.target = target;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
    }
    

}
