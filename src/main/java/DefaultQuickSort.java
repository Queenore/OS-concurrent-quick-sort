public class DefaultQuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{123, 1, 23, 12, 4, 54};
        quickSort(array);
        for (int a : array) {
            System.out.println(a);
        }
    }

    private static void quickSort(int[] array) {
        sort(array, 0, array.length);
    }

    private static void sort(int[] arr, int fromInclusive, int toExclusive) {
        if (fromInclusive >= toExclusive - 1) {
            return;
        }
        int pos = partition(arr, fromInclusive, toExclusive);
        sort(arr, fromInclusive, pos);
        sort(arr, pos + 1, toExclusive);
    }

    private static int partition(int[] arr, int fromInclusive, int toExclusive) {
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

    private static void swap(int[] arr, int index1, int index2) {
        int temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    }
}
