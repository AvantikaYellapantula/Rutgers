#include "memory.h"

static Memory head = NULL;
static void *myblock[5000];
static int MAX = sizeof(myblock);
static int MEMORY_SIZE = 0;

void* mymalloc(unsigned int size){
  //Initiate Variables
  Memory searchMem;
  Memory addMem;
  size += sizeof(struct Memory);

  //*********
  // Error checking
  //*********
  if(size <= 0){
    printf("Error: Invalid Parameter\n");
    return NULL;
  }
  if(MEMORY_SIZE == MAX){//Error Check To For Valid Parameters And Space
    printf("Error: Memory Is FUll\n");
    return NULL;
  }  
  if((MEMORY_SIZE + size) > MAX){//Return NULL If Not Enough Space In Memory To Allocate New Node
    printf("Error: Not Enough Space To Allocate Pointer\n");
    return NULL;
  }
 
  //*********
  // adding node
  //*********
  // Create New Node For Memory Object 
  addMem = (Memory)sbrk(size);
  addMem->size = size;
  addMem->next = NULL;
  addMem->prev = NULL;
  addMem->free = 0;
  
  if(MEMORY_SIZE==0){// Set Root Node If One Does Not Exist
	  MEMORY_SIZE+=(size);
    head = addMem;
    return (char *)addMem + sizeof(struct Memory); 
  } else {// Search LinkedList To Create Space For New Data Type
	  searchMem = head;
	  while(searchMem != NULL){
		  if(searchMem->free == 1 && size <= searchMem->size){// Node Is Free And Has Enough Space To occupy
        MEMORY_SIZE+=size; 
        addMem = (struct Memory *)((char *)searchMem + size);
        addMem->size = searchMem->size - size;
        addMem->free = 1;

        addMem->prev = searchMem;
        addMem->next = searchMem->next;

        if(searchMem->next != NULL){
          searchMem->next->prev = addMem;
        }

        searchMem->next = addMem;
		    searchMem->free = 0;
		    searchMem->size = size;
		    return (char *)searchMem + sizeof(struct Memory); 
		  }
		  else if(searchMem->next == NULL){// Reached Last Node In LinkedList
		    MEMORY_SIZE+=size;
		    addMem->prev = searchMem;
		    searchMem->next = addMem;
		    return (char *)addMem + sizeof(struct Memory); 
		  }
		  searchMem = searchMem->next;
	  }
  }

  return NULL;
}

void myfree(void *ptr){
  // Initiate Variables
  Memory searchNode;
  Memory targetNode;

  //*********
  // Error checking
  //*********
  if(ptr == NULL){// Error Checking For Valid Parameter Values
    printf("Error: Invalid Parameter Pointer In free(x)\n");
    return;
  }
  if(head == NULL){// If Head Is NULL, Nothing Was Allocated
    printf("Error: Pointer Not Found In Dynamic Memory\n");
    return;
  }
  
  //*********
  // removing
  //*********
  targetNode = (struct Memory *)((char *)ptr - sizeof(struct Memory));  
  searchNode = head;
  while(searchNode != NULL){// Search LinkedList To Free Node
    if(searchNode == targetNode){//Found Node To Pointer
      if(searchNode->free == 1){//Error Pointer Was Already Free
        printf("Error: Pointer Already Free\n");
        return;
      }
      
	    //Free Pointer
      searchNode->free = 1;
	    MEMORY_SIZE-=searchNode->size;
      return;
    }
    //I don't know how to do this part
    //Free Pointers Not Returned From Malloc
    searchNode = searchNode->next;
  }

  // Pointer Not Found In LinkedList
  printf("Error: Pointer Not Found In Memory\n");
}

//********//
// Testing Different Size Of Memory Block
//********//
void setMemoryCap(int size){
  MAX = size;
}

void setMemorySize(int size){
  MEMORY_SIZE = size;
}
