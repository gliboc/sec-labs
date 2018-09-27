package io.github.mosser.arduinoml.ens.model;

import io.github.mosser.arduinoml.ens.generator.Visitable;
import io.github.mosser.arduinoml.ens.generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Fsm implements NamedElement, Visitable {

    private String name;
    private List<State> states = new ArrayList<State>();
    // private List<Transition> transitions = new ArrayList<Transition>();
    private State initial;

    public Fsm() {
    }

    public Fsm(String name) {
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

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public void setInitial(State state) {
        this.initial = state;
    }

    public State getInitial() {
        return initial;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Fsm fusion(Fsm a1, Fsm a2) {

        if (a1.getName() == a2.getName()) {
            return a1;
        }

        Fsm a = new Fsm(String.format("%s_%s", a1.getName(), a2.getName()));

        State initial = new State(
            String.format("%s_%s", a1.getInitial().getName(), a2.getInitial().getName()));

        List<State> newStates = new ArrayList<State>();

        for (State s1 : a1.getStates()) {
            for (State s2 : a2.getStates()) {
                State s = new State(String.format("%s_%s", s1.getName(), s2.getName()));

                List<Action> actions = new ArrayList<Action>(s1.getActions());
                actions.addAll(s2.getActions());

                s.setActions(actions);
                newStates.add(s);

            }
        }
        a.setStates(newStates);
        
        return a;
    }
}
