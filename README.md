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

The hardware/software configuration used by the authors:
- CPU: Intel Core i5-1035G1 
- RAM: 8 GB
- OS: Windows 11
- Git version 2.39.0
- Java version 16.0.2
- SUMO version 1.19.0
- Python version 3.9.2

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

<!-- ```bash
DEv-CF/
├── bin/        # complied Java class files
├── input/      # input files
├── lib/        # external library: fwkDEVS
├── output/     # output files: report containing execution time, position and speed records
├── src/        # source code of the DEv-CF model
└── script.sh   # shell script to compile, run project, and read the report file
``` -->

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

After set-up the environment as required, follow these steps to run experiments

### Small-scale experiments

#### Run on Java

1. Move to the DEv-CF folder: `cd DEv-CF`

3. Run the script: `./script.sh`

The simulation of 10 cars moving on a 1km road was executed with data set 3 from the input file `./input/input10_3.txt`.
