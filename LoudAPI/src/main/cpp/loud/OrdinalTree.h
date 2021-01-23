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
    RankSelect* rankSelect;
    int levels;
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
