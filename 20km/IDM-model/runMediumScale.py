import traci
from sumolib import checkBinary

sumoBinary = checkBinary('sumo')

def main():

    runSimulation(10000,1)

def runSimulation(nbCars, nbFile):
    # Start simulation 
    traci.start([
        'sumo',
        '-n', './config/net1000.net.xml',
        '-r', f'./input/trips{nbCars}_{nbFile}.rou.xml',
        '--log', f'./output/log{nbCars}_{nbFile}.txt',
        # '--netstate-dump', f'./output/dump{nbCars}_{nbFile}.out.xml',
        '--summary', f'./output/summary{nbCars}_{nbFile}.xml',
        '--start'
    ])

    # Move simulation step
    time = 0
    while time < 86399:
        traci.simulationStep()
        time+=1

    # End simulation
    traci.close()

    print(f"{nbCars}_{nbFile} done")

def shouldContinueSim():
    # Checks that the simulation should continue running
    # Returns: (bool) 'True' if vehicles exist on network, otherwise 'False'

    numVehicles = traci.simulation.getMinExpectedNumber()
    return True if numVehicles > 0 else False

if __name__ == "__main__":
    main()