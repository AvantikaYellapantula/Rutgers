#ifndef BANK_H
#define BANK_H

struct account{
  char *name;
  float balance;
  int session;
};

int accountExists(char *name);
int openAccount(char *name);
int startSession(char *name);
int endSession(char *name);
int creditAccount(char *name, float value);
int debitAccount(char *name, float value);
float balanceAccount(char *name);

#endif
