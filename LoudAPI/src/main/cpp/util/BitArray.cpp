//
// Created by Fredy Camilo Moncada Contreras on 1/18/21.
//

#include "BitArray.h"
#include "../exceptions/IndexOutOfBoundsException.h"

BitArray::BitArray(JNIEnv* env, jlong size) {
    this->env = env;
    this->length = size;
    bits = this->env->NewLongArray((int)(size / WORD_SIZE + 1)); //se constuye un arreglo con ceil(length/word_size) bloques.
}

BitArray::BitArray(JNIEnv *env, jlongArray array, jlong size) {
    this->env = env;
    this->length = size;
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

    int positionAux = pos / WORD_SIZE;
    jlong block = (jlong) this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux);
    jlong mask = (1l << (pos % WORD_SIZE));
    block |= (long) mask;
    this->env->SetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux, reinterpret_cast<jobject>(block));
}

void BitArray::setBit(jint pos, jboolean b) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jint positionAux = pos / WORD_SIZE;
    jlong block = (jlong) this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux);
    jlong mask = (long) (1l << (pos % WORD_SIZE));
    if (b) {
        block |= mask;
    } else {
        block &= ~mask;
    }
    this->env->SetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux, reinterpret_cast<jobject>(block));  //bits[pos / WORD_SIZE] = block;
}

void BitArray::clearBit(jint pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jint positionAux = pos / WORD_SIZE;
    jlong block = (jlong) this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux);
    jlong mask = (long) (1l << (pos % WORD_SIZE));
    block &= ~mask;
    this->env->SetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), positionAux, reinterpret_cast<jobject>(block)); //bits[pos / WORD_SIZE] = block;
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
    jcharArray out = this->env->NewCharArray(length);
    for (int i = 0; i < length; i++) {
        env->SetObjectArrayElement(reinterpret_cast<jobjectArray>(out), i,reinterpret_cast<jobject>(getBit(i) ? '1' : '0'));
    }
    return out;
}

jlongArray BitArray::cloneBits() {
    jlongArray copia = env->NewLongArray(length);

    for(int i=0; i < length; i++){
        env->SetObjectArrayElement(reinterpret_cast<jobjectArray>(copia), i, env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), i));
    }

    return copia;
}

jlongArray BitArray::getBitArray() {
    return this->bits;
}

jint BitArray::getRandom(jint min, jint max) {
    return rand() % max + min;
}