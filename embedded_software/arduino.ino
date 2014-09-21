#include <Servo.h> 
 
Servo myservo;  // create servo object to control a servo 
                // a maximum of eight servo objects can be created 
 
int pos = 0;    // variable to store the servo position 
int pin0 = 10;
int pin1 = 11;
int pin2 = 2;
int pin3 = 3;
int pin4 = 4;
int pin5 = 5;
int pin6 = 6;
int pastPos = 150;

void setup() 
{
  Serial.begin(9600);
  myservo.attach(9);  // attaches the servo on pin 9 to the servo object 
  for(int i = 0; i < 7; i++) {
    pinMode(i, INPUT);
  }
  myservo.write(150);
  delay(1000);
  Serial.print("done");
} 
 
 
void loop() 
{ 
  // tell servo to go to position
  //calculate the angle
  int a = digitalRead(pin6);
  int b = digitalRead(pin5);
  int c = digitalRead(pin4);
  int d = digitalRead(pin3);
  int e = digitalRead(pin2);
  int f = digitalRead(pin1);
  int g = digitalRead(pin0);
  int pos = (a<<7) + (b << 6) + (c << 5) + (d << 4) + (e << 3) + (f << 2) + (g << 1);
  Serial.println(pos);
  pos = 150 - pos;
  if (pos != pastPos && pos >= 0 && pos <=135 ) {
    myservo.write(pos);
    Serial.print("WROTE THIS POSITION: ");
    Serial.println(-1 * (pos-150));
    pastPos = pos;
  }
  Serial.print(a);
  Serial.print(b);
  Serial.print(c);
  Serial.print(c);
  Serial.print(d);
  Serial.print(e);
  Serial.print(f);
  Serial.println(g);
  //Serial.println(pos);
  delay(6000);
  

}