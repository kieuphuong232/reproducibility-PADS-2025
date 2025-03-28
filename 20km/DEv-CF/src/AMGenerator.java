

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import DEVSModel.DEVSAtomic;
import DEVSModel.Port;

public class AMGenerator extends DEVSAtomic {
	
	Port carGen;
	
	enum Phase {generate, stop};
	Phase phase;
	
	private float sigma, time;
	
	static int numCar = 10000;
	static int numTestFile = 1;
	
	static String test = "input/input";
	static String filePath = test+String.valueOf(numCar)+"_"+String.valueOf(numTestFile)+".txt";
	
	String line = "";
	BufferedReader reader = null;
	
	public AMGenerator(String name) {
		this.name = name;
		carGen = new Port(this, "car_generated");
		this.addOutPort(carGen);
		
		// 2024_04_17 read input from file
		try {
			reader = new BufferedReader(new FileReader(filePath));
			reader.readLine();				// Read header line
		} catch (IOException ex) {
				ex.printStackTrace();
		} 
	}
	
	@Override
	public void deltaExt(Port arg0, Object arg1, float arg2) {
		// nothing to do
		
	}
	
	@Override
	public void deltaInt() {		
//		if (countCar < numCar) {
//			countCar ++;
//		}
//		else phase = Phase.stop;		
		
		// 2024_04_17 
		System.out.println("---deltaInt---" + this.name);
		
		try {
			line = reader.readLine();				
		} catch (IOException ex) {
				ex.printStackTrace();
		}	
		
		if (line == null)
			phase = Phase.stop;
		
		System.out.println(phase);
	}

	@Override
	public float getDuration() {
		// 2024_04_17 edit for input duration time
//		System.out.println("---getDuration---" + this.name);
		
		if (phase == Phase.stop) return Float.POSITIVE_INFINITY;
		else {
			String[] fields = line.split(" ");
			return Integer.parseInt(fields[0]);
		}
	}

	@Override
	public void init() {
		/* Init state: Generate */
		
		phase = Phase.generate;
		time = 0;
		sigma = 0;
		
		try {
			line = reader.readLine();				
		} catch (IOException ex) {
				ex.printStackTrace();
		} 
		
		if (line != null) {
			String[] fields = line.split(" ");
			sigma = Integer.parseInt(fields[0]);
		}
	}

	@Override
	public Object[] lambda() {
		/* Output an object include:
		 * obj[0] : port
		 * obj[1] : carGen object with id, init speed, departure time */
		
		// Test 2024_04_17 read input from files
		time += getDuration();
		System.out.println("---lamda---" + this.name);
		System.out.println("Time: " + time);
		
		String id = "";
		int speed = 0;
		
		String[] fields = line.split(" ");

		id = fields[1];
		speed = Integer.parseInt(fields[2]);
				
		System.out.println("*** Car("+id+") generated");
		Object[] obj = new Object[2];
		obj[0] = this.carGen; 	
		obj[1] = new Car(id, speed, time);
			
		return obj;
	}
}
