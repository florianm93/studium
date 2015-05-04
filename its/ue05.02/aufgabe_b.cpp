/*
 IT-Systeme, Uebungsblatt 05, Aufgabe 2b
 Florian Macherey
*/

#include <iostream>
#include <chrono>
#include <ctime>
#include <thread>
#include <iomanip>

#ifndef ANZAHL
#define ANZAHL 10       // Anzahl der Durchlaeufe einer Thread Variante um ein statistisch sinnvolles Ergebnis zu erhalten
#endif


void calc_cross_product_seriell(double *erg, double **m_ptr, double *vek, int n) {
    // Berechnen des Kreuzproduktes serielle Variante
    for (int i=0; i<n; ++i) {
        for (int j=0; j<n; ++j) {
            erg[j] += m_ptr[i][j] * vek[j];
        }
    }
}

void calc_cross_product_threaded(double *erg, double **m_ptr, double *vek, int n1, int n2) {
    // Berechnen des Kreuzproduktes multithreading Variante
    for (int i=n1; i<n2; ++i) {
        for (int j=n1; j<n2; ++j) {
            erg[j] += m_ptr[i][j] * vek[j];
        }
    }
}

int main() {
    int n,i,j,anz,k;
    double *vek, *mat, **m_ptr, *erg;
    std::thread *threads;
    std::chrono::time_point<std::chrono::system_clock> start, end, start2, end2;
    std::chrono::duration<double> seconds_took, seconds_took2;
    
    std::cout << "Dimension n der Matrix: ";
    std::cin >> n;
    
    
    vek = new double[n];
    m_ptr = new double*[n];
    erg = new double[n];
    
    // intialisieren von vektoren und matrizen mit Zufallswerten
    std::srand(std::time(0));
    for(i=0; i<n; ++i) {
        vek[i] = std::rand() / (double)RAND_MAX;
        erg[i] = 0.0;
        mat = new double[n];
        for (j=0; j<n; ++j) {
            mat[j] = std::rand() / (double)RAND_MAX;
        }
        m_ptr[i] = mat;
        
    }
    
    std::cout << "Zeiten sind avg fuer " << ANZAHL << " Durchlaeufe" << std::endl;
    // seriell
    start = std::chrono::system_clock::now();
    for (i=0; i<ANZAHL; ++i) {
        calc_cross_product_seriell(erg, m_ptr, vek, n);
    }
    end = std::chrono::system_clock::now();
    seconds_took = (end - start)/(double)ANZAHL;
    std::cout << "Zeit seriell: " << std::setw(10) << seconds_took.count() << std::endl;
    
    /// multithreaded
    int anz_threads[] = {2,3,4,10,20,50};
    for(i=0; i<6; ++i) {
        anz_threads[i]>n ? anz=n : anz=anz_threads[i];
        
        
        
        threads = new std::thread[anz];     // Array mit allen Threads
        start2 = std::chrono::system_clock::now();
        for (k=0; k<ANZAHL; ++k) {
            for (j=0; j<anz; ++j) {
                threads[j] = std::thread (calc_cross_product_threaded, erg, m_ptr, vek, n/anz*j, n/anz*(j+1)); // Berechnung in einzelne Teile zerlegen
            }
            ++j;
            if (n/anz*j < n) {
                threads[j] = std::thread (calc_cross_product_threaded, erg, m_ptr, vek, n/anz*j, n/anz*(j+1)); // Berechnung in einzelne Teile zerlegen, letzter Thread falls *n* und *anz* teilerfremd sind
            }
            
            for (j=0; j<anz; ++j) {
                threads[j].join();      // Hauptprozess auf das Ende der Threads warten lassen
            }
        }
    
        end2 = std::chrono::system_clock::now();
        seconds_took2 = (end2 - start2)/(double)ANZAHL;
        std::cout << "Zeit " << std::right << std::setw(5) << anz << " Threads: " << std::left << std::setw(10) << seconds_took2.count() << " Speedup: " << std::left << std::setw(10) << seconds_took/seconds_took2 << " Effizienz: " << std::left << std::setw(10) << (seconds_took/seconds_took2)/anz << std::endl;
        delete[] threads;

    }

    
    // alle vektoren freigeben
    delete[] vek;
    delete[] erg;
    for (i=0; i<n; ++i) {
        delete[] m_ptr[i];
    }
    delete[] m_ptr;
    
    return 0;
}