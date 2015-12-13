#include "indexer.h"

// Token HashTable
// [0] - [9] Tokens That Start With The Numbers 0-9
// [10] - [35] Tokens That Start With The Letters a-z
struct node *hashTable[36];

// Set Values of nodes In the HashTable Array
void insertToken(char *token, char *file_Name){
  char checkIndex;
  int ascii;
  struct node *insert;
  struct node *tokenNode;

  // Error Checking Parameters
  if(token == NULL || file_Name == NULL){
    printf("Error: Invalid Parameter Values\n");
    return;
  }

  //Obtain The First Character In String Token And Get ASCII Value
  //So We Can Calculate The Index Range For The HashTable
  checkIndex = token[0];
  ascii = ((int) checkIndex);
  if(ascii >= 48 && ascii <= 57){ //Insert Number Into Range [(0-9)]
    ascii -= 48;
  }else{ //Insert Character Into Range [(10-35)]
    ascii -= 87; 
  }

  //Search Token In HashTable To Update Frequency
  //Create Node If Does Not Exist
  insert = (struct node*) malloc(sizeof(struct node));
  if(!insert){
    printf("Error: Not Able to Allocate Memroy For New Node\n");
    return;
  }

  //Initialize Insert
  insert->token = (char*) malloc(sizeof(char) * (strlen(token)+1));
  insert->file_Name = (char*) malloc(sizeof(char) * (strlen(file_Name)+1));
  insert->frequency = 1;
  insert->nextToken = NULL;
  insert->prevToken = NULL;
  insert->nextFile = NULL;
  insert->prevFile = NULL;
  strcpy(insert->token, token);
  strcpy(insert->file_Name, file_Name);
 
  tokenNode = hashTable[ascii];

  if(tokenNode == NULL){
    //printf("Creating Head %s\n", insert->token);
    hashTable[ascii] = insert;
  }else{
    while(tokenNode != NULL){ // Traverse LinkedList
      int compare = strcmp(insert->token, tokenNode->token); // Compare Token Values Of Existing Nodes In LinkedList
      if(compare == 0){ // Tokens Are Equals
        compare = strcmp(insert->file_Name, tokenNode->file_Name); // Compare File Name Values of Existing Nodes in LinkedList
        if(compare == 0){ // File Names Are Equal
          tokenNode->frequency++; // Update Frequency In Token and Exit Function
          return;
        }else{ // Traverse The FileNode LinkedList To Find A Node Or Insert A New One
          struct node *fileNode = tokenNode->nextFile;
          if(fileNode == NULL){ // Check For Head Of FileNode And Create If Does Not Exist
            insert->prevFile = tokenNode;
            tokenNode->nextFile = insert;
            return;
          } 
          while(fileNode != NULL){ // Traverse The FileNodes
            compare = strcmp(insert->file_Name, fileNode->file_Name); // Compare FileName Values
            if(compare == 0){ // File Names Are the Same, Update Frequency and Sort LinkedList
              fileNode->frequency++;
              sortRow(ascii, fileNode);
            }else if(compare != 0 && fileNode->nextFile == NULL){ //Add The FileNode To The End Of The List
              insert->prevFile = fileNode;
              fileNode->nextFile = insert;
              return; 
            }
            fileNode = fileNode->nextFile;
          }
        }
        return;
      }else if(compare < 0){ // Insert Before Current Node
        if(tokenNode == hashTable[ascii]){ // Replace Head Node
          //printf("Replacing Head Node With %s\n", insert->token);
          struct node *temp = hashTable[ascii];
          hashTable[ascii] = insert;
          insert->nextToken = temp;
          temp->prevToken = insert;
          return;
        }else{ //Insert Node
          //printf("Inserting %s\n", insert->token);
          struct node *temp = tokenNode->prevToken;
          temp->nextToken = insert;
          insert->prevToken = temp;
          insert->nextToken = tokenNode;
          tokenNode->prevToken = insert;
          return;
        }
      }else if(tokenNode->nextToken == NULL){
        //printf("Inserting Tail %s\n", insert->token);
        tokenNode->nextToken = insert;
        insert->prevToken = tokenNode;
        return;
      }
      tokenNode = tokenNode->nextToken;
    }
  }
}

// Rehash The File Nodes For Each Token, To Keep Highest Frequency As Head Node
void sortRow(int ascii, Node fileNode){
  //Initialize Variables
  struct node *temp;

  //Error Checking Parameter Values
  if(ascii < 0 || fileNode == NULL){
    printf("Error: Invalid Parameter Values\n");
    return;
  }

  temp = fileNode->prevFile;
  while(temp != NULL){
    if(fileNode->frequency < temp->frequency){
      //Connect The Previous And Next Nodes of fileNode
      fileNode->prevFile->nextFile = fileNode->nextFile;
      if(fileNode->nextFile != NULL){
        fileNode->nextFile->prevFile = fileNode->prevFile;
      }else{ //Prevent SegmentFault If fileNode Is Tail  Node
        fileNode->nextFile->prevFile = NULL;
      }

      if(temp == hashTable[ascii]){ //Replace HeadNode In HashTable
        fileNode->prevFile = NULL;
        fileNode->nextFile = hashTable[ascii];
        fileNode->nextToken = hashTable[ascii]->nextToken;
        hashTable[ascii]->prevFile = fileNode;
        hashTable[ascii]->nextToken = NULL;
        hashTable[ascii] = fileNode; 
      }else if(temp->prevFile == NULL && (temp->prevToken != NULL || temp->nextToken != NULL)){ //Replace TokenNodes
        fileNode->prevToken = temp->prevToken;
        fileNode->nextToken = temp->nextToken;
        fileNode->prevFile = NULL;
        fileNode->nextFile = temp;
        temp->prevFile = fileNode;
        temp->prevToken = NULL;
        temp->nextToken = NULL;
        if(fileNode->prevToken != NULL){
          fileNode->prevToken->nextToken = fileNode;
        }
        if(fileNode->nextToken != NULL){
          fileNode->nextToken->prevToken = fileNode;
        }
      }else{ //Regular Node
         
      }
      return;
    }
    temp = temp->prevFile; 
  }
} 

//Test Function
void printHashTable(){
  printf("Printing HashTable\n");
  struct node *tokenNode;
  int i;
  for(i=0; i < 36; i++){
    tokenNode = hashTable[i];
    while(tokenNode != NULL){
      printf("TokenNode Token %s File %s Frequency %d\n", tokenNode->token, tokenNode->file_Name, tokenNode->frequency);
      struct node *fileNode = tokenNode->nextFile;
      while(fileNode != NULL){
        printf("FileNode Token %s File %s Frequency %d \n", fileNode->token, fileNode->file_Name, fileNode->frequency);
        fileNode = fileNode->nextFile;
      }
      tokenNode = tokenNode->nextToken;
    }
  }
}

//Write HashTable To JSON File
void writeJSONFile(char *fileName){
  printf("Writing JSON File\n");
    FILE *f;
	struct node *tokenNode;
    f = fopen(fileName, "w");
    fprintf(f, "{\"list\" : [\n");//list
	
	int i;
	for(i=0; i < 36; i++){
		if (hashTable[i] != NULL){
			tokenNode = hashTable[i];
			while(tokenNode != NULL){
				fprintf(f, "\t{\"%s\" : [\n", tokenNode->token);//hash index
				struct node *fileNode = tokenNode;
				while(fileNode != NULL){
					if (fileNode->nextFile!=NULL)
						fprintf(f, "\t\t{\"%s\" : %d},\n", fileNode->file_Name, fileNode->frequency);//linked list nodes
					else
						fprintf(f, "\t\t{\"%s\" : %d}\n", fileNode->file_Name, fileNode->frequency);//linked list nodes
					fileNode = fileNode->nextFile;
				}				
				
				int k, end=0;
				for (k=i+1;k<36;k++){
					if (hashTable[k]!=NULL)
						end=1;
				}
				if (tokenNode->nextToken == NULL && end==0)
					fprintf(f, "\t]}\n");//hash index end
				else 
					fprintf(f, "\t]},\n");//hash index end
				tokenNode=tokenNode->nextToken;
			}
		}
	}
	fprintf(f, "]}\n");//list end
    fclose(f);

}
