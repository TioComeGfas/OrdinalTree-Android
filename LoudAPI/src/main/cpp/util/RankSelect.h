//
// Created by Fredy Camilo Moncada Contreras on 1/16/21.
//

#ifndef LOUD_RANKSELECT_H
#define LOUD_RANKSELECT_H

#include <cstdint>

class RankSelect {
private:
    int* bitvector;
    int* Rs;
    int s;
    int n;
    int k;
    int8_t* popc;

public:
    RankSelect(long* vector, int k);
    void reBuild(long* vector, int k);
    int rank(int i);
    int select(int i);
private:
    int rankSuperBlock(int i);
    int rankBlock(int i);
    int rankInt(int i);
    int* buildBitVector(int n, long* vector);
    int* buildRs(int n, int s);
    int8_t * buildPopc();
    int popCount(int x);
};

#endif //LOUD_RANKSELECT_H