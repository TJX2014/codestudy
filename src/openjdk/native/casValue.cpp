#include<stdio.h>

template<typename T>
T changeValue11(T exchange_value,
                                                T volatile* dest,
                                                T compare_value) {
  __asm__ volatile ("lock cmpxchgl %1,(%3)"
                    : "=a" (exchange_value)
                    : "r" (exchange_value), "a" (compare_value), "r" (dest)
                    : "cc", "memory");
  return exchange_value;
}

int main() {
  int exchange_value=10;
  int dest = 21;
  int compare_value = 20;
  changeValue11(exchange_value, &dest, compare_value);
  printf("%d", dest);
}