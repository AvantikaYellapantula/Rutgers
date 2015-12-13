#ifndef SERVER_H
#define SERVER_H

void initServer();
void response();
int openAccount(char *name);
int startSession(char *name);
int endSession(char *name);
int creditAccount(char *name, float value);
int debitAccount(char *name, float value);
float balanceAccount(char *name);

#endif
