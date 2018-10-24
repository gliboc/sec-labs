//Interfacing with arduino led + 7segment

#include "glue_arduino.h"

int led = 13; // Synchronized with the shield led
int led_on = 1; // led state
int button = 10; //button
int button_state= 0;

// TODO : add some more global variables

//Global variables for 7 segment
int a = 1;  //For displaying segment "a"
int b = 2;  //For displaying segment "b"
int c = 3;  //For displaying segment "c"
int d = 4;  //For displaying segment "d"
int e = 5;  //For displaying segment "e"
int f = 6;  //For displaying segment "f"
int g = 7;  //For displaying segment "g"


void setup() {               
  //Setup for LED on pin 
  pinMode(led, OUTPUT);  
  
  led_on=1;//true

  int i;
  for (i = a; i <= g; i++){
    pinMode(i, OUTPUT); 
  }
}

void turnOff() //turn off the 7seg (CC) 
{
  int i;
  for (i = a; i <= g; i++){ // this could be prettier
    digitalWrite(i,HIGH); // change into HIGH for common anode
  }
}


void displayDigit(int digit)
{
  turnOff();
  //Conditions for displaying segment a
  if (digit != 1 && digit != 4)
    digitalWrite(a,LOW); // change into HIGH for common cathode
  if (digit != 5 && digit != 6)
    digitalWrite(b, LOW);
  if (digit != 2)
    digitalWrite(c, LOW);
  if (digit != 4 && digit != 1 && digit != 7)
    digitalWrite(d, LOW);
  if ((digit == 2) | (digit == 6) | (digit == 8) | (digit == 0))
    digitalWrite(e, LOW);
  if (digit != 1 && digit != 2 && digit != 3 && digit != 7)
    digitalWrite(f, LOW);
  if (digit != 1 && digit != 7 && digit != 0)
    digitalWrite(g, LOW);
}
