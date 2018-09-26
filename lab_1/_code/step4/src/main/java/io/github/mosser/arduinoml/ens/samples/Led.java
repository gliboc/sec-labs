package io.github.mosser.arduinoml.ens.samples;

import io.github.mosser.arduinoml.ens.model.*;
import io.github.mosser.arduinoml.ens.generator.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Led {

	public static void main(String[] args) {
		Actuator led = new Actuator("LED", 13);
    Sensor btn = new Sensor("BTN", 2);

		// Declaring states
		State on = new State("on");
		State off = new State("off");

		// Creating actions
		Action switchTheLightOn = new Action(led, SIGNAL.HIGH);
		Action switchTheLightOff = new Action(led, SIGNAL.LOW);

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

		theSwitch.setStates(Arrays.asList(on, off));
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
        } catch (IOException e) {
            System.err.println(e);
        }
	}

}
