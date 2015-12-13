#include <stdio.h>
#include <stdlib.h>
#include "Matrix.h"
 
int main(int argc, char **argv)
{	
  FILE *file=fopen(argv[1], "r");
  char buf[1000];

	if (!file)
		return 1;
	
	//gets the size
	fgets(buf,1000, file);
	getSize(buf);
		
	//gets the fist matrix 
	int j;
	for (j=0;j<n;j++){
		fgets(buf,1000, file);
		getNumA1(buf, j);
	}
	
	fgets(buf,1000, file);//takes out the extra space
	
	//gets the second matrix 
	for (j=0;j<n;j++){
		fgets(buf,1000, file);
		getNumA2(buf, j);
	}
	/*
	//prints the frist matrix
	printf("\n\nMatrix 1:");
	int k;
	for (j=0;j<m;j++){
		printf("\n");
		for (k=0;k<n;k++){
			printf("%i ",a1[j][k]);
		}
	}
	
	//prints the second matrix
	printf("\n\nMatrix 2:");
	for (j=0;j<m;j++){
		printf("\n");
		for (k=0;k<n;k++){
			printf("%i ",a2[j][k]);
		}
	}
	*/
	//prints the answer
	add();
	//printf("\n\nAnswer:");
	int k;
	for (j=0;j<m;j++){
		for (k=0;k<n;k++){
			printf("%i ",answer[j][k]);
		}
		printf("\n");
	}
	fclose(file);
  return 0;
}
void add(){
	int k, j;
	for (j=0;j<m;j++){
		for (k=0;k<n;k++){
			answer[j][k]=a1[j][k]+a2[j][k];
		}
	}
}
void getSize(char *buffer){
	char Mnums[10];
	char Nnums[10];
	
	int i;for (i=0;i<100;i++){
		if (buffer[i]=='\0'){break;}
		Mnums[i]=buffer[i];
		Nnums[i]=buffer[i+2];
		}
		m=atoi(Mnums);
		n=atoi(Nnums);
}
void getNumA1(char *buffer, int line){
	char nums[10];
	int j, k=0, letterNum=0;
	for (j=0;j<10;j++){
		if (buffer[j]!=' ' && buffer[j]!='\n' && buffer[j]!='\t' && buffer[j]!='\0'){
		//printf("\n%i_%c;",j,buffer[j]);
		nums[k]=buffer[j];k++;
		}
		else if (buffer[j]==' ' || buffer[j]=='\n' || buffer[j]=='\t'){
			//printf("\n%i_%i;",j,atoi(nums));
			a1[line][letterNum]=atoi(nums);
			k=0;
			letterNum++;
			int g;
			for (g=0;g<10;g++)
				nums[g]=0;
		}
	}
}
void getNumA2(char *buffer, int line){
	char nums[10];
	int j, k=0, letterNum=0;
	for (j=0;j<10;j++){
		if (buffer[j]!=' ' && buffer[j]!='\n' && buffer[j]!='\t' && buffer[j]!='\0'){
		//printf("\n%i_%c;",j,buffer[j]);
		nums[k]=buffer[j];k++;
		}
		else if (buffer[j]==' ' || buffer[j]=='\n' || buffer[j]=='\t' || buffer[j]=='\0'){
			//printf("\n%i_%i;",j,atoi(nums));
			a2[line][letterNum]=atoi(nums);
			k=0;
			letterNum++;
			int g;
			for (g=0;g<10;g++)
				nums[g]=0;
		}
	}
}