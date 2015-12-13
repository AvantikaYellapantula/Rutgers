#include <stdio.h>
#include <stdlib.h>
#include "hashTable.h"

int main(int argc, char **argv)
{	
Table=newTable(1000);

  FILE *file=fopen(argv[1], "r");
  char buf[1000];

	if (!file){
		printf ("\nerror");
		return 1;
	}
	
	while (fgets(buf,1000, file)!=NULL){
		char op=getI_d(buf);
		if (op=='i'){
			int stat=add(getNum(buf));
			if (stat==0)
				printf ("\ninserted");
			else if (stat==2)
				printf ("\nduplicate");
			else 
				printf ("\nerror");
		}
		else if (op=='s'){
			int stat2=find(getNum(buf));
			if (stat2==0)
				printf ("\npresent");
			else 
				printf ("\nabsent");
		}
		else {
			
		}
	}
	
	/*int i;
	for( i = 0; i < Table->size; i++ ) {
		if(Table->table[i]!=NULL)
		printf("\nindex %i is: %i", i, Table->table[i]->value);	
	}*/
	
	return 0;
}


int find(int n){
	int pos=n%Table->size;
	
	if (Table->table[pos]!=NULL && Table->table[pos]->value==n)
		return 0;
	
	return 1;
}
int add(int n){
	int number=n;
	int pos=n%Table->size;
	
	if (Table->table[pos]==NULL){
		struct entry *item=(struct entry*)malloc(sizeof(struct entry));
		item->value=number;
		Table->table[pos]=item;
		return 0;
	}
	else {
		while (Table->table[pos]!=NULL){
			n=n+1;
			pos=n%Table->size;
		}
		struct entry *item=(struct entry*)malloc(sizeof(struct entry));
		item->value=number;
		Table->table[pos]=item;
		return  2;
	}
	return 1;
}
struct HTable* newTable(int s){
	struct HTable *t=(struct HTable*)malloc(sizeof(struct HTable)); 
	t->size=s;
	
	t->table = malloc(sizeof(struct entry*)*s);

	int i;
	for( i = 0; i < s; i++ ) {
		t->table[i] = NULL;
	}
		
	return t;
}
char getI_d(char *buffer){
	if (buffer[0]=='i')
		return 'i';
	else if (buffer[0]=='s')
		return 's';
	else
		return 'e';
}
int getNum(char *buffer){
	char nums[1000];
	
	int i;for (i=2;i<100;i++){
		if (buffer[i]=='\0'){break;}
		nums[i-2]=buffer[i];
		}
		//printf("\ninput: %d",atoi(nums));
		int number=atoi(nums);
		int j;
		for (j=0;j<1000;j++)
			nums[j]=0;
		
		return number;
}