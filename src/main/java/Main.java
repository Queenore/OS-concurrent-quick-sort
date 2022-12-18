import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        threadsCountEfficiencyCompare();
        compareSorts();
    }

    private static int[] generateRandomIntArray(int size, int minValue, int maxValue) {
        int[] array = new int[size];
        for (int j = 0; j < size; j++) {
            array[j] = ThreadLocalRandom.current().nextInt(minValue, maxValue + 1);
        }
        return array;
    }

    private static void threadsCountEfficiencyCompare() {
        System.out.println("Threads count efficiency test\n" +
                "=================================================");
        for (int size = 10000; size < 10000001; size *= 10) {
            System.out.println("array length = " + size);
            int[] array = generateRandomIntArray(size, -10000, 10000);
            for (int threadsCount = 2; threadsCount <= 32; threadsCount *= 2) {
                int attempts = 5;
                int timeSum = 0;
                for (int attempt = 0; attempt < attempts; attempt++) {
                    int[] arrayCopy = Arrays.copyOf(array, array.length);
                    long startConcurrentSortTime = System.currentTimeMillis();
                    ForkJoinPool fjPool = new ForkJoinPool(threadsCount);
                    ConcurrentQuickSort forkJoinQuicksortTask = new ConcurrentQuickSort(arrayCopy,0, size);
                    fjPool.invoke(forkJoinQuicksortTask);
                    timeSum += (System.currentTimeMillis() - startConcurrentSortTime) ;
                }
                System.out.println("\tthreads count = " + threadsCount + " => execution average time = "
                        + (timeSum / attempts) + " ms");
            }
        }
        System.out.println("=================================================");
    }

    private static void compareSorts() {
        System.out.println("Comparing default and concurrent quick sort\n" +
                "=============================================================================================================");
        for (int size = 100; size < 100000001; size *= 2) {
            System.out.print("array length = " + size);
            int attempts = 5;
            int defaultSortTime = 0;
            int concurrentSortTime = 0;
            for (int attempt = 0; attempt < attempts; attempt++) {
                int[] array = generateRandomIntArray(size, -10000, 10000);
                int[] arrayForDefaultSort = Arrays.copyOf(array, array.length);
                int[] arrayForConcurrentSort = Arrays.copyOf(array, array.length);

                long startDefaultSortTime = System.currentTimeMillis();
                DefaultQuickSort.quickSort(arrayForDefaultSort);
                defaultSortTime += (System.currentTimeMillis() - startDefaultSortTime);

                long startConcurrentSortTime = System.currentTimeMillis();
                ForkJoinPool fjPool = new ForkJoinPool(8);
                ConcurrentQuickSort forkJoinQuicksortTask = new ConcurrentQuickSort(arrayForConcurrentSort,0, size);
                fjPool.invoke(forkJoinQuicksortTask);
                concurrentSortTime += (System.currentTimeMillis() - startConcurrentSortTime);
            }
            System.out.println(" => default sort average time = " + (defaultSortTime / attempts)
                    + " ms | concurrent sort average time = " + (concurrentSortTime / attempts) + " ms");
        }
        System.out.println("=============================================================================================================");
    }
}
