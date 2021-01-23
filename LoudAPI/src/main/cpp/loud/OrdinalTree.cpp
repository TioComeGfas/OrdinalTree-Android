//
// Created by fredy on 15-01-2021.
//

#include "OrdinalTree.h"

OrdinalTree::OrdinalTree() { }

bool OrdinalTree::build(int countNodes) {
    if(countNodes <= 0){
        return false;
    }

    /**
     * hay que ingresar todos los valores dentro de esta estructura
     * para posteriormente procesar los valores con rank y select
     */
    auto* bitArray = new BitArray(countNodes);

    int cantidadNodosContruidos = 0;
    int posicionBitVector = 0;
    int cantidadBitsDisponibles = 32;
    auto* bitvector = (unsigned int*) malloc(sizeof(unsigned int)); //cuanto espacio le asigno ??

    //inserto el nodo ficticio
    bitvector[0] = bitvector[0] & 0x80000000; // 10000000000000000000000000000000

    // le resto la cantidad de bits disponibles
    // me quedan 30 bits disponibles para utilizar
    cantidadBitsDisponibles -= 2;

    while(cantidadNodosContruidos < countNodes){ //bucle hasta que termine
        int countHijos = getRandom(1,50);

        // SI LOS HIJOS CABEN EN LA POSICION SI INSERTA EN ESA
        if( (countHijos + 1) <= cantidadBitsDisponibles){
            unsigned int vectorTemp = (int) pow(2, countHijos) - 1;
            vectorTemp = vectorTemp << (unsigned)(cantidadBitsDisponibles - countHijos);
            bitvector[posicionBitVector] += vectorTemp;

            cantidadBitsDisponibles -= (countHijos + 1);

            if(cantidadBitsDisponibles == 0) posicionBitVector++;
            cantidadNodosContruidos++; //se creo un nuevo nodo
        }else{
            //hay que divivir la cantidad de bits a insertar
            // dejando en 2 partes:
            // la primera, con la cantidad de bits disponibles en la posicion x
            // la segunda, con la cantidad de bits restantes en la posicion x + 1
            int countHijosA = cantidadBitsDisponibles;
            unsigned int vectorTempA = (int) pow(2, countHijosA) - 1;

            vectorTempA = vectorTempA << (unsigned)(countHijosA);
            bitvector[posicionBitVector] += vectorTempA;

            posicionBitVector++;
            cantidadBitsDisponibles = 32;

            //parte 2
            int countHijosB = cantidadBitsDisponibles - countHijos + 1; //revisar este apartado
            unsigned int vectorTempB = (int) pow(2, countHijosB) - 1;
            vectorTempB = vectorTempB << (unsigned)(countHijosB);
            bitvector[posicionBitVector] += vectorTempB;

            cantidadBitsDisponibles -=  (countHijosB + 1);
            if(cantidadBitsDisponibles == 0) posicionBitVector++;
            cantidadNodosContruidos++; //se creo un nuevo nodo
        }
    }

    return true;
}

int OrdinalTree::getRandom(int min, int max) {
    return rand() % max + min;
}

int OrdinalTree::depth(int x) {
    return 0;
}
