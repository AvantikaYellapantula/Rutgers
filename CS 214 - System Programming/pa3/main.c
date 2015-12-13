#include "memory.h"
#define malloc(x) mymalloc(x)
#define free(ptr) myfree(ptr)

int main(int agrc, char **argv){
  //Error: Freeing The Node Twice
  char *test1 = malloc(sizeof(char) * 100);
  free(test1);
  free(test1);

  //Error: Not freeing dynamically allocated memory
  int test2;
  free(&test2);
  
  //No Error
  int *test3 = malloc(sizeof(int));
  free(test3);

  //No Error
  char *test4 = malloc(4);
  free(test4);

  //Allocating and Freeing Multiple Nodes
  int *test5 = malloc(10);
  int *test6 = malloc(20);
  int *test7 = malloc(30);
  int *test8 = malloc(40);
  int *test9 = malloc(50);
  int *test10 = malloc(100);
  free(test6);
  free(test8);
  free(test10);
  free(test5);
  free(test7);
  free(test9);

  return 1;
}
