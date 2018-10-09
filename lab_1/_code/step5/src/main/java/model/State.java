package model;

import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class State implements NamedElement, Visitable {

	private String name;
	private List<Action> actions = new ArrayList<Action>();
	private List<Transition> transitions = new ArrayList<Transition>();
	private State next;

  public State() {}

  public State(String name) {
    this.name = name;
  }

	@Override public String getName() {
		return name;
	}

	@Override public void setName(String name) {
		this.name = name;
	}

	public List<Action> getActions() {
		return actions;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}




	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public void addTransition(String name, Transition transition) {
		
	}

	public void addTransition(Transition transition) {
		this.transitions.add(transition);
	}

	public State getNext() {
		return next;
	}

	public void setNext(State next) {
		this.next = next;
	}

	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
}
