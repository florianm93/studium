#include <iostream>


// self-definded sorting functions
template<typename T>
int descending(const T& left, const T& right) {
    return right<left;
}

template<typename T>
int ascending(const T& left, const T& right) {
    return right>left;
}
// end sorting functions

template<typename T>
struct Element {
    T value;
    Element *next;
    Element(T v, Element *n) : value(v), next(n) { }
};

template<typename T>
void insert(Element<T>* &rp, T v, int (*compare)(const T& left, const T& right)=ascending) {
    if (rp != NULL) {
        if (!compare(v,rp->value)) {    // Sortieren
            insert(rp->next, v, compare); // Rekursion
        } else {
            rp=new Element<T>(v, rp);
        }
    } else {
        rp=new Element<T>(v, NULL);
    }
}

template<typename T>
void remove(Element<T>* &rp, T v) {
    if (rp != NULL) {
        if (rp->value == v) {
            Element<T> *tmp=rp;
            rp = rp->next;
            delete tmp;
            remove(rp, v);
        } else remove(rp->next, v); // Rekursion
    }
}

template<typename T>
void print(Element<T> *p) {
    while(p) {
        std::cout << p->value << " ";
        p=p->next;
    }
    std::cout << std::endl;
}

int main(void) {
    Element<int>* head = NULL;
    insert(head, 2, descending);    // nothing for default *ascending*
    insert(head, 5, descending);
    insert(head, 1, descending);
    print(head);
    remove(head, 2);
    print(head);
}
