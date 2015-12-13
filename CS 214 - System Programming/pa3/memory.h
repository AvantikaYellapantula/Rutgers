#ifndef MEMORY_H
#define MEMORY_H

#include <stdlib.h>
#include <stdio.h>

struct Memory{
  struct Memory *prev;
  struct Memory *next;
  unsigned int size;
  int free;
};
typedef struct Memory* Memory;

void* mymalloc(unsigned int size);
void myfree(void *ptr);

void setMemoryCap(int size);
void setMemorySize(int size);

#endif
