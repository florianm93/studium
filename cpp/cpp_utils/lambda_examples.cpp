#include<iostream>

int main() {
    double x = 1, y = 2, z = 3;

    auto l1 = [] (double a, double b, double c) -> double {
        return a+b+c;
    };

    auto l2 = [&] (int w) -> bool {
        return (w>=x) && (w<=z);
    };

    auto l3 = [&]() {
        z = -z;
    };



    std::cout << x << " + " << y << " + " << z << " = " << l1(x,y,z) << std::endl; 
    std::cout << "ist im interval: " << l2(2) << std::endl;
    std::cout << "ist im interval: " << l2(5) << std::endl;
    std::cout << "vorher: " << z;
    l3();
    std::cout << " nachher: " << z << std::endl;
    
    return 0;
}
