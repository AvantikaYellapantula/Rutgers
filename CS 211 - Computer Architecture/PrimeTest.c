#include <stdio.h>
#include <stdlib.h>
 
int main(int argc, char **argv)
{
int num=atoi(argv[1]), i, passed=1;//passed: 1: true 0: false
//printf("%i",num);
if (num<=0||num=='\n') {printf("error\n");return 0;}

num=(int) num;

for(i=2;i<num;i++){
if(num%i==0){passed=0;}
}

if (passed==1){printf("yes\n");}
else {printf("no\n");}
	
  return 0;
}
