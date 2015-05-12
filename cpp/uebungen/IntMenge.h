#ifndef INTMENGE_H_INCLUDED
#define INTMENGE_H_INCLUDED

#include <vector>
#include <cstddef>

class IntMenge{
        public:
                IntMenge(IntMenge& copy);
                IntMenge() { };
                virtual ~IntMenge() { };
                void hinzufuegen(int el);
                void entfernen(int el);
                bool istMitglied(int el);
                size_t size(void);
                void anzeigen(void);
                void loeschen(void);
                int getMax();
                int getMin();
        private:
                std::vector<int> values;
};

#endif
