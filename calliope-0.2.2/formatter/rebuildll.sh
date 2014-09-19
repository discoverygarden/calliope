gcc -c -DHAVE_EXPAT_CONFIG_H -DHAVE_MEMMOVE -DJNI -I/usr/lib/jvm/java-6-oracle/include -Iinclude -Iinclude/STIL -Iinclude/AESE -O0 -Wall -g3 -fPIC src/*.c src/AESE/*.c src/STIL/*.c
gcc *.o -shared -o libAeseFormatter.so
mv libAeseFormatter.so /usr/local/lib/
rm *.o
