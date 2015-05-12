#include <iostream>
#include <fstream>

using namespace std;

int main(int argc, char* argv[]) {
    ifstream ifs;
    int i;
    string input;
    
    for (i=1; i<argc; ++i) {
        ifs.open(argv[i]);
        if(ifs.is_open()) {
            while (getline(ifs, input)) {
                cout << input << endl;
            }
            ifs.close();
        }
        else {
        cout << "Datei " << argv[i] << " nicht gefunden!" << endl;
        }
    }
    
    
    return 0;
}
