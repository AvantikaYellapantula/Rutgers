#include "tokenizer.h"
#include "indexer.h"

// Read Every File That Exists In The Target Directory and Sub-Directories
void readDirectory(char *dir){
  // Initialize Variables
  DIR *directory;
  struct dirent *entity;
  struct stat s;

  // Error Checking Parameter Value
  if(dir == NULL){
    printf("Error: Invalid Parameter Value\n");
    return;
  }

  //Error Check If Root Directory Folder Exists
  directory = opendir(dir);
  if(directory == NULL){
    printf("Error: Root Directory Folder Does Not Exist\n");
    return;
  }

  //Traverse The Directory
  entity = readdir(directory);
  while(entity != NULL){
    char *subdir = malloc(strlen(entity->d_name) + strlen(dir) + 2);
    strcpy(subdir, dir);
    strcat(subdir, "/");
    strcat(subdir, entity->d_name);

    if(strcmp(entity->d_name, ".") != 0 && strcmp(entity->d_name, "..") != 0){
      if(stat(subdir, &s) == 0){
        if(s.st_mode & S_IFDIR){
          readDirectory(subdir);
        }else if(s.st_mode & S_IFREG){
          //For Debug Only
          if(subdir[(strlen(subdir)-1)] == 't'){ //Remove Once Everything Is Tested
            readFile(subdir);
          }
        }
      }
    }
    entity = readdir(directory);
  }

  //Close Directory
  closedir(directory);
}

// Reads A Single File
// Tokenizes Each Line By Line
// Inserts Tokens Into Data Structure
void readFile(char *file_Name){
  // Initialize Variables
  FILE *file;
  char line[BUFSIZ];

  // Error Check If Parameter Is Valid
  if(file_Name == NULL){
    printf("Error: Invalid File Name\n");
    return;
  }

  // Open File
  file = fopen(file_Name, "r");

  // Error Check If File Exists
  if(file == NULL){
    printf("Error: Could Not Open File\n ");
    return;
  }

  // Read File Line By Line
  while(fgets(line, BUFSIZ, file) != NULL){
    tokenizeLine(line, file_Name);
  }

  // Close File
  fclose(file);
}

// Find Tokens Within The Line And Insert Into DataStructure
void tokenizeLine(char *line, char *file_Name){
  // Initialize Variables
  int index = 0;
  int startIndex = -1;
  int endIndex = -1;
  char *token;

  // Error Checking For Valid Parameter Values
  if(line == NULL || file_Name == NULL){
    printf("Error: Invalid Parameter Value\n");
    return;
  }

  // Valid Characters (0-9)/\(a-z)/\(A-Z)
  // ASCII Values: (48-57)/\(97-122)/\(65-90)
  while(index < strlen(line)){
    int ascii = ((int) line[index]);

    // Capital Letters Converted Into LowerCase Letters
    if(ascii >= 65 && ascii <= 90){
      line[index] = tolower(line[index]);
      if(startIndex == -1)
        startIndex = index;
    }else if(ascii >= 97 && ascii <= 122){ // LowerCase Letters
      if(startIndex == -1)
        startIndex = index;
    }else if(ascii >= 48 && ascii <= 57){ // Numbers
      if(startIndex == -1)
        startIndex = index;
    }else if(startIndex != -1){ // Invalid Character Is A Separator: Insert Current startIndex-endIndex Into DataStructure
      endIndex = (index-1);
      token = (char*) malloc(endIndex-startIndex + 1);
      memcpy(token, &line[startIndex], (endIndex-startIndex + 1));
      //printf("Token: %s\n", token);
      insertToken(token, file_Name);
      startIndex = -1;
      endIndex = -1;
    }

    index++;
  }
}

void testPrint(){
  printHashTable();
}

void writeHashTable(char *fileName){
  writeJSONFile(fileName);
}

