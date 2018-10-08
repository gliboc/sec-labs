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
}

void reset_pull() {
  if(!is_present(RESET_FLAG)) { return; }
  resetCounter();
  RESET_FLAG = 0;
}



int main(void)
{
  setup();
  while(1) {
    // message producer
    button_push();
    const_push();
    // message consumer
    led_pull();
    incr_pull();
    reset_pull();
    // frequency
    _delay_ms(500);
  }
  return 0;
}
