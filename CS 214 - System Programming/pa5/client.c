#include <stdio.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <ctype.h>
#include "client.h"
#include "util.h"

// Send String To Server
void sendRequest(char *request, int serverSocket){
  // Initialize Variables
  unsigned int response_length;
  char buff[BUFSIZ];
  int bytes_recieved;
  int total_bytes;

  // Error Checking Parameter
  if(request == NULL){
    error("Invalid Parameter Value For sendRequest(char*, int)");
  }

  response_length = strlen(request);

  printf(YELLOW "Sending Request To Server ");
  if(send(serverSocket, request, response_length, 0) != response_length){
    error("Error: Failed To Send To Server");
  }
  printf(GREEN "Success %s\n",request);

  //exits
  if (strcmp (request, "exit")){
	  exit(0);
  }
  
  total_bytes = 0;
  printf(YELLOW "Recieving Data From Server ");
  while(total_bytes < response_length){
    if((bytes_recieved = recv(serverSocket, buff, (BUFSIZ-1), 0)) <= 0){
      error("Error: Failed To Recieve From Server\n");
    }

    total_bytes += bytes_recieved;
    buff[bytes_recieved] = '\0'; 
  }
  printf(GREEN "Success\n");

  printf(YELLOW "Server Response: %s\n", buff);
}

// Return 0 If Compare and Input Are Equal
// Return -1 If Compare and Input Are Not Equal
int checkCommand(char *compare, char *input){
  //Initialize Variables
  int index;

  // Error Checking Parameters
  if(compare == NULL || input == NULL){
    error("Invalid Parameter Values For getCommand(char*, char*)");
  }

  // Return Invalid If Input Is Smaller Than Compare Value
  if(strlen(input) < strlen(compare)){
    return -1;
  }

  // Compare String Values
  for(index = 0; index < strlen(compare); index++){
    if(compare[index] != input[index]){
      return -1;
    }
  }

  return 0;
}

void command(int serverSocket){
  //Initialize Variables
  char input[BUFSIZ];
  int index;

  //Print Menu
  //printf(CLEAR);
  printf(BLUE "1. " YELLOW "open " CYAN "'accountname'\n");
  printf(BLUE "2. " YELLOW "start " CYAN "'accountname'\n");
  printf(BLUE "3. " YELLOW "credit " CYAN "'amount'\n");
  printf(BLUE "4. " YELLOW "debit " CYAN "'amount'\n");
  printf(BLUE "5. " YELLOW "balance\n");
  printf(BLUE "6. " YELLOW "finish\n");
  printf(BLUE "7. " YELLOW "exit\n");

  //Get Input Request
  printf(BLUE "Please Enter Command: " YELLOW);
  if(fgets(input, BUFSIZ, stdin) == NULL)
    error("Empty Input");

  //convert all characters to lower case
  for(index = 0; index < strlen(input); index++){
    int ascii = (int) input[index];
    if(ascii >= 65 && ascii <= 90){
      input[index] = tolower(ascii);
    }
  }

  //Error Checking Each Command
	char *sendVAR;
	strcpy(sendVAR, input);
	char *value;
	value = strtok(input, " ");
	int len = strlen(value)-1;
	//printf("CMD %s, size: %d\n", value, len); 
  if(checkCommand("open", value) == 0 && len==3){
    value = strtok(NULL, " ");
	//printf("Account to open %s\n", value); 
  }else if(checkCommand("start", value) == 0 && len==4){
	value = strtok(NULL, " ");
	//printf("Account to start %s\n", value); 
  }else if(checkCommand("credit", value) == 0 && len==5){
    value = strtok(NULL, " ");
	//printf("Value To Credit %s\n", value); 
  }else if(checkCommand("debit", value) == 0 && len==4){
	value = strtok(NULL, " ");
	//printf("Value To Debit %s\n", value);  
  }else if(checkCommand("balance", value) == 0 && len==7){
      
  }else if(checkCommand("finish", value) == 0 && len==6){
      
  }else if(checkCommand("exit", value) == 0 && len==4){
      
  }else { error("Invalid Input\n"); return;}
  
  strcat (input, " ");
 //strcat(input, value);
  printf ("\n\n***%s***\n\n",sendVAR);
  sendRequest(sendVAR, serverSocket);
}

int main(int argc, char **argv){
  //Initialize Variables
  int serverSocket;
  struct sockaddr_in server_Address;
  unsigned short server_port;
  char *server_IP;

  //Error Checking Arguments
  if(argc < 3){
    error("Error: Too Few Arguments");
  }

  server_IP = argv[1];
  server_port = atoi(argv[2]);

  if(server_port <= 1024){
    error("Error: Ports 0-1024 Are Reserved For Root Process");
  }

  printf(YELLOW "Opening Socket ");
  serverSocket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP); 
  if(serverSocket < 0){
    error("Error: Failed To Open Socket");
  }
  printf(GREEN "Success\n");

  server_Address.sin_family = AF_INET;
  server_Address.sin_addr.s_addr = inet_addr(server_IP);
  server_Address.sin_port = htons(server_port);
 
  printf(YELLOW "Connecting To Server ");
  while(connect(serverSocket, (struct sockaddr*) &server_Address, sizeof(server_Address)) < 0){
    printf(RED "Connection Failed\n");
    clock_t start = clock();
    clock_t diff = clock() - start;
    int msec = diff * 1000 / CLOCKS_PER_SEC;
    while(msec < 3000){
      diff = clock() - start;
      msec = diff * 1000 / CLOCKS_PER_SEC;
    }
  }
  printf(GREEN "Conection Success\n");
  
  while(1){
    command(serverSocket);
  }

  close(serverSocket);
  return 0;
}
