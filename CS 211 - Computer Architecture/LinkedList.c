#include <stdio.h>
#include <stdlib.h>
 
 struct node {
	int data;
	struct node *next;
};
void add(int n);
void removes(int n);
char getI_d(char *buffer);
int getNum(char *buffer);
 
struct node *root;
 
int main(int argc, char **argv)
{	
  FILE *file=fopen(argv[1], "r");
  char buf[1000];

	if (!file){
		printf ("error");
		return 1;
	}

	while (fgets(buf,1000, file)!=NULL){
		char op=getI_d(buf);
		if (op=='i'){
			add(getNum(buf));
		}
		else if (op=='d'){
			removes(getNum(buf));
		}
		else {
			printf("\nerror");
		}
	}
	struct node *runner=root;
	printf("\n\nFINAL:");
	while(runner!=NULL){
		printf("\t%d",runner->data);
		runner=runner->next;
	}
	
	fclose(file);
	
  return 0;
}
void removes(int n){
	
	if (root->data==n){
		root=root->next;
		return;
	}
	struct node *runner=root;
	struct node *temp=runner;
	
	//goes through the LL and removes n
	while(runner->next!='\0'){	
		if (runner->data==n){
			temp->next=runner->next;
		}
		temp=runner;
		runner=runner->next;
	}
}
void add(int n){
	
	if (root == NULL){
		root = (struct node*) malloc(sizeof(struct node));
		root->data=n;
		root->next=NULL;
		return;
	}
		struct node *cur = root;
		struct node *prev = (struct node*) malloc(sizeof(struct node));
		
		while(cur != NULL){
			if(cur->data == n)return;
			
			if(cur == root && n < cur->data){//less then cur and before root
				struct node *NN = (struct node*) malloc(sizeof(struct node));
				NN->data=n;
				NN->next=root;
				root = NN;
				return;
			}
			else if(n < cur->data){//less then cur
				struct node *NN = (struct node*) malloc(sizeof(struct node));
				NN->data=n;
				NN->next = cur;
				prev->next =NN;
				return;
			}
			else if(n > cur->data && cur->next == NULL){//greater then cur 
				struct node *NN = (struct node*) malloc(sizeof(struct node));
				NN->data=n;
				NN->next=NULL;
				cur->next=NN;
				return;
			}
			 
			prev = cur;
			cur = cur->next;
		}
	
}
char getI_d(char *buffer){
	if (buffer[0]=='i')
		return 'i';
	else if (buffer[0]=='d')
		return 'd';
	else
		return 'e';
}
int getNum(char *buffer){
	char nums[1000];
	
	int i;for (i=2;i<100;i++){
		if (buffer[i]=='\0'){break;}
		nums[i-2]=buffer[i];
		}
		printf("\ninput: %d",atoi(nums));
		int number=atoi(nums);
		int j;
		for (j=0;j<1000;j++)
			nums[j]=0;
		
		return number;
}