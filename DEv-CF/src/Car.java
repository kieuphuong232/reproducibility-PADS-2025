

import java.util.ArrayList;

public class Car {
	/* This car has below variables:
	 * (String) id 
	 * (int) speedCurrent    : current speed (m/s)
	 * (int) speedDesire     : desire speed (m/s) 
	 * (float) departureTime : departure time (s) 
	 * (float) arrivalTime   : arrival time (s) 
	 * (float) position		 : curent position (m)
	 * (float) t 			 : time reaching end of road from current position (s)
	 * (float) deltaT		 : time reaching front car from current position (s) */
	
	int DistaceBetweenCar=3;
	int CarLenght = 2;
	String id;
	int speedCurrent, speedDesire;
	float departureTime, arrivalTime;
	float t;
	float deltaT = Float.POSITIVE_INFINITY;
	float position = 0;
	float watingTime=0;
	

	// Test 2024_04_09
	public ArrayList<String> listSpeed = new ArrayList();
	public ArrayList<String> listPosition = new ArrayList();
	
	public Car(String name, int speed, float time) {
		super();
		this.id = name;
		this.speedDesire = speed;
		this.speedCurrent = speed;
		this.departureTime = time;
	}
	
	public String toString() {
		return ("Car " + this.id + ", "
				+ "speed " + this.speedCurrent + "m/s, "
				+ "departure time " + this.departureTime + "s, "
				+ "arrival time " + this.arrivalTime + "s");
	}
	
	// For test 2024_04_06
	public String getID() {
		return this.id;
	}
	
	// Set initial speed when a car enters road
	public void setSpeed(int speed) {
		if (speedDesire < speed) speedCurrent = speedDesire;
		else speedCurrent = speed;
	}
	
	public int getSpeed() {
		return speedCurrent;
	}
	
	public void setT(float length) {
		// 2024_04_16 time (s) = length (m) / speedCurrent (m/s)
		this.t = length / speedCurrent;
	}
	
	public float getT() {
		return this.t;
	}
	public float getWatingTime() {
		return watingTime;
	}
	
	public void setDeltaT(float positionFront, int speedFront) {
		//change it because of the gaps that can be broken
		if (speedFront < speedCurrent) {
			// 2024_04_16 deltaTime (s) = deltaDistance (m) / deltaSpeed (m/s)
			this.deltaT = (positionFront - position - DistaceBetweenCar-CarLenght) / (speedCurrent - speedFront);
			if((positionFront - position -DistaceBetweenCar-CarLenght)<0) 
			{
				this.deltaT=0;
				this.position=positionFront-DistaceBetweenCar-CarLenght;
			}
				
			
		}
		else this.deltaT = Float.POSITIVE_INFINITY;
	}
	
	public float getDeltaT() {
		return this.deltaT;
	}
	
	public void setPosition() {
		this.position = 0;
	}
	
	public float getPosition() {
		return this.position;
	}
	
	// Update position, t, deltaT when AMRoad has an external event
	public void updateValue(float e) {
		// 2024_04_16 position (m) = speed (m/s) * time (s)
		this.position += speedCurrent * e;
		this.t -= e;
		this.deltaT -= e;
	}
	public void updateWatingTime(float e) {
		this.watingTime-=e;
	}
	
	// Update speed, t, deltaT when car reaches front car
	public void updateValue(int speedFront, float timeFront) {
		this.speedCurrent = speedFront;
		// why  i changed it ?
		//this.t=timeFront;
		float gaps =  (DistaceBetweenCar+CarLenght) ;
		// i diveded float / int to have a float result
		this.t = timeFront +  ( gaps  /speedFront );
		this.deltaT = Float.POSITIVE_INFINITY;
	}
	
	// Arrival time is set when car is received at AMTransducer
	public void setArrivalTime(float time) {
		arrivalTime = time;
	}
	
	public void setWatingTime(float PositionFront,int speedFront) {
		watingTime=(5-PositionFront)/(speedFront);
	}
	
	// Test 2024_04_09
	public void addSpeed(float time) {
		listSpeed.add(String.valueOf(time) + " " + String.valueOf(speedCurrent));
	}
	
	// Test 2024_04_09
	public void addPosition(float time) {
		listPosition.add(String.valueOf(time) + " " + String.valueOf(position));
	}
	
}
