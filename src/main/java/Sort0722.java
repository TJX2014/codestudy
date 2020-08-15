public class Sort0722 {

    public static void main(String[] args) {
//        int []arr = {5, 1, 3, 4, 2};
//        quickSort(arr, 0, arr.length -1);
//        for (int a: arr) {
//            System.out.println(a + ",");
//        }

        int []arr2 = {5, 1, 3, 4, 2};
        mergeSort(arr2, 0, arr2.length -1);
        for (int a: arr2) {
            System.out.println(a + ",");
        }
    }

    static void mergeSort(int[] arr, int begin, int end) {
        if (begin < end) {
            int mid = (begin+end)/2;
            mergeSort(arr, begin, mid);
            mergeSort(arr, mid+1, end);
            merge(arr, begin, mid, end);
        }
    }

    static void merge(int[] arr, int begin, int mid, int end) {
        if (begin < end) {
            int[] temp = new int[end-begin+1];
            int li = begin;
            int ri = mid+1;
            int ti = 0;
            while (li <= mid && ri <= end) {
                if (arr[li] <= arr[ri]) {
                    temp[ti++] = arr[li++];
                } else {
                    temp[ti++] = arr[ri++];
                }
            }
            while (li <= mid) {
                temp[ti++] = arr[li++];
            }
            while (ri <= end) {
                temp[ti++] = arr[ri++];
            }
            for (int i=begin; i<=end; i++) {
                arr[i] = temp[i-begin];
            }
        }
    }

//    static void mergeSort(int[] arr, int begin, int end) {
//        if (begin < end) {
//            int mid = (begin + end)/2;
//            mergeSort(arr, begin, mid);
//            mergeSort(arr, mid+1, end);
//            merge(arr, begin, mid, end);
//        }
//    }
//
//    static void merge(int[] arr, int begin, int mid, int end) {
//        if (begin < end) {
//            int[] temp = new int[end-begin+1];
//            int li = begin;
//            int ri = mid+1;
//            int ti = 0;
//            while (li <= mid && ri <= end) {
//                if (arr[li] <= arr[ri]) {
//                    temp[ti++] = arr[li++];
//                } else {
//                    temp[ti++] = arr[ri++];
//                }
//            }
//            while (li <= mid) {
//                temp[ti++] = arr[li++];
//            }
//            while (ri <= end) {
//                temp[ti++] = arr[ri++];
//            }
//            for (int i=begin; i<=end; i++) {
//                arr[i] = temp[i-begin];
//            }
//        }
//    }

}
