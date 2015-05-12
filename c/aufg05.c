#include <stdio.h>

#ifndef TYPE
#define TYPE int
#endif

int main() {
    TYPE i,z = getchar();


    for(i=sizeof(TYPE)*8-1; i>= 0; --i) {
        if(z&(1<<i)) {
            putchar('1');
        }
        else {
            putchar('0');
        }

        if(i%4 == 0) {
            putchar(' ');
        }
    }

    return 0;
}
