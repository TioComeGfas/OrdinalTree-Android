//
// Created by Fredy Camilo Moncada Contreras on 1/18/21.
//

#include "BitArray.h"
#include "../exceptions/IndexOutOfBoundsException.h"

BitArray::BitArray(long size) {
    this->length = size;
    //se constuye un arreglo con ceil(length/word_size) bloques.
    bits = new long[(int)(size / WORD_SIZE + 1)];
}

bool BitArray::getBit(int pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    return (bits[pos / WORD_SIZE] & (1l << (pos % WORD_SIZE))) != 0;
}

void BitArray::setBit(int pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    long block = bits[pos / WORD_SIZE];
    long mask = (long) (1l << (pos % WORD_SIZE));
    block |=  mask;
    bits[pos / WORD_SIZE] = block;
}

void BitArray::setBit(int pos, bool b) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    long block = bits[pos / WORD_SIZE];
    long mask = (long) (1l << (pos % WORD_SIZE));
    if (b) {
        block |= mask;
    } else {
        block &= ~mask;
    }
    bits[pos / WORD_SIZE] = block;
}

void BitArray::clearBit(int pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: "[ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    long block = bits[pos / WORD_SIZE];
    long mask = (long) (1l << (pos % WORD_SIZE));
    block &= ~mask;
    bits[pos / WORD_SIZE] = block;
}

long BitArray::getLength() {
    return length;
}

long BitArray::size() {
    //8 por variable this.length
    //4 por bits.length
    //8 por la referencia a bits (pensando en arquitectura de 64 bits, peor caso).

    return ((sizeof(bits)/sizeof(long)) * WORD_SIZE) / 8+8+4+8;
}

char *BitArray::toString() {
    char* out = new char[length];
    for (int i = 0; i < length; i++) {
        out[i] = getBit(i) ? '1' : '0';
    }
    return out;
}

long *BitArray::cloneBits() {
    return nullptr;
}


