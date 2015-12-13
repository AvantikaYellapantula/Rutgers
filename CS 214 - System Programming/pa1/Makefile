COMPILER = gcc
CCFLAGS = -Wall -g
all: tokenizer

tokenizer: tokenizer.o
	$(COMPILER) $(CCFLAGS) -o tokenizer tokenizer.o -lm
tokenizer.o: tokenizer.c tokenizer.h
	$(COMPILER) $(CCFLAGS) -c tokenizer.c -lm
clean:
	rm -f tokenizer tokenizer.o
