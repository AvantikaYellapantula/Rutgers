#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include "server.h"
#include "util.h"

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

void response(int socket){
  char buff[BUFSIZ];
  int recieved;
  char *response = "Server Request Accepted";

 while(1){
	char *curACC=" "; //active account
    //received some request from client
	printf(YELLOW "Received Data From Client ");
    if((recieved = recv(socket, buff, BUFSIZ, 0)) < 0){
      printf("Error: Failed To Receive Response\n");
      exit(0);}
    printf(GREEN "Success\n");

	//handling request
    printf(YELLOW "Client Request " BLUE "%s\n", buff);
	
	char *value;
	value = strtok(buff, " ");
	int len = strlen(value)-1;
	if(checkCommand("open", value) == 0 && len==3){
		value = strtok(NULL, " ");
		if (openAccount(value)==0){
			response="ERROR: Account NOT opened";}
		else {
			response="Account opened: ";
			strcat(response, value); }
	}else if(checkCommand("start", value) == 0 && len==4){
		value = strtok(NULL, " ");
		stpcpy(curACC, value);//must save this account name
		if (startSession(curACC)==0){
			response="ERROR: Account NOT started";}
		else {
			response="Account Started: ";
			strcat(response, value); }
	}else if(checkCommand("credit", value) == 0 && len==5){
		value = strtok(NULL, " ");
		if (creditAccount(curACC, 30)==0){
			response="ERROR: Amount NOT added";}
		else {
			response="Amount added: ";
			strcat(response, value); }
	}else if(checkCommand("debit", value) == 0 && len==4){
		value = strtok(NULL, " ");
		if (debitAccount(curACC, 20)==0){
			response="ERROR: Amount NOT removed";}
		else {
			response="Amount removed: ";
			strcat(response, value); }
	}else if(checkCommand("balance", value) == 0 && len==7){
		float total;
		total=balanceAccount(curACC);
		response="Account balance is: ";
		//strcat (response, total);
	}else if(checkCommand("finish", value) == 0 && len==6){
		if (endSession(curACC)==0){
			response="ERROR: Account NOT ended";}
		else {
			response="Account ended: ";
			strcat(response, value); }
	}else if(checkCommand("exit", value) == 0 && len==4){
		response="exiting";
		//closing socket
	}else {response="Invalid Input";}
	//printf("%s", response); 
	
	//Sending answer back to client
    printf(YELLOW "Sending Data Back To Client ");
    if(send(socket, response, strlen(response), 0) != strlen(response)){
      printf("Error: Failed To Send Response\n");
      exit(0);
    }
    printf(GREEN "Success\n"); 
  }
}

int main(int argc, char **argv){
  // Initialize Variables
  char *ipAddress;
  int serverSocket;
  int clientSocket;
  struct sockaddr_in server_Address;
  struct sockaddr_in client_Address;
  unsigned short server_port;
  unsigned int client_length;

  // Error Checking For Arguments
  if(argc < 3){
    error("Error: Not Enough Arguments");
  }

  ipAddress = argv[1];
  server_port = atoi(argv[2]);
  
  // Error Checking Argument Value For Port Number
  if(server_port <= 1023){
    error("Error: Ports 1-1023 Are Reserved For Root Processes");
  }

  printf(YELLOW "Arguments IP: " BLUE "%s"  YELLOW " Socket: " BLUE "%s\n", argv[1], argv[2]);

  printf(YELLOW "Opening Socket For Server ");
  serverSocket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);
  if(serverSocket < 0){
    error("Error: Failed To Open Socket");
  }
  printf(GREEN "Success\n");

  memset(&server_Address, 0, sizeof(server_Address));
  server_Address.sin_family = AF_INET;
  server_Address.sin_addr.s_addr = inet_addr(ipAddress);
  server_Address.sin_port = htons(server_port);

  printf(YELLOW "Binding Server Address To Socket ");
  if(bind(serverSocket, (struct sockaddr *) &server_Address, sizeof(server_Address)) < 0){
    error("Error: Failed To Bind Local Address IP");
  }
  printf(GREEN "Success\n");

  printf(YELLOW "Listening To Socket ");
  if(listen(serverSocket, 5) < 0){
    error("Error: Failed To Listen To Port");
  }
  printf(GREEN "Success\n");

  while(1){
    client_length = sizeof(client_Address);
    printf(YELLOW "Listening For Request");
    if((clientSocket = accept(serverSocket, (struct sockaddr *) &client_Address, &client_length)) < 0){
      error("Error: Failed To Accept Client Request");      
    }
    printf(GREEN " Success\n");

    printf(YELLOW "Handling Client" BLUE " %s\n", inet_ntoa(client_Address.sin_addr));
    response(clientSocket);
  }

  close(clientSocket);
  return 0;
}
