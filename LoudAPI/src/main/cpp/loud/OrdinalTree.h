//
// Created by fredy on 15-01-2021.
//

#ifndef LOUD_ORDINALTREE_H
#define LOUD_ORDINALTREE_H

#include "../util/RankSelect.h"
#include "../util/BitArray.h"
#include <stdlib.h>

class OrdinalTree{
private:

    /**
     * Objeto que manipula un array de bits
     */
    BitArray* bitArray;

    /**
     * Arreglo de string con los nombres y apellidos de las personas
     */
    char** names;

    /**
     * Objeto para trabajar con rank y select
     */
    RankSelect* rankSelect;
public:
    OrdinalTree();

    /**
     * Construye el bit vector de manera aleatorio.
     * @param countNodes
     * @return
     */
    bool build(int countNodes);


    //Node* firstChildNode(int x);
    //Node* nextSiblingNode(int x);
    //Node* parentNode(int x);
    //Node* childNode(int x);
    int depth(int x);

private:
    int getRandom(int min, int max);
};


#endif //LOUD_ORDINALTREE_H
