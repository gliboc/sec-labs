package model;


import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class Actuator implements NamedElement, Visitable {

    private int pin;
    private String name;

    public Actuator() {}

    public Actuator(String name, int pin) {
        this.name = name;
        this.pin = pin;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
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
