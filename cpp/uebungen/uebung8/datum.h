// NOCH NICHT VOLLST�NDIG (s. �bungsaufgaben)
#ifndef DATUM_H
#define DATUM_H
#include<string>
#include<ostream>

class Datum {
  public:
    Datum();                         // Default-Konstruktor
    Datum(int t, int m, int j = 1997); // allgemeiner Konstruktor
    void set(int t, int m, int j); // Datum setzen
    void aktuell();                  // Systemdatum setzen
    bool istSchaltjahr() const;
    Datum& operator++();             // Tag hochz�hlen, pr�fix
    Datum operator++(int);           // Tag hochz�hlen, postfix
    int tag()   const;
    int monat() const;
    int jahr()  const;

    // besser: durch Methode string toString() ersetzen!
    // (siehe �bungsaufgabe)
    operator std::string() const;         // Typumwandlungsoperator
  private:
     int tag_, monat_, jahr_;
};

std::ostream& operator<<(std::ostream& stream, Datum& d);       // Aufgabe 8.6

bool istSchaltjahr(int jahr); // Impl. s.u.

// inline-Methoden
inline Datum::Datum(int t, int m, int j) { set(t, m, j);}

inline int Datum::tag()   const { return tag_;  }
inline int Datum::monat() const { return monat_;}
inline int Datum::jahr()  const { return jahr_; }
inline bool Datum::istSchaltjahr() const {
  return ::istSchaltjahr(jahr_); 
}
 
// global
bool istGueltigesDatum(int t, int m, int j);

inline bool istSchaltjahr(int jahr)  {
   return  (jahr % 4 == 0 && jahr % 100) || jahr % 400 == 0;
}


#endif  


