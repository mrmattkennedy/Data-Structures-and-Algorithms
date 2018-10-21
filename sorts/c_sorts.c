#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>
#include <stdint.h>
#include <inttypes.h>

void insertionSort(int arrayToSort[], int size);

int main (int argc, char** argv) {

	if (argc != 2)
		return 0;

	struct timespec start, end;
	//do stuff

	//do stuff
	int size = atoi(argv[1]);
	int arrayToSort[size];

	for (int i = 0; i < size; i++)
		arrayToSort[i] = rand() % 10001;

	clock_gettime(CLOCK_MONOTONIC_RAW, &start);
	insertionSort(arrayToSort, size);
	clock_gettime(CLOCK_MONOTONIC_RAW, &end);

	uint64_t delta_us = (end.tv_sec - start.tv_sec) * 1000000 + (end.tv_nsec - start.tv_nsec) / 1000;
	printf("time in microseconds: %" PRId64 "\n", delta_us);
	printf("time in milliseconds: %" PRId64 "\n", delta_us / 1000);
	//for (int i = 0; i < size; i++)
	//	printf("%d\n", arrayToSort[i]);
	return 1;
}

void insertionSort(int arrayToSort[], int size) {
	int i, j, temp;
	for(i = 1; i < size; i++)
	{
		temp = arrayToSort[i];
		j = i - 1;
		while (j >= 0 && arrayToSort[j] > temp)
		{
			arrayToSort[j + 1] = arrayToSort[j];
			j--;
		}
		arrayToSort[j + 1] = temp;
	}	
}
