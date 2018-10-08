#include <avr/io.h>
#include <util/delay.h>
#include <Arduino.h>
#include <fsm.h>


// ########## INITIALIZATION #############

void init_led() { pinMode(13, OUTPUT); }
void init_button() { pinMode(10, INPUT); }
void init_sevenSeg() { 
  for (int a = 1; a < 8; a++) {
    pinMode(a, OUTPUT);
  }
}

void setup() {
  init_sevenSeg();
  init_led();
  init_button();
}

int buttonPressed() {
  return digitalRead(10);
}

// #### 7 segment helper
void turnOff() {
  for (int a=1; a < 8; a++) {
    digitalWrite(a, HIGH);
  }
}

void displayDigit(int digit)
{
  turnOff();
  if(digit!=1 && digit != 4)
    digitalWrite(1,LOW);
  if(digit != 5 && digit != 6)
    digitalWrite(2,LOW);
  if(digit !=2)
    digitalWrite(3,LOW);
  if(digit != 1 && digit !=4 && digit !=7)
    digitalWrite(4,LOW);
  if(digit == 2 || digit ==6 || digit == 8 || digit==0)
    digitalWrite(5,LOW);
  if(digit != 1 && digit !=2 && digit!=3 && digit !=7)
    digitalWrite(6,LOW);
  if (digit!=0 && digit!=1 && digit !=7)
    digitalWrite(7,LOW);
}


// ##### SINGLE FINITE STATE MACHINE
//
//  All the states are encapsulated in a single
//  state function.
//  `state(i, b)` is the state where i is printed 
//  on the screen and the led is set according to b
//
//  We do this only for the product of the states 
//  (print a digit) X (switch led on/off) because 
//  their behaviors are similar.  


void state(int counter, boolean led_on) {
  _delay_ms(500);
  displayDigit(counter);

  if (led_on) {
    digitalWrite(13, HIGH);
  }
  else {
    digitalWrite(13, LOW);
  }
  
  if (buttonPressed()) {
    state(0, !(led_on));
  }
  state( (counter + 1) % 10, !(led_on) );
}



int main(void)
{
  setup();
  state(0, false);
}
