package io.github.mosser.arduinoml.ens.samples;

import io.github.mosser.arduinoml.ens.model.*;
import io.github.mosser.arduinoml.ens.generator.*;

import java.awt.font.NumericShaper.Range;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Delayed;

import org.omg.CORBA.Any;

public class Led {

	public static void main(String[] args) {
		Actuator led = new Actuator("LED", 13);
        Sensor btn = new Sensor("BTN", 10);
        Actuator a = new Actuator("a", 1);
        Actuator b = new Actuator("b", 2);
        Actuator c = new Actuator("c", 3);
        Actuator d = new Actuator("d", 4);
        Actuator e = new Actuator("e", 5);
        Actuator f = new Actuator("f", 6);
        Actuator g = new Actuator("g", 7);

        // Declaring FSM
        Fsm blinkingLed = new Fsm("blinkingLed");
        Fsm sevenSeg = new Fsm("sevenSeg");

		// Declaring states
		State on = new State("on");
        State off = new State("off");

        // Declaring state 
        State one = new State("one");
        State two = new State("two");
        State three = new State("three");
        State four = new State("four");
        State five = new State("five");
        State six = new State("six");
        State seven = new State("seven");
        State eight = new State("eight");
        State nine = new State("nine");
        State zero = new State("zero");
        State clear = new State("clear");

        
        // Binding states to an FSM
        blinkingLed.setStates(Arrays.asList(on, off));
        sevenSeg.setStates(
            Arrays.asList(one,two,three,four,five,six,seven,eight,nine,zero,clear));

        // Setting initial state
        blinkingLed.setInitial(on);
        sevenSeg.setInitial(zero);

		// Creating actions
		Action switchTheLightOn = new Action(Arrays.asList(led), Arrays.asList(SIGNAL.HIGH));
        Action switchTheLightOff = new Action(Arrays.asList(led), Arrays.asList(SIGNAL.LOW));
        
        Action turnOff = new Action(Arrays.asList(a,b,c,d,e,f,g), SIGNAL.HIGH);
        Action printOne = new Action(Arrays.asList(b,c), SIGNAL.LOW);
        Action printTwo = new Action(Arrays.asList(a,b,d,e,g), SIGNAL.LOW);
        Action printThree = new Action(Arrays.asList(a,b,c,d,g), SIGNAL.LOW);
        Action printFour = new Action(Arrays.asList(b,c,f,g), SIGNAL.LOW);
        Action printFive = new Action(Arrays.asList(a,c,d,f,g), SIGNAL.LOW);
        Action printSix = new Action(Arrays.asList(a,c,d,e,f,g), SIGNAL.LOW);
        Action printSeven = new Action(Arrays.asList(a,b,c), SIGNAL.LOW);
        Action printEight = new Action(Arrays.asList(a,b,c,d,e,f,g), SIGNAL.LOW);
        Action printNine = new Action(Arrays.asList(a,b,c,d,f,g), SIGNAL.LOW);
        Action printZero = new Action(Arrays.asList(a,b,c,d,e,f), SIGNAL.LOW);

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
        off.setActions(Arrays.asList(switchTheLightOff));
        
        one.setActions(Arrays.asList(turnOff,printOne));
        two.setActions(Arrays.asList(turnOff,printTwo));
        three.setActions(Arrays.asList(turnOff,printThree));
        four.setActions(Arrays.asList(turnOff,printFour));
        five.setActions(Arrays.asList(turnOff,printFive));
        six.setActions(Arrays.asList(turnOff,printSix));
        seven.setActions(Arrays.asList(turnOff,printSeven));
        eight.setActions(Arrays.asList(turnOff,printEight));
        nine.setActions(Arrays.asList(turnOff,printNine));
        zero.setActions(Arrays.asList(turnOff,printZero));

        // Creating transitions
        SensorTransition onToOff = new SensorTransition();
        onToOff.setTarget(off);
        onToOff.setSensor(btn);
        onToOff.setValue(SIGNAL.HIGH);

        SensorTransition offToOn = new SensorTransition();
        offToOn.setTarget(on);
        offToOn.setSensor(btn);
        offToOn.setValue(SIGNAL.HIGH);

        DelayedTransition onToOffBlink = new DelayedTransition();
        onToOffBlink.setTarget(off);
        onToOffBlink.setDelay(500);

        DelayedTransition offToOnBlink = new DelayedTransition();
        offToOnBlink.setTarget(on);
        offToOnBlink.setDelay(500);

        SensorTransition resetCounter = new SensorTransition();
        resetCounter.setTarget(zero);
        resetCounter.setSensor(btn);
        resetCounter.setValue(SIGNAL.LOW);

        DelayedTransition incrZero = new DelayedTransition();
        incrZero.setTarget(one);
        incrZero.setDelay(500);

        DelayedTransition incrOne = new DelayedTransition();
        incrOne.setTarget(two);
        incrOne.setDelay(500);

        DelayedTransition incrTwo = new DelayedTransition();
        incrTwo.setTarget(three);
        incrTwo.setDelay(500);

        DelayedTransition incrThree = new DelayedTransition();
        incrThree.setTarget(four);
        incrThree.setDelay(500);

        DelayedTransition incrFour = new DelayedTransition();
        incrFour.setTarget(five);
        incrFour.setDelay(500);

        DelayedTransition incrFive = new DelayedTransition();
        incrFive.setTarget(six);
        incrFive.setDelay(500);

        DelayedTransition incrSix= new DelayedTransition();
        incrSix.setTarget(seven);
        incrSix.setDelay(500);

        DelayedTransition incrSeven = new DelayedTransition();
        incrSeven.setTarget(eight);
        incrSeven.setDelay(500);

        DelayedTransition incrEight = new DelayedTransition();
        incrEight.setTarget(nine);
        incrEight.setDelay(500);

        DelayedTransition incrNine = new DelayedTransition();
        incrNine.setTarget(zero);
        incrNine.setDelay(500);

        // Using self-transitions for interruptions - should disappear
        Transition onLoop = new Transition();
        onLoop.setTarget(on);

        Transition offLoop = new Transition();
        offLoop.setTarget(off);

        // Adding transitions to states
        on.setTransitions(Arrays.asList(onToOff, onToOffBlink));
        off.setTransitions(Arrays.asList(offToOn, offToOnBlink));

        // 
        List<State> numerals = Arrays.asList(one, two, three, four, five, six, seven, eight, nine, zero);
        List<DelayedTransition> incrementals = Arrays.asList(incrOne, incrTwo, incrThree, incrFour, incrFive, incrSix, incrSeven, incrEight, incrNine, incrZero);

        for (int i = 0; i < numerals.size(); i++) {
            numerals.get(i).setTransitions(Arrays.asList(resetCounter, incrementals.get(i)));
        }

            // Building the App
            App theSwitch = new App("ArDUinO ReJuvEnAtIon");

            theSwitch.setBricks(Arrays.asList(led, a, b, c, d, e, f, g));
            theSwitch.setSensors(Arrays.asList(btn));

            theSwitch.setFsm(Arrays.asList(blinkingLed, sevenSeg));
            theSwitch.setInitial(on);

            // Generating Code
            Visitor codeGenerator = new ToC();
            theSwitch.accept(codeGenerator);

		// Writing C files
        try {
            System.out.println("Generating C code: ./output/fsm.h");
            Files.write(Paths.get("./output/fsm.h"), codeGenerator.getHeaders().toString().getBytes());
            System.out.println("Generating C code: ./output/main.c");
            Files.write(Paths.get("./output/main.c"), codeGenerator.getCode().toString().getBytes());
            System.out.println("Code generation: done");
            System.out.println("Board upload : cd output && make upload && cd ..;");
        } catch (IOException ex) {
            System.err.println(ex);
        }
	}

}
