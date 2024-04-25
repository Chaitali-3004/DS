
//DS Assignment 3: Develop a distributed system, to find sum of N elements in an array by distributing N/n elements to n number of processors MPI or OpenMP. Demonstrate by displaying the intermediate sums calculated at different processors.

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class DistributedSum extends RecursiveAction {
    private static final int THRESHOLD = 100; // Threshold for parallelism
    private int[] array;
    private int start;
    private int end;
    private int result;

    public DistributedSum(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
        this.result = 0;
    }

    protected void compute() {
        if ((end - start) <= THRESHOLD) {
            // Calculate local sum for the chunk
            for (int i = start; i < end; i++) {
                result += array[i];
            }
        } else {
            // Split the task into smaller subtasks
            int mid = (start + end) / 2;
            DistributedSum left = new DistributedSum(array, start, mid);
            DistributedSum right = new DistributedSum(array, mid, end);
            left.fork();
            right.compute();
            left.join();
            result = left.result + right.result;
        }
    }

    public static void main(String[] args) {
        int[] array = new int[1000]; // Total number of elements
        int totalSum = 0;

        // Initialize array with some values
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        ForkJoinPool pool = new ForkJoinPool();
        DistributedSum task = new DistributedSum(array, 0, array.length);

        pool.invoke(task);

        totalSum = task.result;

        // Display final sum
        System.out.println("Total sum = " + totalSum);
    }
}


/*
import mpi.*;

public class DistributedSum {
    public static void main(String[] args) throws MPIException {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank(); // get the rank of the current process
        int size = MPI.COMM_WORLD.Size(); // get the total number of processes

        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}; // sample input array
        int n = array.length; // total number of elements
        int local_n = n / size; // number of elements to be processed by each process
        int remainder = n % size; // number of remaining elements

        int[] local_array = new int[local_n + (rank < remainder ? 1 : 0)]; // local array to hold the elements for each process
        int offset = rank * local_n + Math.min(rank, remainder); // compute the offset for the current process
        for (int i = 0; i < local_array.length; i++) {
            local_array[i] = array[offset + i];
        }

        int local_sum = 0; // compute the sum of the local elements
        for (int i = 0; i < local_array.length; i++) {
            local_sum += local_array[i];
        }

        int[] global_sums = new int[size]; // array to hold the global sum from each process
        MPI.COMM_WORLD.Allgather(new int[]{local_sum}, 0, 1, MPI.INT, global_sums, 0, 1, MPI.INT); // gather the local sums to all processes
         
        if (rank == 0) { // print the intermediate and final sums
        	System.out.println("Number of Processes Entered: "+ size);
        	System.out.println("\nIntermediate Sums:");
            int sum = 0;
            for (int i = 0; i < size; i++) {
                sum += global_sums[i];
                System.out.println("Process " + i + ": " + global_sums[i]);
            }
            System.out.println("\nTotal Sum: " + sum);
        }

        MPI.Finalize();
    }
}

*/

/*Output:
C:\Users\dell\desktop>javac -cp "C:\Users\dell\Downloads\mpj-v0_44\mpj-v0_44\lib\mpj.jar" DistributedSum.java

C:\Users\dell\desktop>"C:\Users\dell\Downloads\mpj-v0_44\mpj-v0_44\bin\mpjrun.bat" -np 6 DistributedSum

Number of Processes Entered: 6
Intermediate Sums:
Process 0: 3
Process 1: 7
Process 2: 11
Process 3: 15
Process 4: 9
Process 5: 10
Total Sum: 55
*/
