# COMP3100_Project

By: Edgar Murga Garcia De Leon  
SID: 47094133

** Project Overview **

This project implements a client-side job dispatcher simulator that communicates with the ds-sim server-side simulator (ds-server) using the ds-sim simulation protocol. The simulator handles job reception, scheduling, and dispatching, with a focus on designing and evaluating scheduling algorithms for distributed systems.

Stage 1 – Largest-Round-Robin (LRR) Scheduler

The first stage involved implementing a simple scheduling algorithm called Largest-Round-Robin (LRR).

The client connects to the ds-server, receives jobs one at a time, and schedules them to available servers.

The LRR algorithm dispatches jobs to servers of the largest type (i.e., those with the most CPU cores).

If multiple server types have the same core count, the first listed in the configuration file (ds-system.xml) is used.

Jobs are distributed across these servers in a round-robin sequence to balance the load.

This stage successfully demonstrated correct client–server communication and protocol adherence while implementing a functional scheduling strategy.

Stage 2 – Optimized Scheduling for Average Turnaround Time

The second stage focused on designing and implementing new scheduling algorithms that aimed to minimize the average turnaround time of jobs while maintaining reasonable resource utilization and server rental costs.

Developed and tested one or more custom scheduling algorithms that optimally balanced speed and efficiency.

Evaluated performance against baseline algorithms including First Fit (FF), Best Fit (BF), First Fit with Queue (FFQ), Best Fit with Queue (BFQ), and Worst Fit with Queue (WFQ).

Ensured the solution was compatible with any simulation configuration, not limited to provided examples.

Created and used additional configurations to demonstrate algorithm performance and trade-offs between objectives.

This stage showcased the ability to design and justify an optimization-based scheduling strategy while maintaining flexibility and adaptability across various system configurations.

** Outcome **

The completed client simulator successfully implements multiple scheduling algorithms, including LRR and an optimized custom scheduler that reduces average turnaround time without excessive resource overhead. The project demonstrates strong understanding of distributed job scheduling, performance evaluation, and trade-off analysis between conflicting system objectives.

** Notes: **

Project was completed using java and a Linux-based OS.

The final version of my code is within the "src" folder in the java project, and it is the "MyClient_S2_NP" file.

To see older versions of the code go into the "archive" folder.
