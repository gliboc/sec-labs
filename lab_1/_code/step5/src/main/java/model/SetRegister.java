package model;

import generator.Visitable;
import generator.Visitor;

import java.util.ArrayList;
import java.util.List;

public class SetRegister implements Visitable, Action {

    private Register guard;
    private Register target;
    private Boolean value;

    public void SetTarget() {
    }

    public void SetTarget(Register guard, Register target, Boolean value) {
        this.guard = guard;
        this.target = target;
        this.value = value;
    }

    public void SetTarget(Register target, Boolean value) {
        this.target = target;
        this.value = value;
    }

    public void setGuard(Register guard) {
        this.guard = guard;
    }

    public Register getGuard() {
        return guard;
    }

    public Register getTarget() {
        return target;
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
