CC= gcc
FLAGS= -Wall -g

all: server client

server: server.o bank.o util.o
	$(CC) $(FLAGS) -o server server.o bank.o util.o

server.o: server.h server.c
	$(CC) $(FLAGS) -c server.c -lm

client: client.o util.o
	$(CC) $(FLAGS) -o client client.o util.o

client.o: client.h client.c
	$(CC) $(FLAGS) -c client.c -lm

bank.o: bank.h bank.c 
	$(CC) $(FLAGS) -c bank.c util.c -lm

util.o: util.h util.c
	$(CC) $(FLAGS) -c util.c

clean:
	rm -rf *.o 
	rm -f server
	rm -f client
