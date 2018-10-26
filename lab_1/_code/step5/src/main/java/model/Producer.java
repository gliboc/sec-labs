package model;

import generator.Visitable;
import generator.Visitor;

public class Producer implements NamedElement, Visitable {

    private String name;
    private boolean flag;
    private Consumer target;

    public Producer() {}

    public Producer(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    public void setPin(boolean flag) {
        this.flag = flag;
    }

    public boolean getPin() {
        return flag;
    }

    public void setTarget(Consumer target) {
        this.target = target;
    }

    public Consumer getTarget() {
        return target;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
