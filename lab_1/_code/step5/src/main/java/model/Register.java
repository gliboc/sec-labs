package model;

import generator.Visitable;
import generator.Visitor;

public class Register implements Visitable {

    private String name;
    private Boolean value;

    public Register() {}

    public Register(String name, Boolean value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}