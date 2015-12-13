#include "tokenizer.h"
//#include "indexer.h"


//Execute ./index "Inverted File Name" "File To Read or Root Dir"
int main(int argc, char **argv){
  struct stat s;  
  char *inverted_File_Name;
  char *file_Name;

  // Error Checking
  // Argument Check For Inverted-Index File Name
  // Argument Check For directory or file name
  if(argc != 3){
    printf("Error\n");
    exit(0);
  }

  // Set Argument Variables
  inverted_File_Name = argv[1];
  file_Name = argv[2];
  printf("Argument 1: %s Argument 2: %s \n", inverted_File_Name, file_Name);

  // Check If Reading One File Or An Entire Directory
  // Otherwise The Argument Does Not Exist
  if(stat(file_Name, &s) == 0){
    if(s.st_mode & S_IFDIR){ // Argument Is A Directory
      readDirectory(file_Name);
    }else if(s.st_mode & S_IFREG){ // Argument Is A File
      readFile(file_Name);
    }
  }else{
    printf("Error: File Or Directory Does Not Exist\n");
    return 0;
  }

  //Testing Function
  //testPrint();

  //Write HashTable Tree Into JSON File Format
  FILE *f;
  char buffer[500];
  f = fopen(inverted_File_Name, "w");
  fseek (f,0,SEEK_END);
  fread(buffer,500,1,f);
  printf("FILE: ", buffer);//list
  writeHashTable(inverted_File_Name);

  return 0;
}
