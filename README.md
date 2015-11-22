# Linear-Space-knapsack-algo
Linear-Space-knapsack-algo

Written in Java.

DP + Traceback : written in the "solution_used" function used in HW1_CS5050_DP
Divide and Conquer in DP : You can find them in HW2_CS5050

Funtions and variables can be changed in the program to chance the number of objects and number of runs 
in each of the programs

                 ------------------------------------------------------------------------

Folders -

Raw Data - Inputs used for testing all the algorithms

Java Code - Code used for the algos

Graphs - Graphs with different parameters to measure the efficiency of the algorithm

Report - Detailed information about graphs and observations.

                 ------------------------------------------------------------------------

1)      Implemented trace-back routine that takes the 2D cache array and returns an assignment of true or false to each object in the problem description, where true means the object is selected.

2)      Implemented linear space knapsack algorithm described in the nodes and summarized below to solve the same problem knapsack (another repo in github).  The linear-space divide and conquer algorithm should divide the problem in to equal halves (so k is n/2).

3)      For a set of small problem verify that the three algorithms (linear, standard DP and caching) produce the same answer.

For empirical studies:

For randomized data generate a graph that measures the average cpu time of the divide and conquer linear-space solution as a function of the problem size. Given n is the problem size and m is the capacity of the knapsack for each experiment set m= 10*n. In each experiment, generate the sizes of the n objects need from a uniform random distribution between 1 and m/10 (the distribution does not matter since we are using DP). Start at size 64 and go as large as you can within reasonable running times, increasing the problem size by a factor of 2 each time. Produce a log-log graph where x is the log of the size of the problem and y is the log of the average running time.
Compare the performance of the new D&C algorithm with your previous DP+traceback algorithm. What is the difference between the lines? How much larger problems can you solve with the D&C compared to the original algorithm.
