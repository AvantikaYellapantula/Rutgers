#ifndef comb
#define comb

void RCircuit(char *f);
void RValues(char *f);
void buildCircuit();
int getVar(char name, int run);
void setVar(char name, char val, int run);
void generateGrayCode(int columns);
void gray(char* code, int n, int index);
void reverseGray(char* code, int n, int index);

struct block{
	int type;
	struct block *next;
	char input[50];
	char output[50];
	char selector[50];
	int size;
};

char invarC[50];
int invar[50][50];
int finalvar[50];
struct block *start;
char** grayCode;
int p;
#endif