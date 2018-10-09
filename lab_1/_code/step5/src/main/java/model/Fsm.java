package model;

import generator.Visitable;
import generator.Visitor;

import java.io.PrintStream;
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

    public State findState(String name) {

        System.out.println(String.format("Searching for %s", name));
        for (State s : states) {
            if (s.getName().equals(name)) {
                System.out.println("Found!");
                return s;
            }
        }
        System.out.println("Didn't find the state");
        return new State(); // Should raise an error actually
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
        System.out.println(newStates.size());

        for (State s1 : a1.getStates()) {
            for (State s2 : a2.getStates()) { 
                for (Transition t1 : s1.getTransitions()) {
                    for (Transition t2 : s2.getTransitions()) {
                        if ((t1 instanceof DelayedTransition) && (t2 instanceof DelayedTransition)) {
                            if (((DelayedTransition)t1).getDelay() == ((DelayedTransition)t2).getDelay()) {

                                System.out.println("Adding a DelayedTransition");
                                
                                DelayedTransition t = new DelayedTransition();
                                t.setDelay(((DelayedTransition)t1).getDelay());
                                
                                State target = a.findState(String.format("%s_%s", t1.getTarget().getName(), t2.getTarget().getName()));
                                t.setTarget(target);

                                State s = a.findState(String.format("%s_%s", s1.getName(), s2.getName()));
                                s.addTransition(t);
                            }
                        }

                        if ((t1 instanceof SensorTransition) && (t2 instanceof SensorTransition)) {
                            if (((SensorTransition)t1).getSensor() == ((SensorTransition)t2).getSensor()) {

                                SensorTransition t = new SensorTransition();
                                t.setValue(((SensorTransition)t1).getValue());
                                t.setSensor(((SensorTransition)t1).getSensor());

                                State target = a.findState(String.format("%s_%s", t1.getTarget().getName(), t2.getTarget().getName()));
                                t.setTarget(target);

                                State s = a.findState(String.format("%s_%s", s1.getName(), s2.getName()));
                                s.addTransition(t);

                                System.out.println(String.format("Adding a SensorTransition to %s", s.getName()));

                            }
                        }
                    }
                }
                

            }
        }

        State i1 = a1.getInitial();
        State i2 = a2.getInitial();

        System.out.println("New initial state");
        State newInitial = a.findState(String.format("%s_%s", i1.getName(), i2.getName()));
        a.setInitial(newInitial);
        
        System.out.println("This went well.");
        System.out.println(a.getStates().size());
        return a;
    }
}
