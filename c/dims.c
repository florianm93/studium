#include <stdio.h>

#define dim1(v) ( sizeof(v) / sizeof(v[0]) )
#define dim2(v) ( sizeof(v[0]) / sizeof(v[0][0]) )
#define dim3(v) ( sizeof(v[0][0]) / sizeof(v[0][0][0]) )

int main() {
    int a[2][3][4];
    
    int b[][2] = { {1, 1}, {2, 2} };

    printf("%ld\n", dim1(a));
    printf("%ld\n", dim2(a));
    printf("%ld\n", dim3(a));
        
    return 0;
}
