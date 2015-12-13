#ifndef hashTable   
#define hashTable

//Structures 
struct entry {
	int value;
	struct entry *next;
};

struct HTable {
	int size;
	struct entry **table;	
};

//Global variables 
struct HTable *Table;

//Methods
int find(int n);
int add(int n);
char getI_d(char *buffer);
int getNum(char *buffer);
struct HTable* newTable(int s);

#endif 