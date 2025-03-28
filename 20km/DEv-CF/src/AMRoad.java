

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Vector;

import DEVSModel.DEVSAtomic;
import DEVSModel.Port;

public class AMRoad extends DEVSAtomic {

	Port carEnter, carExit;
	Vector<Car> queue , watingQueue;
	
	enum Phase {noCar, normalFlow}
	Phase phase;
	
	private float sigma, time; 
	HashMap<Float, Integer> numCar ;
	
	float length = 0;
	int vMax = 0;
	private PrintWriter writer;
	
	
	public AMRoad(String name, float length, int vMax) {
		super();
		this.name = "road" + name;
		this.length = length;
		this.vMax = vMax;
		System.out.println(this.name + " " + this.length);
		
		carEnter = new Port(this,"car_enter_road");
		carExit = new Port(this,"car_exit_road");
		this.addInPort(carEnter);
		this.addOutPort(carExit);
		
	}
	
	@Override
	public void deltaExt(Port port, Object obj, float e) {
		
		// Update system time
		time += e;
		
		if (port.equals(carEnter)) {
			
			// Car enters road.
			Car carNew = (Car) obj;
			
			// Set position for new car
			carNew.setPosition();
			
			// Set speed for new car
			carNew.setSpeed(vMax);
			
			// Calculate time to road ending point
			carNew.setT(length);
			
			// Test 2024_04_06
			System.out.println("---deltaExt---" + this.name);
			System.out.println("Time: " + time);
			System.out.println("Sigma: " + sigma);
			
			// If road is empty, change to new state
			if (phase == Phase.noCar) {
				phase = Phase.normalFlow;
				
				// Set duration time
				sigma = carNew.getT();
			
				// adding the car
				// Test 2024_04_09
				carNew.addPosition(time);
				carNew.addSpeed(time);
				
				// Add car to queue
				queue.add(carNew);
				numCar.put(time, queue.size());
				WriteInFile("output"+"/numCar"+AMGenerator.numCar+"_"+AMGenerator.numTestFile,time+","+queue.size());
				System.out.println("Number of car on "+this.name+ " : "+queue.size()+" at time : "+time);
			
			}
			// If not, update value for all car
			else {
				upadates(e);
				
				if(watingQueue.size()!=0) {
					//System.out.println("Car "+carNew.id+"is entring wating files");
					watingQueue.add(carNew);
					if(watingQueue.get(0).watingTime==0 && (queue.get(queue.size()-1).position>5 )) {
						//System.out.println("Car "+watingQueue.get(0).id+"is exiting wating files");
						Car carFromWatingQueue = watingQueue.remove(0);
						
						carFromWatingQueue.setDeltaT(queue.get(queue.size()-1).getPosition(),
									queue.get(queue.size()-1).getSpeed());
				
						queue.add(carFromWatingQueue);
						if (watingQueue.size()!=0) {
							watingQueue.get(0).setWatingTime(queue.get(queue.size()-1).position, queue.get(queue.size()-1).speedCurrent);
							
						}
						WriteInFile("output"+"/numCar"+AMGenerator.numCar+"_"+AMGenerator.numTestFile,time+","+queue.size());
					}
				}
				else {
					if(queue.get(queue.size()-1).position-carNew.position<5) {
						watingQueue.add(carNew);
						watingQueue.get(0).setWatingTime(queue.get(queue.size()-1).position, queue.get(queue.size()-1).speedCurrent);
						
						/*if(watingQueue.get(0).watingTime==0) {
							System.out.println("Car "+carNew.id+"is exiting wating files");
							Car carFromWatingQueue = watingQueue.remove(0);
							
							carFromWatingQueue.setDeltaT(queue.get(queue.size()-1).getPosition(),
										queue.get(queue.size()-1).getSpeed());
							queue.add(carFromWatingQueue);
							if (watingQueue.size()!=0) {
								watingQueue.get(0).setWatingTime(carFromWatingQueue.position, carFromWatingQueue.speedCurrent );
								
							}
						}*/
					
					}
					else {
						carNew.setDeltaT(queue.get(queue.size()-1).getPosition(),
								queue.get(queue.size()-1).getSpeed());
						carNew.addPosition(time);
						carNew.addSpeed(time);
						
						// Add car to queue
						queue.add(carNew);
						numCar.put(time, queue.size());
						WriteInFile("output"+"/numCar"+AMGenerator.numCar+"_"+AMGenerator.numTestFile,time+","+queue.size());
						System.out.println("Number of car on "+this.name+ " : "+queue.size()+" at time : "+time);
					
						
					}
				}
				// Find new sigma
				sigma -= e;
				sigma = carNew.getT() < sigma ? carNew.getT() : sigma;
				sigma = carNew.getDeltaT() < sigma ? carNew.getDeltaT() : sigma;
				if(watingQueue.size()!=0) 
					sigma = watingQueue.get(0).watingTime < sigma ? watingQueue.get(0).watingTime : sigma;
			}
			
			// Test 2024_04_22
			/*System.out.println("Car(" + carNew.getID() + ").speed =  " + carNew.getSpeed());
			System.out.println("Car(" + carNew.getID() + ").position =  " + carNew.getPosition());
			System.out.println("Car(" + carNew.getID() + ").T = " + carNew.getT());
			System.out.println("Car(" + carNew.getID() + ").deltaT =  " + carNew.getDeltaT());*/
			}
		
	}

	@Override
	public void deltaInt() {	
		// Test 2024_04_06
		System.out.println("---deltaInt---" + this.name);
		System.out.println("Time: " + time);
		System.out.println("Sigma: " + sigma);
				
		if (queue.isEmpty()) {
			// If road is empty, change to new state
			phase = Phase.noCar;
			sigma = Float.POSITIVE_INFINITY;
		}
		else {
			updates2(getDuration());
			
			if(watingQueue.size()!=0) {
				if(watingQueue.get(0).watingTime==0) {
					//System.out.println("Car "+watingQueue.get(0).id+"is exiting wating files");
					Car carFromWatingQueue = watingQueue.remove(0);
					
					carFromWatingQueue.setDeltaT(queue.get(queue.size()-1).getPosition(),
								queue.get(queue.size()-1).getSpeed());
					queue.add(carFromWatingQueue);
					WriteInFile("output"+"/numCar"+AMGenerator.numCar+"_"+AMGenerator.numTestFile,time+","+queue.size());

					if (watingQueue.size()!=0) {
						watingQueue.get(0).setWatingTime(queue.get(queue.size()-1).position, queue.get(queue.size()-1).speedCurrent);
						
					}
				}
			}
			// Find new sigma
			sigma = queue.get(0).getT();
			for (int i = 1; i < queue.size(); i++) {
				//sigma = queue.get(i).getT() < sigma ? queue.get(i).getT() : sigma;
				sigma = queue.get(i).getDeltaT() < sigma ? queue.get(i).getDeltaT() : sigma;
			}
			if(watingQueue.size()!=0) 
				sigma = watingQueue.get(0).watingTime < sigma ? watingQueue.get(0).watingTime : sigma;
			}
	}

	@Override
	public float getDuration() {
		return sigma;
	}

	@Override
	public void init() {
		/* Init state: noCar */
		
		phase = Phase.noCar;
		time = 0;
		sigma = Float.POSITIVE_INFINITY;
		queue = new Vector<Car>();
		numCar= new HashMap<>();
		watingQueue = new Vector<Car>();
	}

	@Override
	public Object[] lambda() {
		// Update system time
		time += getDuration();
		
		// Test 2024_04_22
		/*System.out.println("---lambda---" + this.name);
		System.out.println("Time: " + time);
		System.out.println("Sigma: " + sigma);
		*/
		// If car reaches end point, output car object to next model
		if (queue.get(0).getT() - getDuration() == 0) {
			queue.get(0).updateValue(getDuration());
			Object[] obj = new Object[2];
			obj[0] = carExit;
			obj[1] = queue.get(0);
			
			// Test 2024_04_22
			System.out.println("** Car(" + queue.get(0).getID() + ") exits road");
			
			// Test 2024_04_09
			queue.get(0).addPosition(time);
			queue.get(0).addSpeed(time);
			
			queue.remove(0);
			numCar.put(time, queue.size());
			WriteInFile("output"+"/numCar"+AMGenerator.numCar+"_"+AMGenerator.numTestFile,time+","+queue.size());
			System.out.println("Number of car on "+this.name+ " : "+queue.size()+" at time : "+time);
			
			
			
			return obj;
		}
		return null;
	}
	
	private void upadates(Float e) {
		
		// update wating queue 
		if(watingQueue.size()!=0) {
			watingQueue.get(0).updateWatingTime(e);
		}
		
		// Update position, t, deltaT, speed
		for (int i = 0; i < queue.size(); i++) {
			queue.get(i).updateValue(e);
			
			// Test 2024_04_22
			/*System.out.println("Car(" + queue.get(i).getID() + ").speed =  " + queue.get(i).getSpeed());
			System.out.println("Car(" + queue.get(i).getID() + ").position =  " + queue.get(i).getPosition());
			System.out.println("Car(" + queue.get(i).getID() + ").T = " + queue.get(i).getT());
			System.out.println("Car(" + queue.get(i).getID() + ").deltaT =  " + queue.get(i).getDeltaT());
			System.out.println();
			*/
			// Test 2024_04_09
			//queue.get(i).addPosition(time);
		}
		
		for (int i = 1; i < queue.size(); i++) {
			// If there's a car reach front car
			if (queue.get(i).getDeltaT() == 0) {
				
				// Test 2024_04_22
				System.out.println("** Car reachs front car **");
				
				// Update speed, t, deltaT for current car
				queue.get(i).updateValue(queue.get(i-1).getSpeed(), queue.get(i-1).getT());
				
				// Test 2024_04_22
				/*System.out.println("Car(" + queue.get(i-1).getID() + ").speed =  " + queue.get(i-1).getSpeed());
				System.out.println("Car(" + queue.get(i-1).getID() + ").T = " + queue.get(i-1).getT());
				System.out.println();
				
				System.out.println("Car(" + queue.get(i).getID() + ").speed =  " + queue.get(i).getSpeed());
				System.out.println("Car(" + queue.get(i).getID() + ").T = " + queue.get(i).getT());
				System.out.println("Car(" + queue.get(i).getID() + ").deltaT =  " + queue.get(i).getDeltaT());
				System.out.println();
				*/
				// Recalculate deltaT for behind car
				if (i != queue.size()-1) {
					queue.get(i+1).setDeltaT(queue.get(i).getPosition(), queue.get(i).getSpeed());
					
					/*System.out.println("Car(" + queue.get(i+1).getID() + ").T = " + queue.get(i+1).getT());
					System.out.println("Car(" + queue.get(i+1).getID() + ").deltaT =  " + queue.get(i+1).getDeltaT());
					System.out.println();		
				*/
					}
										
				// Test 2024_04_09
				queue.get(i).addSpeed(time);
				queue.get(i).addPosition(time);
			}
		}
	}

	private void updates2(Float e) {
		
		// update wating queue 
		if(watingQueue.size()!=0) {
			watingQueue.get(0).updateWatingTime(e);
		}
		// Update position, t, deltaT, speed
		for (int i = 0; i < queue.size(); i++) {
			queue.get(i).updateValue(getDuration());
			
			// Test 2024_04_22
			/*System.out.println("Car(" + queue.get(i).getID() + ").speed =  " + queue.get(i).getSpeed());
			System.out.println("Car(" + queue.get(i).getID() + ").position =  " + queue.get(i).getPosition());
			System.out.println("Car(" + queue.get(i).getID() + ").T = " + queue.get(i).getT());
			System.out.println("Car(" + queue.get(i).getID() + ").deltaT =  " + queue.get(i).getDeltaT());
			System.out.println();
			*/
			// Test 2024_04_09
			//queue.get(i).addPosition(time);
		}
			
		for (int i = 1; i < queue.size(); i++) {
			// If there's a car reach front car
			if (queue.get(i).getDeltaT() == 0) {
				// Test 2024_04_06
				System.out.println("** Car reachs front car **");
				
				// Update speed, t, deltaT for current car
				queue.get(i).updateValue(queue.get(i-1).getSpeed(), queue.get(i-1).getT());
				
				// Test 2024_04_22
				/*System.out.println("Car(" + queue.get(i-1).getID() + ").speed =  " + queue.get(i-1).getSpeed());
				System.out.println("Car(" + queue.get(i-1).getID() + ").T = " + queue.get(i-1).getT());
				System.out.println();
				
				System.out.println("Car(" + queue.get(i).getID() + ").speed =  " + queue.get(i).getSpeed());
				System.out.println("Car(" + queue.get(i).getID() + ").T = " + queue.get(i).getT());
				System.out.println("Car(" + queue.get(i).getID() + ").deltaT =  " + queue.get(i).getDeltaT());
				System.out.println();
				*/
				// Recalculate deltaT for behind car
				if (i != queue.size()-1) {
					queue.get(i+1).setDeltaT(queue.get(i).getPosition(), queue.get(i).getSpeed());
					
					/*System.out.println("Car(" + queue.get(i+1).getID() + ").T = " + queue.get(i+1).getT());
					System.out.println("Car(" + queue.get(i+1).getID() + ").deltaT =  " + queue.get(i+1).getDeltaT());
					System.out.println();		
				*/}
				
				// Test 2024_04_09
				queue.get(i).addSpeed(time);	
				queue.get(i).addPosition(time);
			}
		}
	}

	
private void WriteInFile(String fileName,String data) {
        FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(fileName,true);
	        fileWriter.write(data);
	        fileWriter.write("\n");
	        fileWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
