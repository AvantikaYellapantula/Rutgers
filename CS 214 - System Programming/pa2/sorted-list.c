#include "sorted-list.h"

/*
  Returns A SortedListPtr
  With The Compare and Destroy Functions
*/
SortedListPtr SLCreate(CompareFuncT cf, DestructFuncT df){
  SortedListPtr list = malloc(sizeof(SortedListPtr));
  // return null if memory was not able to be allocated or parameters are invalid
  if(!list || cf == NULL || df == NULL){
    return NULL;
  }
  // Set Comparator And Destroy Functions
  list->compare = cf;
  list->destroy = df;

  return list;
}

/*
  * Stores Root Node In freeNode
  * We free(freeNode) As We Set The Head Node To It's Next Pointer
  * Until We Hit Null, which is the end of the linked list
  * Runtime: O(n)
*/
void SLDestroy(SortedListPtr list){
  // init varaibles
  Node freeNode;
  DestructFuncT destroy;
  Node node;

  // error checking if list is NULL
  if(list == NULL){
    return;
  }

  // set var values
  destroy = list->destroy;
  node = list->root;

  // Free All Nodes In LinkedList
  while(node != NULL){
    freeNode = node;
    node = node->nextNode;
    destroy(freeNode);
  }
}

/*
  Return 1 If newObj is not equal to any other items in the list and was inserted
  Return 0 If newObj is equal to an item already in the list or not successfully inserted
  Use Comparator Function
  Sort Descending Order: Largest = Root
  Runtime: O(n)
*/
int SLInsert(SortedListPtr list, void *newObj){
  // Init Variables
  int insert;
  CompareFuncT compare;
  Node current;
  Node previous;
  Node addNode;

  // Error Checking Return 0 If Parameter newObj Is Empty
  if(newObj == NULL || list == NULL){
    return 0;
  }

  // Set Variables
  compare = list->compare;
  current = list->root;
  previous = current;
  addNode = malloc(sizeof(Node));
  addNode->obj = newObj;
  addNode->count = 1;

  // Error Check If Memory Was allocated
  if(!addNode){
    return 0;
  }

  // Return 1 If First Node Added To SortedListIterator
  if(current == NULL){
    list->size = 1;
    addNode->nextNode = NULL;
    list->root = addNode;
    return 1;
  }

  // Iterate Through LinkedList
  while(current != NULL){
    insert = compare(current->obj, addNode->obj);
    // insert == 0 Then New Object Already Exists In LinkedList
    if(insert == 0){
      free(addNode);
      return 0;
    }
    // Compare == -1 Then New Object Is Greater Than Current Node
    else if(insert == -1){
      // Create New Root Node
      if(current == list->root){
        addNode->nextNode = current;
        list->root = addNode;
      } 
      // Fit In Between Two Nodes
      else {
        previous->nextNode = addNode;
        addNode->nextNode = current;
      }
      list->size++;
      return 1;
    } 
    // Compare == 1 Then New Object Is Less Than Current Node And Will Be Last
    else if(insert == 1 && current->nextNode == NULL){
      addNode->nextNode = NULL;
      current->nextNode = addNode;
      list->size++;
      return 1; 
    }

    // Update Node Positions To Iterate LinkedList
    previous = current;
    current = current->nextNode; 
  }

  return 0;
}

/* 
  Return 1 If newObj is found and free
  Return 0 If newObj is not found
  Use Destroy Function To Free Node
  Runtime: O(n)
*/
int SLRemove(SortedListPtr list, void *newObj){
  // init variables
  int delete;
  CompareFuncT compare;
  DestructFuncT destroy;
  Node current;
  Node previous;

  // error checking if parameters are valid
  if(list == NULL || newObj == NULL){
    return 0;
  }
  
  // set variables
  compare = list->compare; 
  destroy = list->destroy;
  current = list->root;
  previous = current;

  // error check if root it empty
  if(current == NULL){
    return 0;
  }

  // iterate linkedList
  while(current != NULL){
    delete = compare(current->obj, newObj); 
    //If delete == 0 then the node object value and searched for value are equal 
    if(delete == 0){
      current->count--;
      // Root Node
      if(current == list->root){
        list->root = current->nextNode;
      }
      // Last Node
      else if(current->nextNode == NULL){
        previous->nextNode = NULL;
      }
      // Regular Node
      else{
        previous->nextNode = current->nextNode;
      }

      //destroy(current);
      list->size--;
      return 1;
    }

    // Update Node Positions
    previous = current;
    current = current->nextNode;
  }

  return 0;
}


SortedListIteratorPtr SLCreateIterator(SortedListPtr list){
  SortedListIteratorPtr iterator = malloc(sizeof(SortedListIteratorPtr));
  // Return null if parameter is invalid or if memory could not be allocated
  if(list == NULL || !iterator){
    return NULL;
  }

  // Set the iterator to reference root node of list
  // increase reference count for node 
  iterator->ref = list->root;
  list->root->count++;

  return iterator;
}

void SLDestroyIterator(SortedListIteratorPtr iter){
  if(iter != NULL){
    free(iter);
  }
}

void *SLGetItem(SortedListIteratorPtr iter){
  // init variables
  void *dataObj;

  // Error check if parameter is invalid
  if(iter == NULL || iter->ref == NULL){
    return NULL;
  }

  // Set Node As The Reference from IteratorPtr and return value
  Node node = iter->ref;
  dataObj = node->obj;

  return dataObj; 
}

void *SLNextItem(SortedListIteratorPtr iter){
  // init variables
  void *dataObj = NULL;

  // Error Check if parameter is invalid
  if(iter == NULL){
    return NULL;
  }

  // Set Node as the reference from IteratorPtr
  Node node = iter->ref;
  // Set item as the object from node
  if(node->nextNode != NULL){
    dataObj = node->nextNode->obj;
  }
  // set next node for iterator and reduce count for current node
  iter->ref = node->nextNode;
  node->count--;
  if(iter->ref != NULL){
    iter->ref->count++;
  }

  return dataObj;
}
