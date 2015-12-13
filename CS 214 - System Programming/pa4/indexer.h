#ifndef INDEXER_H
#define INDEXER_H

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct node{
  char *token;
  char *file_Name;
  unsigned int frequency;
  struct node *nextToken;
  struct node *prevToken;
  struct node *nextFile;
  struct node *prevFile;
};

typedef struct node* Node;

//26 Characters + 10 Digits = array size of 36
void insertToken(char *token, char *file_Name);
void sortRow(int ascii, Node fileNode);
void printHashTable();
void writeJSONFile(char *fileName);

#endif

