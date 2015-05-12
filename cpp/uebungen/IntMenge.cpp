#include "IntMenge.h"
#include <vector>
#include <cstddef>
#include <iostream>

using namespace std;

IntMenge::IntMenge(IntMenge& copy) {
    size_t i;
    for(i=0; i<copy.size(); ++i) {
        this->values.push_back(copy.values[i]);
    }
}

void IntMenge::hinzufuegen(int el) {
        if(!istMitglied(el)) {
                values.push_back(el);
        }
}

void IntMenge::entfernen(int el) {
        size_t i;
        int index=-1;
        for(i=0; i<values.size(); ++i) {
                if(el == values[i]) {
                        index=i;
                        break;
                }
        }
        if(index>=0) {
                values.erase(values.begin()+index);
        }
}

bool IntMenge::istMitglied(int el) {
        size_t i;
        for(i=0; i<values.size(); ++i) {
                if (el == values[i]) {
                        return true;
                }
        }
        return false;
}

size_t IntMenge::size(void) {
        return values.size();
}

void IntMenge::anzeigen() {
        size_t i;
        std::cout << "( ";
        for (i=0; i<values.size(); ++i) {
            std::cout << values[i] << ",";
        }
        std::cout << "\b) " << endl;
}

void IntMenge::loeschen() {
        values.clear();
}

int IntMenge::getMin() {
        int min = values[0];
        size_t i;
        for(i=0; i<values.size(); ++i) {
                if(values[i] < min) {
                        min = values[i];
                }
        }
        return min;
}

int IntMenge::getMax() {
        int max = values[0];
        size_t i;
        for(i=0; i<values.size(); ++i) {
                if(values[i] > max) {
                        max = values[i];
                }
        }
        return max;
}

