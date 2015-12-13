#ifndef TOKENIZER_H
#define TOKENIZER_H

#include <stdlib.h>
#include <stdio.h>
#include <dirent.h>
#include <sys/stat.h>
#include <string.h>

void tokenizeLine(char *line, char *file_Name);
void readFile(char *file_Name);
void readDirectory(char *dir);
void testPrint();
void writeHashTable(char *fileName);

#endif
