/*
 * BubbleSort.h
 *
 *  Created on: 21.11.2011
 *      Author: christian
 */

#ifndef BUBBLESORT_H_
#define BUBBLESORT_H_

#include "stdTypes.h"

class BubbleSort {
public:
	BubbleSort();
	virtual ~BubbleSort();

    static int doBubbleSort(intVector& arr) {

        bool swapped = true;
        int count = 0;
        for(int i = arr.size() - 1; i > 0 && swapped; --i) {
            swapped = false;
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j+1]) {
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    swapped = true;
                    count++;
                }
            }
        }
        return count;

    }
};

#endif /* BUBBLESORT_H_ */
