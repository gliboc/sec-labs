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

// ##### ELECTRONICS AND LOGICAL COMPONENTS

// ## LED Helper
boolean led_on = true;
void change_state_led(){
  if (led_on){ digitalWrite(13, LOW); } else { digitalWrite(13, HIGH); }
  led_on = !led_on;
}


// #### 7 segment helper
void turnOff() {
  for (int a=1; a < 8; a++) {
    digitalWrite(a, HIGH);
  }
}

void displayDigit(int digit)
=======
int led = 13;

void setup()
>>>>>>> upstream/master:lab_1/_code/step3/main.c
{
  pinMode(led, OUTPUT);
}

<<<<<<< HEAD:lab_1/_code/step3/producer-consumer-version/main.c
// ###### Message FLAG framework

boolean is_present(int messageQueue) { return messageQueue == 1; }

// message FLAGs
boolean LED_FLAG   = false;
boolean RESET_FLAG = false;
boolean CONST_FLAG = false;
int counter = 0;

// ##### Message producers

void button_push() {
  if (digitalRead(10) == HIGH) {
    LED_FLAG = true; RESET_FLAG = true;
  }
}

void const_push() { CONST_FLAG = true; }

// ###### Message consumers

void led_pull() {
  if(!is_present(LED_FLAG)) { return; } // do nothing if no message present
  change_state_led(); // business code
  LED_FLAG = 0;        // remove flag
}

void displayCounter() {
  displayDigit(counter);
}
void incrementCounter() {
  counter = (counter + 1) % 10;
}
void resetCounter() {
  counter = 0;
}

void incr_pull() {
  if(!is_present(CONST_FLAG)) { return; }
  displayCounter(); incrementCounter();
  CONST_FLAG = 0;
int main(void)
{
  setup();
  state_on(); // initial state
  return 0;
  state_on();
}
