

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import DEVSModel.DEVSAtomic;
import DEVSModel.Port;

public class AMTransducer extends DEVSAtomic {
	
	Port carRec;
	
	enum Phase {idle, received};
	Phase phase;
	
	private float time;
	
	// 2024_04_17 ouput in trans
	private FileWriter writerSpeed;
	private FileWriter writerPosition;
	
	public AMTransducer() {
		name = "trans";
		carRec = new Port(this,"car_received");
		this.addInPort(carRec);
	}
	
	@Override
	public void init() {
		phase = Phase.idle;	
		time = 0;
		try {
			writerSpeed = new FileWriter("output/speed"+AMGenerator.numCar+"_"+AMGenerator.numTestFile);
			writerPosition =new FileWriter("output/position"+AMGenerator.numCar+"_"+AMGenerator.numTestFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deltaExt(Port port, Object obj, float e) {
		// Update system time
		time += e;
		
		System.out.println("---deltaExt---trans");
		System.out.println("Time: " + time);
		

		
		// If car comes to AMTransducer
		if (port.equals(carRec)) {
			if (phase == Phase.idle) {
				// Change to received state
				phase = Phase.received;
				// Set arrival time
				((Car)obj).setArrivalTime(time);
				Car carNew = (Car)obj;
				
				System.out.println("*** Car received");
				
				// 2024_04_17 print results to file
				
				for (String s : carNew.listPosition) {
					try {
						writerPosition.write(carNew.getID()+" "+s);
						writerPosition.write("\n");
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				
				for (String s : carNew.listSpeed) {
					try {
						writerSpeed.write(carNew.getID()+" "+s);
						writerSpeed.write("\n");
					} catch (IOException e1) {
						e1.printStackTrace();
					}

				}
				
			}
		}
	}

	@Override
	public void deltaInt() {
		if (phase == Phase.received) {
			// Back to idle state
			phase = Phase.idle;
		}
	}

	@Override
	public float getDuration() {
		if (phase == Phase.received)
			return 0;			
		else return Float.POSITIVE_INFINITY;
	}

	@Override
	public Object[] lambda() {
		// nothing to output
		return null;
	}
	
	public void endSim(){
		try {
			writerPosition.close();
			writerSpeed.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("**** Simulation ends ****");
	}
}
