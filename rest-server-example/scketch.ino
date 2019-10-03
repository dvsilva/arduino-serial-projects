int actuatorPin =  7; // actuator pin
int serialPortValue; // serial port data variable

int sensorPin = A1; // sensor pin
int sensorValue = 0;  // sensor data variable

void setup(){
  	Serial.begin(9600); // serial port rate
  	pinMode(pin,OUTPUT); // define actuator pin as output
}
 
void loop(){
  sensorValue = analogRead(sensorPin); // read sensor data
  Serial.println(vsensorValueal);    
  
  if(Serial.available() > 0){ // verify if serial communication exists
      serialPortValue = Serial.read();// read serial port data
      
      switch(serialPortValue){
        case 0:
           digitalWrite(actuatorPin, LOW); // turn off
        break;
        case 1:
           digitalWrite(actuatorPin, HIGH); // turn on
         break;
    }
  }
}