package model;


import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements NamedElement, Visitable {

    private String name;
    private List<Action> actions = new ArrayList<Action>();
    private List<Register> memory = new ArrayList<Register>();

    public Consumer() {}

    public Consumer(String name, List<Action> actions, List<Register> memory) {
      this.name = name;
      this.actions = actions;
      this.memory = memory;
    }

    public List<Register> getMemory() {
        return memory;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


}
