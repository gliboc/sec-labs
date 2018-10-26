package model;


import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Consumer implements NamedElement, Visitable {

    private String name;
    private List<SetActuator> setact = new ArrayList<SetActuator>();
    private List<SetRegister> setreg= new ArrayList<SetRegister>();
    private List<Register> memory = new ArrayList<Register>();

    public Consumer() {}

    public Consumer(String name, List<Register> memory) {
        this.name = name;
        this.memory = memory;
    }

    public List<Register> getMemory() {
        return memory;
    }

    public List<SetActuator> getSetActions() {
        return setact;
    }

    public void setSetActions(List<SetActuator> actions) {
        this.setact = actions;
    }

    public List<SetRegister> getSetRegisters() {
        return setreg;
    }

    public void setSetRegisters(List<SetRegister> actions) {
        this.setreg = actions;
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
