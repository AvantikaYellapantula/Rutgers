#ifndef SORTED_LIST_H
#define SORTED_LIST_H
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef int (*CompareFuncT)( void *, void * );
typedef void (*DestructFuncT)( void * );

struct Node{
  void *obj;
  struct Node *nextNode;
  int count;
};
typedef struct Node* Node;

struct SortedListIterator{
  Node ref;
};
typedef struct SortedListIterator* SortedListIteratorPtr;

struct SortedList{
  CompareFuncT compare;
  DestructFuncT destroy;
  Node root;
  int size;
};
typedef struct SortedList* SortedListPtr;

SortedListPtr SLCreate(CompareFuncT cf, DestructFuncT df);
void SLDestroy(SortedListPtr list);
int SLInsert(SortedListPtr list, void *newObj);
int SLRemove(SortedListPtr list, void *newObj);
SortedListIteratorPtr SLCreateIterator(SortedListPtr list);
void SLDestroyIterator(SortedListIteratorPtr iter);
void *SLGetItem(SortedListIteratorPtr iter);
void *SLNextItem(SortedListIteratorPtr iter);

#endif
