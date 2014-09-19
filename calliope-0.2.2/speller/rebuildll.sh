gcc -c -DJNI -Iinclude -I/usr/lib/jvm/java-6-oracle/include -O0 -Wall -g3 -fPIC src/*.c
gcc *.o -shared -L/usr/local/lib -laspell -o libAeseSpeller.so
mv libAeseSpeller.so /usr/local/lib/
rm *.o
