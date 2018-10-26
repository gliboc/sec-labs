package samples;

import model.*;
import generator.*;

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
        Actuator a = new Actuator("a", 8);
        Actuator b = new Actuator("b", 9);
        Actuator c = new Actuator("c", 3);
        Actuator d = new Actuator("d", 4);
        Actuator e = new Actuator("e", 5);
        Actuator f = new Actuator("f", 6);
        Actuator g = new Actuator("g", 7);

        // Registers (or flags)
        Register switch_led_flag = new Register("LED_FLAG", false);
        Register reset_flag = new Register("RESET_FLAG", false);
        Register const_flag = new Register("CONST_FLAG", false);

        
        SetActuator turnOff = new SetActuator(Arrays.asList(a,b,c,d,e,f,g), SIGNAL.HIGH);
        SetActuator printOne = new SetActuator(Arrays.asList(b,c), SIGNAL.LOW);
        SetActuator printTwo = new SetActuator(Arrays.asList(a,b,d,e,g), SIGNAL.LOW);
        SetActuator printThree = new SetActuator(Arrays.asList(a,b,c,d,g), SIGNAL.LOW);
        SetActuator printFour = new SetActuator(Arrays.asList(b,c,f,g), SIGNAL.LOW);
        SetActuator printFive = new SetActuator(Arrays.asList(a,c,d,f,g), SIGNAL.LOW);
        SetActuator printSix = new SetActuator(Arrays.asList(a,c,d,e,f,g), SIGNAL.LOW);
        SetActuator printSeven = new SetActuator(Arrays.asList(a,b,c), SIGNAL.LOW);
        SetActuator printEight = new SetActuator(Arrays.asList(a,b,c,d,e,f,g), SIGNAL.LOW);
        SetActuator printNine = new SetActuator(Arrays.asList(a,b,c,d,f,g), SIGNAL.LOW);
        SetActuator printZero = new SetActuator(Arrays.asList(a,b,c,d,e,f), SIGNAL.LOW);


         // Consumer 
         Consumer reset = new Consumer();
         reset.setName("reset");
         reset.setSetActions(Arrays.asList(turnOff));
 
         // Producers 
         Producer btn_push = new Producer("button_push", false);
         btn_push.setTarget(reset);
        // Consumer led_pull = new Consumer("led_pull", false);

        // List<State> numerals = Arrays.asList(one, two, three, four, five, six, seven, eight, nine, zero);
        // List<DelayedTransition> incrementals = Arrays.asList(incrOne, incrTwo, incrThree, incrFour, incrFive, incrSix, incrSeven, incrEight, incrNine, incrZero);

        // for (int i = 0; i < numerals.size(); i++) {
        //     numerals.get(i).setTransitions(Arrays.asList(resetCounter, incrementals.get(i)));
        // }

            // Building the App
            App theSwitch = new App("ArDUinO ReJuvEnAtIon");
            theSwitch.setRegisters(Arrays.asList(switch_led_flag, reset_flag, const_flag));
            theSwitch.setProducers(Arrays.asList(btn_push));

            // theSwitch.setBricks(Arrays.asList(led, a, b, c, d, e, f, g));
            // theSwitch.setSensors(Arrays.asList(btn));

            // theSwitch.setFsm(Arrays.asList(blinkingLed, sevenSeg));
            // theSwitch.setInitial(on);

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
