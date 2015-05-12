#include<iostream>

double eval(double (*f)(double), double x) {
    return f(x);
}

int main() {
    double x = 4.0;

    auto f_sqr = [](double x) -> double {
        return x*x;
    };
    
    std::cout << "f(" << x << ") = " << eval(f_sqr, x) << std::endl;

        
    return 0;
}
