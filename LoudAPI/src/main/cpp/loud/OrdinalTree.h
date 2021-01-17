//
// Created by fredy on 15-01-2021.
//

#ifndef LOUD_ORDINALTREE_H
#define LOUD_ORDINALTREE_H

#include "NodeTree.h"
#include "../util/RankSelect.h"
#include <stdlib.h>

class OrdinalTree{
private:
    typedef struct Node{
        char* name_lastName;
        double bitvector;
    } OrdinalNode;

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
    //ordinalNode firstChild(int x);
    //ordinalNode nextSibling(int x);
    //ordinalNode parent(int x);
    //ordinalNode child(int x, int i);
    int dephth(int x);

private:
    int getRandom(int min, int max);
};


#endif //LOUD_ORDINALTREE_H
