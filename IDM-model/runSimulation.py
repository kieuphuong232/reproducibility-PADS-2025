import traci
from sumolib import checkBinary

sumoBinary = checkBinary('sumo')

numCars = 10
numTestFiles = 9

def main():

    runSimulation(numCars,numTestFiles)
    
    # for x in range(1, numTestFiles+1):
    #     runSimulation(numCars, x)
            

def runSimulation(nbCars, nbFile):
    # Start simulation 
    traci.start([
        'sumo',
        '-n', './config/net1000.net.xml',
        '-r', f'./input/trips{nbCars}_{nbFile}.rou.xml',
        '--log', f'./log/log{nbCars}_{nbFile}.txt',
        '--netstate-dump', f'./log/dump{nbCars}_{nbFile}.out.xml',
        '--start'
    ])

    # Move simulation step
    traci.simulationStep()
    while shouldContinueSim():
        traci.simulationStep() 

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