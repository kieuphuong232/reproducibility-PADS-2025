import DEVSSimulator.Root;

public class Simulator {
	
	public static void main(String[] args) {
		CMSystem sys = new CMSystem();
		Root root = new Root(sys, 86399); 	
		root.startSimulation();
	}
}
