CC = clang++
CCFLAGS = -std=c++11 -pthread
ANZAHL = 10

run: all
	echo "... running ..."
	./aufgabe_b
	cd aufgabe_c/ && pwd && java PrimMain

all:
	echo "... compiling ..."
	$(CC) -DANZAHL=$(ANZAHL) $(CCFLAGS) -o aufgabe_b aufgabe_b.cpp
	javac aufgabe_c/Primzahl.java aufgabe_c/PrimMain.java

clean:
	rm -rf aufgabe_b
	rm -rf aufgabe_c/*.class
