import java.util.concurrent.RecursiveAction;

class ConcurrentQuickSort extends RecursiveAction {
    int[] array;
    int fromInclusive;
    int toExclusive;

    public ConcurrentQuickSort(int[] array, int fromInclusive, int toExclusive) {
        this.array = array;
        this.fromInclusive = fromInclusive;
        this.toExclusive = toExclusive;
    }

    @Override
    protected void compute() {
        if (fromInclusive < toExclusive - 1) {
            int pos = partition(array, fromInclusive, toExclusive);
            ConcurrentQuickSort t1 = new ConcurrentQuickSort(array, fromInclusive, pos);
            ConcurrentQuickSort t2 = new ConcurrentQuickSort(array, pos + 1, toExclusive);
            t1.fork();
            t2.compute();
            t1.join();
        }
    }

    private int partition(int[] arr, int fromInclusive, int toExclusive) {
        int pivotal = arr[fromInclusive];
        int pos = fromInclusive;
        for (int j = fromInclusive + 1; j < toExclusive; ++j) {
            if (arr[j] <= pivotal) {
                swap(arr, ++pos, j);
            }
        }
        swap(arr, pos, fromInclusive);
        return pos;
    }

    private void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}