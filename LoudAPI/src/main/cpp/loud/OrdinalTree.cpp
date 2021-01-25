//
// Created by fredy on 15-01-2021.
//

#include "OrdinalTree.h"

OrdinalTree::OrdinalTree() { }

bool OrdinalTree::build(int countNodes) {
    return true;
}

int OrdinalTree::getRandom(int min, int max) {
    return rand() % max + min;
}

int OrdinalTree::depth(int x) {
    return 0;
}
