# Reproducibility PADS 2025

This is the guildline to reproduce the results discussed in the paper:

"A New Discrete Event Simulation of Large-Scale Car Traffic at Microscopic Level"

Accepeted to ACM SIGSIM PADS 2025.

## Authors & Contacts

- Thi Phuong Kieu thi-phuong.kieu@lis-lab.fr
- Hassan Haghighi hassan.haghighi@lis-lab.fr
- Amine Hamri amine.hamri@lis-lab.fr

## Requirements

- x86_64 CPU
- 8GB RAM
- Unix system with Git, Java, SUMO, Python

Install 
- Git: https://git-scm.com/downloads
- Java: https://www.java.com/en/download/manual.jsp
- SUMO: https://sumo.dlr.de/docs/Downloads.php
- Python: https://www.python.org/downloads/
- MATLAB: https://mathworks.com/help/install/ug/install-products-with-internet-connection.html

The hardware/software configuration used by the authors:
- CPU: Intel Core i5-1035G1 
- RAM: 8 GB
- OS: Windows 11
- Git 2.39.0
- Java 16.0.2
- SUMO 1.19.0
- Python 3.9.2
- MATLAB R2024a

## Get started

Ensure that Git is already installed. Open the Terminal and check:
`git --version`

1. Clone the repository: 
    ```bash
    git clone https://gitlab.lis-lab.fr/thi-phuong.kieu/reproducibility-PADS-2025.git CFM
    ```

2. Move to the folder: `cd CFM`

### Structure of CFM folder

```bash
CFM/
├── DEv-CF/        # Java project: DEv-CF model
├── IDM-model/     # SUMO project: IDM-model
└── README.md      # this file
```

## Article claims

The article has 3 claims:

- C1: The DEv-CF model is accurated with IDM model in small-scale.

- C2: The DEv-CF model is accurated with IDM model in medium-scale.

- C3: fwkDEVS framework executes faster than SUMO.

## Reproducing the results

The article has 10 figures that can be procedured. These can be generated through running 28 experiments. The mapping between claims, figures, experiments configuration are resumed in the following table.

| Claim | Figures | Experiments | 
|-------|---------|-------------|
| C1    | 6a-f, 7a-b | small-scale |          
| C2    | 9       | medium-scale|
| C3    | 10      | large-scale |

After set-up the environment as required, follow these steps to run experiments.

### Small-scale experiments

#### 1. Run Java project

Structure of `DEv-CF` folder

```bash
DEv-CF/
├── bin/        # complied Java class files
├── input/      # input files
│   ├── input10_3.txt   # data set 3
│   ├── input10_7.txt   # data set 7
│   ├── input10_9.txt   # data set 9
│   └── ...             # additional data sets
├── lib/        # external library: fwkDEVS
├── output/     # output files: report containing execution time, position and speed records
├── src/        # source code of the DEv-CF model
└── script.sh   # shell script to compile, run project, and read the report file
```

1. Open Terminal from `DEv-CF` folder

2. Change the permission to make file executable: `chmod +x script.sh`

3. Run the script: `./script.sh`

The simulation of 10 vehicles moving on a 1km road was executed with data set 3 from the input file `./input/input10_3.txt`.

Here's what you can expect to see in the terminal:

```txt
time:243.0
--road1(car_exit_road = Car 10, speed 10m/s, departure time 143.0s, arrival time 0.0s)
-trans(trans.Port.car_received = Car 10, speed 10m/s, departure time 143.0s, arrival time 0.0s)
--trans(car_received = Car 10, speed 10m/s, departure time 143.0s, arrival time 243.0s)

======================================================
|Simulated model: Car-following model simulation
|Simulation ends at time: 86399.0 t.u
|Execution time: 61894924 ns
======================================================
```

The execution time could be different depend on the CPU performance.

To continue running other data sets:

4. Open file `./src/Generator.java`

5. At line 20, change the value `numTestFile` from `3` to `7` and save.

6. Back to the Terminal, run the script again: `./script.sh`

Now, the simulation of 10 vehicles from the data set 7 (`./input/`) is executed. 

The results show on Terminal:

```txt
time:263.1111
--road1(car_exit_road = Car 10, speed 9m/s, departure time 152.0s, arrival time 0.0s)
-trans(trans.Port.car_received = Car 10, speed 9m/s, departure time 152.0s, arrival time 0.0s)
--trans(car_received = Car 10, speed 9m/s, departure time 152.0s, arrival time 263.1111s)

======================================================
|Simulated model: Car-following model simulation
|Simulation ends at time: 86399.0 t.u
|Execution time: 61238642 ns
======================================================
```

7. Once again, open file `./src/Generator.java` and change the value `numTestFile` to `9`, then save. Run the script `./script.sh` again to execute simulation with data set 9.

The results show on Terminal:

```txt
time:251.0
--road1(car_exit_road = Car 10, speed 8m/s, departure time 126.0s, arrival time 0.0s)
-trans(trans.Port.car_received = Car 10, speed 8m/s, departure time 126.0s, arrival time 0.0s)
--trans(car_received = Car 10, speed 8m/s, departure time 126.0s, arrival time 251.0s)

======================================================
|Simulated model: Car-following model simulation
|Simulation ends at time: 86399.0 t.u
|Execution time: 59993262 ns
======================================================
```

Now, new output files have now appeared in the structure of `DEv-CF` folder:

```bash
DEv-CF/
├── bin/        
├── input/              # --INPUT FILES--
│   ├── input10_3.txt   # input data set 3
│   ├── input10_7.txt   # input data set 7
│   ├── input10_9.txt   # input data set 9
│   └── ...             # additional data sets
├── lib/
├── output/             # --OUTPUT FILES--
│   ├── ...             # additional output files
│   ├── position10_3    # position records for 10 cars in data set 3
│   ├── position10_7    # position records for 10 cars in data set 7
│   ├── position10_9    # position records for 10 cars in data set 9
│   ├── report          # report file containing port transitions and execution time of the latest simulation
│   ├── speed10_3       # speed records for 10 cars in data set 3
│   ├── speed10_7       # speed records for 10 cars in data set 7
│   └── speed10_9       # Speed records for 10 cars in data set 9
├── src/        
└── script.sh   
```

These output files are used to plot the figure in the third step:

| Output file  | Figure | 
|--------------|--------|
| position10_3 | 6a, 7a |
| position10_7 | 6b     |
| position10_9 | 6c     |
| speed10_3    | 7b     |

#### 2. Run SUMO project

Structure of `IDM-model` folder

```bash
IDM-model/
├── config/             # network configuration files
├── input/              # trip input files
├── output/             # output files: log files, position and speed records
└── runSimulation.py    # python script to run simulation
```

1. Open Teminal from `IDM-model` folder

2. Run the script: `py runSimulation.py`

You will see this output in the terminal:

```txt
Step #249.00 (0ms ?*RT. ?UPS, TraCI: 0ms, vehicles TOT 10 ACT 0 BUF 0)
10_3 done
Step #264.00 (0ms ?*RT. ?UPS, TraCI: 0ms, vehicles TOT 10 ACT 0 BUF 0)
10_7 done
Step #252.00 (0ms ?*RT. ?UPS, TraCI: 1ms, vehicles TOT 10 ACT 0 BUF 0)
10_9 done
```

The simulations using three different data sets 3, 7 and 9 were executed.

The `IDM-model` folder now includes new output files:

```bash
IDM-model/
├── config/            
├── input/                  # --INPUT FILES--
│   ├── trips10_3.rou.xml   # input data set 3
│   ├── trips10_7.rou.xml   # input data set 7
│   ├── trips10_9.rou.xml   # input data set 9
│   └── ...                 # additional data sets
├── output/                 # --OUTPUT FILES--
│   ├── dump10_3.out.xml    # position and speed records
│   ├── dump10_7.out.xml    # position and speed records 
│   ├── dump10_9.out.xml    # position and speed records 
│   ├── log10_3.txt         # warning and simulation summary records
│   ├── log10_7.txt         # warning and simulation summary records
│   └── log10_9.txt         # warning and simulation summary records
└── runSimulation.py   
```

We need to convert the XML files to CSV files for data visualization, using the XML2CSV tool of SUMO.

3. Go to the `output` folder: `cd output`

4. Convert these XML files to CSV: 

```bash
py xml2csv.py dump10_3.out.xml
py xml2csv.py dump10_7.out.xml
py xml2csv.py dump10_9.out.xml
```

If successful, the converted CSV files will appear inside the `output` folder.

```bash
IDM-model/
├── config/            
├── input/ 
├── output/                 # --OUTPUT FILES--
│   ├── dump10_3.out.csv    # converted CSV file
│   ├── dump10_3.out.xml    
│   ├── dump10_7.out.csv    # converted CSV file
│   ├── dump10_7.out.xml     
│   ├── dump10_9.out.csv    # converted CSV file
│   ├── dump10_9.out.xml    
│   └── ...
└── runSimulation.py   
```

These output files are used to plot the figure in the next step:

| Output file      | Figure | 
|------------------|--------|
| dump10_3.out.csv | 6d, 7a, 7b |
| dump10_7.out.csv | 6e     |
| dump10_9.out.csv | 6f     |

#### 3. Plot figures on MATLAB

After executing the simulation and converting output data, follow these steps to generate the Figure 6 and Figure 7:

1. Launch MATLAB on your system

2. Navigate to CFM project folder

3. Run the MATLAB scripts: 

```matlab
figure_6abc.m
figure_6def.m
figure_7.m
```

### Medium-scale experiments

To plot Figure 9, follow these steps to run the DEv-CF model and IDM model to collect the output data then generate the figure.

#### 1. Run Java project

1. Open the Terminal and avigate to the project directory: `CFM/20km/DEv-CF`

2. Change file permission: `chmod +x script.sh` 

3. Execute the script to start the simultion: `./script.sh`

The simulation of 10,000 vehicles on a 20km-road was executed. The results display on Terminal: 
