#include <stdio.h>
#include <ctype.h>
 
int main(int argc, char **argv)
{
  int num=atoi(argv[1]);

if (num<=0||num=='\n') {printf("error\n");return 0;}

    if (num%2==0){printf("even\n");}
	else if (num%2!=0){printf("odd\n");}
	
  return 0;
}
