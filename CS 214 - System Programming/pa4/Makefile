COMPILER = gcc
CCFLAGS = -Wall -g
all: main

main: main.o indexer.o tokenizer.o
	$(COMPILER) $(CCFLAGS) -o index main.o indexer.o tokenizer.o
main.o: main.c
	$(COMPILER) $(CCFLAGS) -c main.c -lm
tokenizer.o: tokenizer.c tokenizer.h
	$(COMPILER) $(CCFLAGS) -c tokenizer.c -lm
indexer.o: indexer.c indexer.h
	$(COMPILER) $(CCFLAGS) -c indexer.c -lm

clean:
	rm -rf *.o index
