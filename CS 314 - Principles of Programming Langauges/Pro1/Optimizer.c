/*
 *********************************************
 *  314 Principles of Programming Languages  *
 *  Fall 2015                                *
 *  Author: Ulrich Kremer                    *
 *  Student Version                          *
 *********************************************
 */

#include <assert.h>
#include <stdio.h>
#include <stdlib.h>
#include "InstrUtils.h"
#include "Utils.h"

void nextSub(Instruction *head, Instruction *back);

int main()
{
	Instruction *head;

	head = ReadInstructionList(stdin);
	if (!head) {
		WARNING("No instructions\n");
		exit(EXIT_FAILURE);
	}

	/* YOUR CODE GOES HERE */
	Instruction *cur;

	cur=head;
	while (cur != NULL){//goes through whole list 
		if (cur->opcode==OUTPUTAI){ //searches for all the "outputAI" 
			//printf ("**OUTPUTAI found 1:%d 2:%d 3:%d\n",cur->field1,cur->field2,cur->field3);
			nextSub(head, cur);
		}
		cur=cur->next;
	}
	
	if (head) 
		PrintInstructionList(stdout, head);
	
	return EXIT_SUCCESS;
}

void nextSub(Instruction *head, Instruction *cur){	
	Instruction *loop;
	loop=head;
	
	switch (cur->opcode){
	case 0://LOADI
		cur->critical=1;
		break;
	case 1://LOADAI
		while (loop){
			if (loop->critical==0 && loop->opcode==2 && cur->field1==loop->field2 && cur->field2==loop->field3){
				//checks for multiple assignments to the same var
				Instruction *check;
				check=loop->next;
				int found=0;
				
				while (check){
					if (check->opcode==2 && cur->field1==check->field2 && cur->field2==check->field3){
						found=1;
					}	
					check=check->next;
				}
				
				if (found==0){
					loop->critical=1;
					nextSub(head, loop);
				}
			}
			loop=loop->next;
		}
		break;
	case 2://STOREAI
		while (loop){
			if (loop->opcode==0 && cur->field2==loop->field2){
				loop->critical=1;
				nextSub(head, loop);
			}
			if ((loop->opcode==0 && cur->field1==loop->field2)|| 
				((loop->opcode==3 || loop->opcode==4 || loop->opcode==5 || loop->opcode==6) && cur->field1==loop->field3)){
				loop->critical=1;
				nextSub(head, loop);
			}
			loop=loop->next;
		}
		break;		 
	case 3://ADD 
		while (loop){
			if ((loop->opcode==0 && (cur->field1==loop->field2 || cur->field2==loop->field2))|| 
				((loop->opcode==1 || loop->opcode==3 || loop->opcode==4 || loop->opcode==5 || loop->opcode==6) && (cur->field1==loop->field3 || cur->field2==loop->field3))){
				loop->critical=1;
				nextSub(head, loop);
			}
			loop=loop->next;
		}
		break;
	case 4://SUB
		while (loop){
			if ((loop->opcode==0 && (cur->field1==loop->field2 || cur->field2==loop->field2))|| 
				((loop->opcode==1 || loop->opcode==3 || loop->opcode==4 || loop->opcode==5 || loop->opcode==6) && (cur->field1==loop->field3 || cur->field2==loop->field3))){
				loop->critical=1;
				nextSub(head, loop);
			}
			loop=loop->next;
		}
		break;
	case 5://MUL 
		while (loop){
			if ((loop->opcode==0 && (cur->field1==loop->field2 || cur->field2==loop->field2))|| 
				((loop->opcode==1 || loop->opcode==3 || loop->opcode==4 || loop->opcode==5 || loop->opcode==6) && (cur->field1==loop->field3 || cur->field2==loop->field3))){
				loop->critical=1;
				nextSub(head, loop);
			}
			loop=loop->next;
		}
		break;
	case 6://DIV
		while (loop){
			if ((loop->opcode==0 && (cur->field1==loop->field2 || cur->field2==loop->field2))|| 
				((loop->opcode==1 || loop->opcode==3 || loop->opcode==4 || loop->opcode==5 || loop->opcode==6) && (cur->field1==loop->field3 || cur->field2==loop->field3))){
				loop->critical=1;
				nextSub(head, loop);
			}
			loop=loop->next;
		}
		break;
	case 7://OUTPUTAI
		while (loop){
			if (loop->opcode==2 && cur->field1==loop->field2 && cur->field2==loop->field3){
				cur->critical=1;
				
				//checks for multiple assignments to the same var
				Instruction *check;
				check=loop->next;
				int found=0;
				
				while (check){
					if (check->opcode==2 && cur->field1==check->field2 && cur->field2==check->field3){
						found=1;
					}	
					check=check->next;
				}
				
				if (found==0){
					loop->critical=1;
					nextSub(head, loop);
				}
			}
			loop=loop->next;
		}
		break;
	}	
}
