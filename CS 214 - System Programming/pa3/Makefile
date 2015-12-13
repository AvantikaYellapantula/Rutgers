COMPILER = gcc
CCFLAGS = -Wall -g
all: main

main: main.o memory.o
	$(COMPILER) $(CCFLAGS) -o main main.o memory.o
main.o: main.c
	$(COMPILER) $(CCFLAGS) -c main.c -lm
memory.o: memory.c memory.h
	$(COMPILER) $(CCFLAGS) -c memory.c -lm
clean:
	rm -rf *.o main
