gcc -c -DHAVE_EXPAT_CONFIG_H -DHAVE_MEMMOVE -DJNI -I/usr/lib/jvm/java-6-oracle/include -Iinclude -I../formatter/include -I../formatter/include/STIL -O0 -Wall -g3 -fPIC ../formatter/src/STIL/cJSON.c src/*.c
gcc *.o -shared -lexpat -laspell -o libAeseStripper.so
cp libAeseStripper.so /usr/local/lib
