# Reproducibility PADS 2025

## Getting started

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
- Unix system with Java, SUMO

Install Java [here](https://www.java.com/en/download/manual.jsp) and SUMO [here](https://sumo.dlr.de/docs/Downloads.php).

The hardware/software configuration used by the authors:
- CPU: Intel Core i5-1035G1 
- RAM: 8 GB
- OS: Windows 11
- Java version 16.0.2
- SUMO version 1.19.0

## Run the DEv-CF model on Java

Ensure that Java is already installed. Open Terminal and check `java --version`.

1. Clone the repository: 
    ```bash
    git clone https://gitlab.lis-lab.fr/thi-phuong.kieu/reproducibility-PADS-2025.git DEv-CF
    ```

2. Move to the DEv-CF folder: `cd DEv-CF`

3. Run the script: `./script.sh`

The simulation of 10 cars moving on a 1km road was executed.

### Structure of DEv-CF model

```bash
DEv-CF/
├── bin/        # complied Java class files
├── input/      # input files
├── lib/        # external library: fwkDEVS
├── output/     # output files: report containing execution time, position and speed records
├── src/        # source code of the DEv-CF model
└── script.sh   # shell script to compile, run project, and read the report file
```

### Change the scenario parameters

To change the scenario parameters, open class file `Generator.java` then change the number of car and number of test file.
Then run the script again.

## Run the IDM model on SUMO

Ensure that SUMO is already installed. Open Terminal and check `sumo --version`.

1. Clone the repository: 
    ```bash
    git clone https://gitlab.lis-lab.fr/thi-phuong.kieu/reproducibility-PADS-2025.git IDM-model
    ```
    
2. Move to the DEv-CF folder: `cd IDM-model`

3. Run the Python script:

## Article claims

The article has 3 claims:

- C1: The DEv-CF model is accurated with IDM model in small-scale.

- C2: The DEv-CF model is accurated with IDM model in medium-scale.

- C3: fwkDEVS framework executes faster than SUMO.

## Reproducing the results

The article has ... figures that can be procedured. These can be generated through running ... experiments. The mapping between claims, figures, experiments configuration are resumed in the following table.

| Claim | Figures | Input files | Output files | Plot file|
|-------|---------|-------------|--------------|----------|
| C1    | 
| C2    | 

