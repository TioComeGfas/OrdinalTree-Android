//
// Created by Fredy Camilo Moncada Contreras on 1/18/21.
//

#ifndef LOUD_BITARRAY_H
#define LOUD_BITARRAY_H

class BitArray {
private:
    static const int WORD_SIZE = 64;
    long length;
protected:
    long* bits = nullptr;

public:
    /**
     * Crea un Array de bits.
     * @param size es la cantidad de bits que tiene el ubiobio.cl.bitArray.BitArray this.
     */
    BitArray(long size);

    /**
     * Permite conocer el valor 0 o 1 de la i-ésima posición del ubiobio.cl.bitArray.BitArray
     * @param pos
     * @return
     */
    bool getBit(int pos);

    /**
     * Pone en 1 el bits de la posición @pos del arreglo
     * @param pos
     */
    void setBit(int pos);

    /**
     * Pone en 0 o 1 dependiendo si b es falso o verdadero respectivamente
     * la posición pos del ubiobio.cl.bitArray.BitArray.
     * @param pos
     * @param b
     */
    void setBit(int pos, bool b);

    /**
     * Pone en 0 el bits de la posición pos del ubiobio.cl.bitArray.BitArray
     * @param pos
     */
    void clearBit(int pos);

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

    long* cloneBits();
};

#endif //LOUD_BITARRAY_H
