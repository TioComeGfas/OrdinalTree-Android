//
// Created by Fredy Camilo Moncada Contreras on 1/18/21.
//

#ifndef LOUD_BITARRAY_H
#define LOUD_BITARRAY_H

#include <jni.h>

class BitArray {
public:
    static const jint WORD_SIZE = 64;

private:
    jlong length; // cantidad de nodos del arreglo
    JNIEnv* env; // variable global de java

protected:
    jlongArray bits = nullptr; //arreglo de bits

public:
    /**
     * Crea un Array de bits.
     * @param size es la cantidad de bits que tiene el BitArray this.
     */
    BitArray(JNIEnv* env, jlong size);

    /**
     * Crea un Array de bits.
     * @param size es la cantidad de bits que tiene el BitArray this.
     * @param array
     */
    BitArray(JNIEnv* env,jlongArray array, jlong size);

    /**
     * Permite conocer el valor 0 o 1 de la i-ésima posición del ubiobio.cl.bitArray.BitArray
     * @param pos
     * @return
     */
    jboolean getBit(jint pos, jint element);

    /**
     *
     * @param position
     * @param element
     */
    void setBit(jint position, jint element);

    /**
     * Pone en 1 el bits de la posición @pos del arreglo
     * @deprecated
     * @param pos
     */
    void setBit(jint pos);

    /**
     * Pone en 0 o 1 dependiendo si b es falso o verdadero respectivamente
     * la posición pos del BitArray.
     * @param pos
     * @param b
     */
    void setBit(jint pos, jboolean b);

    /**
     * Pone en 0 el bits de la posición pos del ubiobio.cl.bitArray.BitArray
     * @param pos
     */
    void clearBit(jint pos);

    /**
     * Retorna la cantidad de bits en el ubiobio.cl.bitArray.BitArray.
     * @return
     */
    jlong getLength();

    /**
     * Retorna el tamaño del ubiobio.cl.bitArray.BitArray en byte.
     * @return
     */
    jlong size();

    jcharArray toString();

    jlongArray cloneBits();

    /**
     * Retorna el arreglo de bits que fue generado con anterioridad
     * @return bits
     */
    jlongArray getBitArray();

    jint getRandom(jint min, jint max);
};

#endif //LOUD_BITARRAY_H