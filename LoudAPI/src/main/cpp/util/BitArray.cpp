//
// Created by Fredy Camilo Moncada Contreras on 1/18/21.
//

#include <android/log.h>
#include "BitArray.h"
#include "../exceptions/IndexOutOfBoundsException.h"

#define APP_NAME "module_loud jni"
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR,APP_NAME,__VA_ARGS__)

BitArray::BitArray(JNIEnv* env, jlong size) {
    this->env = env;
    this->length = (int)(size / WORD_SIZE + 1);
    bits = this->env->NewLongArray(length); //se constuye un arreglo con ceil(length/word_size) bloques.
}

BitArray::BitArray(JNIEnv *env, jlongArray array, jlong size) {
    this->env = env;
    this->bits = array;
    this->length = (int)(size / WORD_SIZE + 1);
}

jboolean BitArray::getBit(jint pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    auto valueA = (jlong) this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), pos / WORD_SIZE);
    jlong valueB = (1l << (pos % WORD_SIZE));
    return (valueA & valueB) != 0;
}

void BitArray::setBit(jint pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jlong* array = this->env->GetLongArrayElements(bits, (jboolean *)false);

    jint positionAux = pos / WORD_SIZE;
    jlong block = array[positionAux];
    jlong mask = (1l << (pos % WORD_SIZE));
    block |= mask;
    array[positionAux] = block;

    int size = this->env->GetArrayLength(bits);
    this->env->SetLongArrayRegion(bits,0,length,array);
}

void BitArray::setBit(jint pos, jboolean b) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jlong* array = this->env->GetLongArrayElements(bits, (jboolean *)false);

    jint positionAux = pos / WORD_SIZE;
    jlong block = array[positionAux];
    jlong mask = (long) (1l << (pos % WORD_SIZE));
    if (b) {
        block |= mask;
    } else {
        block &= ~mask;
    }
    array[positionAux] = block;

    this->env->SetLongArrayRegion(bits,0,length,array);
}

void BitArray::clearBit(jint pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jlong* array = this->env->GetLongArrayElements(bits, (jboolean *)false);

    jint positionAux = pos / WORD_SIZE;
    jlong block = array[positionAux];
    jlong mask = (long) (1l << (pos % WORD_SIZE));
    block &= ~mask;
    array[positionAux] = block;

    int size = this->env->GetArrayLength(bits);
    this->env->SetLongArrayRegion(bits,0,size,array);
}

jlong BitArray::getLength() {
    return length;
}

jlong BitArray::size() {
    //8 por variable this.length
    //4 por bits.length
    //8 por la referencia a bits (pensando en arquitectura de 64 bits, peor caso).

    return ((sizeof(bits)/sizeof(long)) * WORD_SIZE) / 8+8+4+8;
}

jcharArray BitArray::toString() {
    jcharArray out = this->env->NewCharArray(length*WORD_SIZE +1);
    jchar* array = this->env->GetCharArrayElements(out,(jboolean*)false);

    for (int i = 0; i < (length*WORD_SIZE +1); i++) {
        array[i] = getBit(i) ? '1' : '0';

        if(array[i] == '1') LOG_E("VALOR[%i]= %c",i, array[i]);
    }

    this->env->SetCharArrayRegion(out,0,length,array);
    return out;
}

jlongArray BitArray::cloneBits() {
    LOG_E("CLONE BITS");
    jlongArray copia = env->NewLongArray(length);

    jlong* array = this->env->GetLongArrayElements(bits, (jboolean *)false);

    env->SetLongArrayRegion(copia,0,length,array);
    return copia;
}

jlongArray BitArray::getBitArray() {
    return this->bits;
}

jint BitArray::getRandom(jint min, jint max) {
    return rand() % max + min;
}