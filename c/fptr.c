#include <stdio.h>
#include <math.h>

int gt(int l, int r) {
    return l>r;

}

int main() {
    int i;
    double x = M_PI/2;
    double (*f[10])(double) = {sin, cos, tan, asin, acos, atan};

    for(i=0; i<10; ++i) {
        if(*f[i] != NULL) {
            printf("%f\n",f[i](x) );
        }
    }

    int a=5,b=3,c=2;
    int (*cmp)(int, int) = gt;
    printf("%d\n", cmp(a,b) );
    printf("%d\n", cmp(c,b) );


}
