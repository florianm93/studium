#include<iostream>
#include<chrono>
#include<thread>

void f(int num) {
    //std::cerr << "Thread " << num << " startet" << std::endl;
    std::this_thread::sleep_for(std::chrono::seconds(num));
    std::cerr << "Thread " << num << " ist beendet" << std::endl;
}

int main() {
    std::thread t1(f, 1);
    std::thread t2(f, 2);
    std::thread t3(f, 3);
    std::cerr << "Thread 1 id: " << t1.get_id() << std::endl;
    std::cerr << "Thread 2 id: " << t2.get_id() << std::endl;
    std::cerr << "Thread 3 id: " << t3.get_id() << std::endl;
    t1.join();
    t2.join();
    t3.join();
    
    std::cerr << "Thread 1 id:" << t1.get_id() << std::endl;
    std::cerr << "main() wird beendet" << std::endl;

    return 0;
}
