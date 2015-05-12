#include <iostream>

void leerzeichenEntfernen(char* c) {
    int i,j;
    
    for(i=0,j=0; c[i]!='\0'; ++i) {
        if (c[i] != ' ') {
            c[j] = c[i];
            ++j;
        }
    }
    c[j] = '\0';
}

int main() {
    char str[] = "a bb ccc d\0";
    std::cout << str << std::endl;
    leerzeichenEntfernen(str);
    std::cout << str << std::endl;
}

