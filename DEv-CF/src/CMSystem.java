

import DEVSModel.DEVSCoupled;

public class CMSystem extends DEVSCoupled {
	
	AMGenerator gen1;
	AMRoad road1;
	AMTransducer trans;
	
	@Override
	public void setSelectPriority() {
		// TODO Auto-generated method stub
		
	}
	
	public CMSystem() {
		super();
		name = "Car-following model simulation";
		
		// Create AMs Generator
		this.gen1 = new AMGenerator("gen1");
		
		// Create AMs Road with name, length (m), vMax (m/s)
		this.road1 = new AMRoad("1", (float) 1000, 13);
		
		// Create AMTransducer
		this.trans = new AMTransducer();
		
		// Get sub models
		this.getSubModels().add(gen1);
		
		this.getSubModels().add(road1);
		
		this.getSubModels().add(trans);
		
		// Link ports
		
		this.addIC(this.gen1.getOutPort("car_generated"), this.road1.getInPort("car_enter_road"));
		
		this.addIC(this.road1.getOutPort("car_exit_road"), this.trans.getInPort("car_received"));
		
		// Test 2024_04_17
//		this.addIC(this.gen1.getOutPort("car_generated"), this.trans.getInPort("car_received"));
	}	
}
