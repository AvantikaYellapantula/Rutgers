#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include "util.h"

void error(char *error){
  printf(RED "%s\n \t%s\n" RESET, error, strerror(errno));
  exit(0);
}
