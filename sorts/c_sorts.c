#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <time.h>
#include <stdint.h>
#include <inttypes.h>
#include <ctype.h>
#include <string.h>

void insertionSort(int arrayToSort[], int size);
void bubbleSort(int arrayToSort[], int size);
int isValidNumber(char number[]);

int main (int argc, char** argv) {

	if (((argc - 1) % 2 != 0) || (argc < 3))
		return 0;

	for (int i = 2; i < argc;  i+=2)
		if (isValidNumber(argv[i]) == 0)
			return 0;

	struct timespec start, end;
	int size, choice;
	void (*sort_fun_ptr_arr[])(int[], int) = {insertionSort, bubbleSort};

	for (int i = 1; i < argc; i+=2) {
		size = atoi(argv[i + 1]);
		int arrayToSort[size];
		if (strcmp(argv[i], "insertion") == 0)
			choice = 0;
		else if (strcmp(argv[i], "bubble") == 0) 
			choice = 1;
		else {
			printf("No matching sort\n");
			continue;
		}

		for (int i = 0; i < size; i++)
			arrayToSort[i] = rand() % 10001;
		
		clock_gettime(CLOCK_MONOTONIC_RAW, &start);
		(*sort_fun_ptr_arr[choice])(arrayToSort, size);
		clock_gettime(CLOCK_MONOTONIC_RAW, &end);

		uint64_t delta_us = (end.tv_sec - start.tv_sec) * 1000000 + (end.tv_nsec - start.tv_nsec) / 1000;
		printf("% " PRId64, delta_us);
		//for (int i = 0; i < size; i++)
			//printf("%d\n", arrayToSort[i]);
	}
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

void bubbleSort(int arrayToSort[], int size) {
	int i, j, temp;
	for (i = 0; i < size; i++)
		for (j = i; j < size; j++)
			if (arrayToSort[i] > arrayToSort[j]) { // i > j
				arrayToSort[j] = arrayToSort[i] + arrayToSort[j];
				arrayToSort[i] = arrayToSort[j] - arrayToSort[i];
				arrayToSort[j] = arrayToSort[j] - arrayToSort[i];
			}
}

int isValidNumber(char number[])
{
	int i = 0;

	//checking for negative numbers
	if (number[0] == '-')
		return 0;
	for (; number[i] != 0; i++)
	{
		//if (number[i] > '9' || number[i] < '0')
		if (!isdigit(number[i]))
			return 0;
	}
	return 1;
}

