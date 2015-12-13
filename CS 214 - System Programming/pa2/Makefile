COMPILER = gcc
CCFLAGS = -Wall -g
all: exec lib

exec: main.o sorted-list.o
	$(COMPILER) $(CCFLAGS) -o main main.o sorted-list.o

lib: sorted-list.o main.o
		ar rc libsl.a sorted-list.o main.o
main.o: main.c
		$(COMPILER) $(CCFLAGS) -c main.c -lm
sorted-list.o: sorted-list.c sorted-list.h
		$(COMPILER) $(CCFLAGS) -c sorted-list.c -lm
clean:
		rm -rf *.o libsl.a 
