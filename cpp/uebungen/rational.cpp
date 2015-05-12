// Klasse für rationale Zahlen
// (Definition der Methoden)
//////////////////////////////
#include"rational.h"
#include<iostream>
#include<cassert>
using namespace std;

// Elementfunktionen
 
void Rational::add(const Rational& r) {
     zaehler = zaehler*r.nenner + r.zaehler*nenner;
     nenner  = nenner*r.nenner;
     kuerzen();
}
 
void Rational::sub(const Rational& s) {
     Rational r = s;
     r.zaehler *=-1;
     add(r);
}
 
void Rational::mult(const Rational& r) {
     zaehler = zaehler*r.zaehler;
     nenner  = nenner *r.nenner;
     kuerzen();
}

void Rational::div(const Rational& n) {
     Rational r = n;
     r.kehrwert();
     mult(r);
}

void Rational::set(long z, long n) {
     zaehler = z;
     nenner  = n;
     assert(nenner != 0);
     kuerzen();
}

void Rational::eingabe() {
     cout << "Zähler :";
     cin >> zaehler;
     cout << "Nenner :";
     cin >> nenner;
     assert(nenner != 0);
     kuerzen();
}
 
void Rational::ausgabe() const {
     cout << zaehler << "/" << nenner << endl;
}

void Rational::kehrwert() {
     long temp = zaehler;
     zaehler = nenner;
     nenner  = temp;
     assert(nenner != 0);
}

long ggt(long x, long y) {
    long rest;
    while (y > 0) {
       rest = x % y;
       x = y; 
       y = rest;
    }
    return x;
}

void Rational::kuerzen() {
     int sign = 1;
     if (zaehler < 0) { sign=-sign; zaehler = -zaehler;}
     if (nenner  < 0) { sign=-sign; nenner  = -nenner;}
     long teiler = ggt(zaehler, nenner);
     zaehler = sign*zaehler/teiler;
     nenner = nenner/teiler;
}


// globale Funktionen
const Rational add(const Rational& a, const Rational& b) {
   Rational r = a;
   r.add(b);
   return r;
}
 
const Rational sub(const Rational& a, const Rational& b) {
   Rational r = a;
   r.sub(b);
   return r;
}
 
const Rational mult(const Rational& a, const Rational& b) {
   Rational r = a;
   r.mult(b);
   return r;
}

const Rational div(const Rational& z, const Rational& n) {
   Rational r = z;
   r.div(n);
   return r;
}

// Aufgabe 4.1
const Rational add(long a, Rational& b) {
   Rational r = Rational(a,1);
   r.add(b);
   return r;
}
 
const Rational add(Rational& a, long b) {
   Rational r = Rational(b,1);
   a.add(r);
   return a;
}

// Aufgabe 4.2
void ausgabe_class(Rational& a) {
        cout << a.getZaehler() << "/" << a.getNenner() << " (class)" << endl;
}

// for debugging
int main() {
        long c=1;
        Rational a(1,2);
        
        a.ausgabe();
        Rational r1,r2;
        r1 = add(c,a);
        r2 = add(a,c);
        r1.ausgabe();
        r2.ausgabe();

        ausgabe_class(r2);

        return 0;
}

