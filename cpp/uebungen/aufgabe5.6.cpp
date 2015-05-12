#include <iostream>
#include <cctype>
#include <fstream>
#include <cstring>

using namespace std;

int main() {
    ifstream ifs;
    string word;
    int i;
    bool firstAlpha, otherAlpha;
    
    ifs.open("aufgabe5.6.inp");
    
    while(ifs) {
        ifs >> word;
        firstAlpha = false;
        otherAlpha = true;
        if(isalpha(word[0]) || word[0] == '_') {
            firstAlpha = true;
        }
        for (i=1; i<word.length(); ++i) {
            if(!(isalpha(word[i])) && !(isnumber(word[i])) && (word[i]!='_')) {
                otherAlpha = false;
            }
        }
        if (firstAlpha && otherAlpha) {
            cout << word << endl;
        }
    }
    
    return 0;
}

