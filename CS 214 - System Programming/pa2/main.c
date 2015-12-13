#include <stdio.h>
#include <stdlib.h>
#include "sorted-list.h"

// Test Compare Function
int compare(void * p1, void * p2){
  float *x1 = (float *) p1;
  float *x2 = (float *) p2;

  if(*x1 < *x2)
    return -1;
  else if (*x1 > *x2)
    return 1;
  else
    return 0;
}

void destroy(void * p){
  if(!p || p == NULL){
    printf("Error\n");
    return;
  }
  free(p);
}

int main(int agrc, char **argv){
  void *item;

  float x1 = 10;
  float x2 = 20;
  float x3 = 5;
  float x4 = 15;
  float x5 = 0;

  void *p1 = &x1;
  void *p2 = &x2;
  void *p3 = &x3;
  void *p4 = &x4;
  void *p5 = &x5;

	CompareFuncT compareFuncT = compare;
  DestructFuncT destructFuncT = destroy;

	SortedListPtr SL = SLCreate(compareFuncT, destructFuncT);

 //10 20 5 15 0 R10 R5 R0 
  SLInsert(SL, p1);
  SLInsert(SL, p2);
  SLInsert(SL, p3);	
  SLInsert(SL, p4);
  SLInsert(SL, p5);
  SLInsert(SL, p4);

  SLRemove(SL, p1);

	SortedListIteratorPtr IT = SLCreateIterator(SL);
  item = SLGetItem(IT);

  while(item != NULL){
    printf("%f\n", *(float*)(item));
    SLNextItem(IT);
    item = SLGetItem(IT);
  }

  return 0;
}
