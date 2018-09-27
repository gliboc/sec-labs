package io.github.mosser.arduinoml.ens.samples;

import io.github.mosser.arduinoml.ens.model.*;
import io.github.mosser.arduinoml.ens.generator.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Led {

	public static void main(String[] args) {
		Actuator led = new Actuator("LED", 13);
        Sensor btn = new Sensor("BTN", 2);
        Actuator a = new Actuator("a", 8);
        Actuator b = new Actuator("b", 9);
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
        
        Action turnOff = new Action(Arrays.asList(a,b,c,d,e,f,g), 
            new ArrayList<SIGNAL>(Collections.nCopies(7, SIGNAL.HIGH)));

		// Binding actions to states
		on.setActions(Arrays.asList(switchTheLightOn));
		off.setActions(Arrays.asList(switchTheLightOff));

        // Creating transitions
        SensorTransition onToOff = new SensorTransition();
        onToOff.setTarget(off);
        onToOff.setSensor(btn);
        onToOff.setValue(SIGNAL.HIGH);

        SensorTransition offToOn = new SensorTransition();
        offToOn.setTarget(on);
        offToOn.setSensor(btn);
        offToOn.setValue(SIGNAL.HIGH);

        // Using self-transitions for interruptions - should disappear
        Transition onLoop = new Transition();
        onLoop.setTarget(on);

        Transition offLoop = new Transition();
        offLoop.setTarget(off);

        // Adding transitions to states
        on.setTransitions(Arrays.asList(onToOff, onLoop));
        off.setTransitions(Arrays.asList(offToOn, offLoop));

            // Building the App
            App theSwitch = new App("ArDUinO ReJuvEnAtIon");

            theSwitch.setBricks(Arrays.asList(led));
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
