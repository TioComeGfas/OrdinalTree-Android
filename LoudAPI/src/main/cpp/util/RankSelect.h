//
// Created by Fredy Camilo Moncada Contreras on 1/16/21.
//

/**
 * Este codigo fue traducido de Java para C++
 * Grupo de investigación ALBA,
 * Proyecto 2030 ...
 * @author Luis Gajardo, Miguel Romero y Fernando Santolaya
 * Basado en
 * BitSecuenceRG de libcds Autor Francisco Claude
 * https://github.com/fclaude/libcds/blob/master/src/static/bitsequence/BitSequenceRG.cpp
 */

#ifndef LOUD_RANKSELECT_H
#define LOUD_RANKSELECT_H

#include <cstdint>
#include "BitArray.h"
#include "../exceptions/IndexOutOfBoundsException.h"

using namespace std;

class RankSelect {
private:
    static const int WORD_SIZE= 64;
    /** mask for obtaining the first 5 bits */
    static const long mask63 = 63l;
    jlong length;
    jint factor;
    jint s;
    jlong ones;
    jlongArray bits;
    jlongArray Rs; //arreglo de superBlock
    JNIEnv* env; //maquina virtual de Java

public:

    /**
     * Crea un Array de bits estático con soporte para las
     * operaciones de rank y select.
     * @param ba
     */
    RankSelect(JNIEnv* env, BitArray* bitArray);

    /**
     * Crea un Array de bits estático con soporte para las
     * operaciones de rank y select.
     * @param env maquina virtual de java para las operaciones de este mismo
     * @param ba, bit array a clonar, sobre el cual se opera
     * @param factor factor con el cual se determina la redundancia de la estructura
     *               si factor=2, redundancia 50%
     *                  factor=3, redundancia 33%
     *                  factor=4, redundancia 25%
     *                  factor=20, redundancia 5%;
     */
    RankSelect(JNIEnv* env, BitArray* bitArray, int factor);

    long numberOfOnes();

    /**
     * Permite conocer el valor 0 o 1 de la i-ésima posición del ubiobio.cl.bitArray.BitArray
     * @param pos
     * @return
     */
    bool access(long pos);

    /**
     * retorna la cantidad de unos que hay hasta pos inclusive.
     * @param pos
     * @return
     * @throws IndexOutOfBoundsException, si pos esta fuera del rango [0..length)
     */
    long rank1(long pos);

    /**
     * retorna la posición en el bit array en la que ocurre el i-ésimo uno.
     * @param i
     * @return
     */
    long select1(long i);

    /**
     * retorna la cantidad de ceros que hay hasta pos inclusive.
     * @param pos posición hasta donde se quiere contar la cantidad de ceros.
     * @return total de ceros hasta la posicion pos
     */
    long rank0(long pos);

    /**
     * retorna la posición en el bit array en la que ocurre el i-ésimo cero.
     * @param i número ordinal del cero buscado.
     * @return posición del i-esimo cero
     */
    long select0(long i);

    /**
     * retorna el menor indice i>=start tal que access(i) es true;
     * @param start
     * @return posición en el bitArray del 1 siguiente o igual a start.
     *         En el caso de no haber un 1 posterior retorna el largo
     *         en bits de la secuencia.
     */
    long selectNext1(long start);

    /**
     * retorna el mayor indice i<=start tal que access(i) es true;
     * @param start
     * @return retorna la posición i del 1 previo a start.  Si no
     * hay un 1 previo a la posción start retorna  -1.
     */
    long selectPrev1(long start);

    /**
     * retorna el menor indice i>start tal que access(i) es false;
     * @param start
     * @return
     */
    long selectNext0(long start);

    /**
     * retorna el mayor indice i<start tal que access(i) es false;
     * @param start
     * @return
     */
    long selectPrev0(long start);

    /**
     * Retorna la cantidad de bits en el ubiobio.cl.bitArray.BitArray.
     * @return
     */
    long getLength();

    /**
     * Retorna el tamaño del ubiobio.cl.bitArray.BitArray en byte.
     * @return
     */
    long size();

    char* toString();

    long numberOfZeroes();

private:
    void buildRank();

    long BuildRankSub(int ini, int bloques);

    int countBits(long value);

    int lenghArray();

    int trailZeros(long value);

    int leadingZeros(long x);
};

#endif //LOUD_RANKSELECT_H