#include <iostream>
#include "IntMenge.h"

int main() {
    IntMenge menge;
    int i;

    menge.hinzufuegen(2);
    menge.hinzufuegen(-9);
    menge.hinzufuegen(2);

    menge.anzeigen();

    menge.entfernen(99);
    menge.entfernen(-9);

    menge.anzeigen();
    menge.loeschen();
    menge.anzeigen();

    for(i=17; i<33; ++i) {
        menge.hinzufuegen(i*i);
    }
    std::cout << "Anzahl=" << menge.size() << " Minimum=" << menge.getMin() << " Maximum=" << menge.getMax() << std::endl;
    menge.anzeigen();
    if(menge.istMitglied(289)) {
        std::cout << "289 is a member" << std::endl;
    }
    ////IntMenge mengeB(menge);
    //mengeB.anzeigen();

    return 0;
}
