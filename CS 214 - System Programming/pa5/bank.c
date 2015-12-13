#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include "bank.h"
#include "util.h"

#define MAX_ACCOUNTS 20

struct account accounts[20];
int total=0;

//Return -1 If Account Does Not Exist
//Return Index If Account Already Exists
int accountExists(char *name){
  //Initialize Variables
  int index;

  //Error Checking Parameter Values
  if(name == NULL){
    error("Invalid Parameter For accountExists(char*)");  
  }

  for(index = 0; index < MAX_ACCOUNTS; index++){
    if(strcmp(name, accounts[index].name) == 0){
      return index;
    }
  }

  return -1;
}

//Return 0 If Failed To Open Account
//return 1 If Success
int openAccount(char *name){
  //Initialize Variables
  int index;
  
  //Error Checking Parameter Values
  if(name == NULL){
    error("Invalid Parameter For openAccount(char*)");
  }
  
  if (total>=20){
	  error("Max accounts reached For openAccount(char*)");
  }

  if(accountExists(name) == -1){
	printf("Creating New Account\n");
	for(index = 0; index < MAX_ACCOUNTS; index++){
		if(accounts[index].name==NULL){
			accounts[index].name=name;
			accounts[index].balance=0;
			accounts[index].session=0;
			total=total+1;
			return 1;
		}
	}
  }

  return 0;
}

//Return 0 If Failed Session
//Return 1 If Success
int startSession(char *name){
  //Initialize Variables
  int index;
  
  //Error Checking Parameter Values
  if(name == NULL){
    error("Invalid Parameter For startSession(char*)");
  }

  if((index = accountExists(name)) != -1){
    printf("Start Session with %d %s\n", index, name);
    accounts[index].session = 1;
    return 1;
  }

  return 0;
}

//Return 0 If Failed To End
//Return 1 If Success
int endSession(char *name){
  //Initialize Variables
  int index;
  
  //Error Checking Parameter Values
  if(name == NULL){
    error("Invalid Parameter For endSession(char*)");
  }

  if((index = accountExists(name)) != -1){
    printf("Ending Session with %d %s\n", index, name);
    accounts[index].session = 0;
    return 1;
  }

  return 0;
}

//Return 0 If Failed To Credit
//Return 1 If Success
int creditAccount(char *name, float value){
  //Initialize Variables
  int index;

  //Error Check Parameter Values
  if(name == NULL || value <= 0.0f){
    error("Invalid Parameter For creditAccount(char*, float)");
  }

  if((index = accountExists(name)) != -1){
    printf("Crediting Value %f To %d %s\n", value, index, name);
    accounts[index].balance += value;
    return 1;
  }
  return 0;
}

//Return 0 If Failed To Debit
//Return 1 If Success
int debitAccount(char *name, float value){
  //Initialize Variables
  int index;

  //Error Check Parameter Values
  if(name == NULL || value <= 0.0f){
    error("Invalid Parameter For debitAccount(char *, float)");
  }

  if((index = accountExists(name)) != -1){
    if(accounts[index].balance < value){
      return 0;
    }
    
    printf("Debiting Value %f To %d %s\n", value, index, name);
    accounts[index].balance -= value;
    return 1;
  }
  return 0;
}

//Return Balance Value From Struct If Success
//Return -1.0f If Account Does Not Exist
float balanceAccount(char *name){
  //Initialize Variables
  int index;

  //Error Check Parameter Values
  if(name == NULL){
    error("Invalid Parameter For balanceAccount(char*)");
  }
 
  if((index = accountExists(name)) != -1){
    printf("Getting Balance For Account %d %s\n", index, name);
    return accounts[index].balance;
  }

  return -1.0f;
}
