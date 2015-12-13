#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include "comb.h"

int main(int argc, char** argv){
	start=(struct block*)malloc(sizeof(struct block));
	start->type=0;
	
	if(argc <= 2){
		printf("error\n");
		exit(0);
	}
	
	RCircuit(argv[1]);
	RValues(argv[2]);
	buildCircuit();
	printf("\n\n");
		
	return 0;
}

 void RCircuit(char *f){
	FILE *file;
	char line[BUFSIZ];
	int j,k,m, pos, ps, num;
	char* input;
	char* output;
	char* cmd;
	char* cmdstring;
	char* nums;
	float z;
	struct block *head=start;
	
	file = fopen(f, "r");
	
	fgets(line, sizeof(line), file);
	
	ps = (int) line[9] - 48;
	input = (char*) malloc(ps * sizeof(char));
	pos=0;
	
	for(k = 11; k < sizeof(line); k++){
		if(line[k] == '\n')
			break;
		else if(line[k] != ' '){
			input[pos] = line[k];
			pos++;	
		}
	}
	
	for(k=0;k<50;k++){
		if (input[k]!='\0')
		invarC[k]=input[k];
	}
	
	fgets(line, sizeof(line), file);
	
	ps = (int) line[10] - 48;
	output = (char*) malloc(ps * sizeof(char));
	pos=0;
	for(k = 12; k < sizeof(line); k++){
		if(line[k] == '\n')
			break;
		else if(line[k] != ' '){
			output[pos] = line[k];
			pos++;	
		}
	}
	
	strncpy(start->input, &input[0], sizeof(input));start->input[sizeof(input)]='\0';
	strncpy(start->output, &output[0], sizeof(output));start->input[sizeof(output)]='\0';
	/*printf ("\nin: %s\nout: %s\n",input,output);*/
	
	while(fgets(line, sizeof(line), file) != NULL){
		struct block *cur=(struct block*)malloc(sizeof(struct block));
		ps = (int) line[0] - 48;
		cmd = (char*) malloc(ps * sizeof(char));
		ps = (int) line[1] - 48;
		cmdstring = (char*) malloc(ps * sizeof(char));
			
		for(k = 0; k < sizeof(line); k++){
			if(line[k] == '\n'||line[k] == ' ')
				break;
			else {
				cmd[k] = line[k];
			}
		}
		
		pos=0;		
		for(j = k+1; j < sizeof(line); j++){
			if(line[j] == '\n')
				break;
			else if(line[j] != ' '){
				cmdstring[pos] = line[j];
				pos++;	
			}
		}
		
		head->next=cur;
		cur->next=NULL;
		nums = (char*) malloc(1 * sizeof(char));
		strncpy(nums, &cmdstring[0], 1);nums[1]='\0';
		num=atoi(nums);
		if(!strcmp("NOT", cmd) > 0 || !strcmp("NOT", cmd) < 0){
			cur->type=1;
			strncpy(cur->input, &cmdstring[0], 1);cur->input[1]='\0';
			strncpy(cur->output, &cmdstring[1], 1);cur->output[1]='\0';
		}
		else if(!strcmp("OR", cmd) > 0 || !strcmp("OR", cmd) < 0){
			cur->type=2;
			strncpy(cur->input, &cmdstring[0], 2);cur->input[2]='\0';
			strncpy(cur->output, &cmdstring[2], 1);cur->output[1]='\0';
		}
		else if(!strcmp("AND", cmd) > 0 || !strcmp("AND", cmd) < 0){
			cur->type=3;
			strncpy(cur->input, &cmdstring[0], 2);cur->input[2]='\0';
			strncpy(cur->output, &cmdstring[2], 1);cur->output[1]='\0';
		}
		else if(!strcmp("DECODER", cmd) > 0 || !strcmp("DECODER", cmd) < 0){
			cur->type=4;
			strncpy(cur->input, &cmdstring[1], num);cur->input[num]='\0';
			strncpy(cur->output, &cmdstring[num+1], pow(2,num));cur->output[(int)floor(pow(2,num))]='\0';
		}
		else if(!strcmp("MULTIPLEXER", cmd) > 0 || !strcmp("MULTIPLEXER", cmd) < 0){
			cur->type=5;
			strncpy(cur->input, &cmdstring[1], num);cur->input[num]='\0';
			z=sqrt(num);
			
			if (num==2)
				m=1;
			else if (num==4)
				m=2;
			else if (num==8)
				m=3;
			else if (num==16)
				m=4;
			else if (num==32)
				m=5;
			else if (num==64)
				m=6;
			else if (num==128)
				m=7;
			else if (num==256)
				m=8;
			else if (num==512)
				m=9;
			else if (num==1024)
				m=10;
			
			strncpy(cur->selector, &cmdstring[num+1], m);cur->selector[m]='\0';
			strncpy(cur->output, &cmdstring[m+num+1], 1);cur->output[1]='\0';
		}
	
		head=cur;
	}
 }
 
 void RValues(char *f){
	FILE *file;
	char line[BUFSIZ];
	int k, pos, ps, lin;
	char* input;
	
	file = fopen(f, "r");
	
	lin=0;
	while(fgets(line, sizeof(line), file) != NULL){
		ps = (int) line[10] - 48;
		input = (char*) malloc(ps * sizeof(char));
		pos=0;
		for(k = 0; k < sizeof(line); k++){
			if(line[k] == '\n')
				break;
			else if(line[k] != ' '){
				invar[lin][pos] = line[k]-48;
				pos++;	
			}
		} 
		lin++;
	}
	start->size=lin;
 }
 
 void buildCircuit(){
	struct block *cur;
	char a,b,out;
	int run,i,r,check,k,temp,SIZE;
	
	for (run=0;run<start->size;run++){
		cur=start->next;
		
		while(cur!='\0'){
			/*printf("\nCMD[%i]: %i:%s__%s__%s",run,cur->type,cur->input,cur->selector,cur->output);*/
			switch(cur->type){
				case 1:
					a=(char)cur->input[0];
					out=(char)cur->output[0];
					if(getVar(a,run)==0){
					setVar(out,'1'-48,run);/*printf("1111111");*/}
					else{
			setVar(out,'0'-48,run);/*printf("0000000");*/}
					break;
				case 2:
					a=(char)cur->input[0];
					b=(char)cur->input[1];
					out=(char)cur->output[0];
				
					if(getVar(a,run)==1||getVar(b,run)==1)
						setVar(out,'1'-48,run);
					else
						setVar(out,'0'-48,run);
					break;
				case 3:
					a=(char)cur->input[0];
					b=(char)cur->input[1];
					out=(char)cur->output[0];
					
					if(getVar(a,run)==1&&getVar(b,run)==1)
						setVar(out,'1'-48,run);
					else
						setVar(out,'0'-48,run);
					break;
				case 4:
					temp = 0;
					for(i=0; i < 50; i++){
						if(!(cur->input[i] >= 'a' && cur->input[i] <= 'z') && !(cur->input[i] >= 'A' && cur->input[i] <= 'Z')){
								temp = i;
								i = 50;
						}
					}
					generateGrayCode(pow(2,temp), temp);
					
					for (i=0; i < pow(2,temp); i++){
						check=0;
						for (r=0; r < temp; r++){
							if (getVar(cur->input[r],run)==grayCode[i][r]){
							check++;}	
						}
						if (check>=temp-1){
							setVar((char)cur->output[0],(char)getVar(cur->input[check],run),run);
						}
						else
							setVar((char)cur->output[0],'0'-48,run);
					}
					
					break;
				case 5:
					temp = 0;
					for(i=0; i < 50; i++){
						if(!(cur->selector[i] >= 'a' && cur->selector[i] <= 'z') && !(cur->selector[i] >= 'A' && cur->selector[i] <= 'Z')){
								temp = i;
								i = 50;
						}
					}
					generateGrayCode(pow(2,temp), temp);
					
					for (i=0; i < pow(2,temp); i++){
						check=0;/*printf("%c",check,getVar(cur->selector[r],run),grayCode[i][r]);*/
						for (r=0; r < temp; r++){/*printf("***%i(%i:%c)***",check,getVar(cur->selector[r],run),grayCode[i][r]);*/
							if (getVar(cur->selector[r],run)==grayCode[i][r]){/*printf("(%i:%c)",getVar(cur->selector[r],run),grayCode[i][r]);*/
							check++;}	
						}
						if (check>=temp-1){
							setVar((char)cur->output[0],(char)getVar(cur->input[check],run),run);
							/*printf("|_%c_%i_|",cur->input[check],temp);*/
						}
						else
							setVar((char)cur->output[0],'0'-48,run);
					}
					break;
			}
			cur=cur->next;
		}
		/*for (k=0;k<50;k++){
			if (invarC[k]=='\0')
				break;
			printf("\nRUN: %i  invar[%i]: %c=%i",run,k,invarC[k],invar[run][k]);
		}*/
		SIZE=0;
		for (i=0;i<50;i++){
			if (start->output[i]=='\0')
				break;
			SIZE++;
		}
		for (i=0;i<50;i++){
			if (start->output[i]=='\0')
				break;
			for (k=0;k<50;k++){
				if (invarC[k]=='\0')
				break;
				if (getVar(invarC[k],run)==getVar(start->output[i],run)){
					printf("%i ",invar[run][k]);
					break;
				}
			}
			if (i==SIZE-1)
			printf("\n");
		}
	}
 }
 int getVar(char name, int run){
	 int k;
	 
	 for (k=0;k<50;k++){
		if (invarC[k]==name)
			return invar[run][k];
	}
	return 2;
 }
 
 void setVar(char name, char val, int run){
	 int k;
	 
	 for (k=0;k<50;k++){
		if (invarC[k]==name){
			invar[run][k]=val;
			return;
		}
		if (invarC[k]=='\0'){
			invarC[k]=name;
			invar[run][k]=val;
			invarC[k+1]='\0';
			return;
		}
	}
 }

void rGray(char* code, int n, int index){
	int w;
	if(n == 0){
		for(w = 0; w < sizeof(code); w++)
			grayCode[p][w] = code[w];
		
		p = p + 1;	
	}else{
		code[index] = '1';
		gray(code, (n-1), (index + 1));
		code[index] = '0';
		rGray(code, (n-1), (index + 1));
	}
}

void gray(char* code, int n, int index){
int w;
	if(n == 0){
		for(w = 0; w < sizeof(code); w++)
			grayCode[p][w] = code[w];
		
		p = p + 1;
	}else{
		code[index] = '0';
		gray(code, (n-1), (index + 1));
		code[index] = '1';
		rGray(code, (n-1), (index + 1));
	}
}

void generateGrayCode(int columns){
	int rows=pow(2,columns),w;
	char* tempGrayCode = (char*) malloc(sizeof(char) * columns);
	p = 0;
	grayCode = (char**) malloc(sizeof(char*) * rows);
	for(w = 0; w < rows; w++)
		grayCode[w] = (char*) malloc(sizeof(char) * columns);	

	gray(tempGrayCode, columns, 0);
}
 