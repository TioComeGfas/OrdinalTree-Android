//
// Created by Fredy Camilo Moncada Contreras on 1/16/21.
//

#include "RankSelect.h"

RankSelect::RankSelect(long *vector, int k) {
    this->k = k;
    this->n = (sizeof(vector) / sizeof(long)) * 64;
    this->s = k * 32;

    this->popc = buildPopc();
    this->bitvector = buildBitVector(n,vector);
    this->Rs = buildRs(n,s);
}

void RankSelect::reBuild(long *vector, int k) {
    this->k = k;
    this->n = (sizeof(vector) / sizeof(long)) * 64;
    this->s = k * 32;

    this->bitvector = buildBitVector(n,vector);
    this->Rs = buildRs(n,s);
}

int RankSelect::rank(int i) {
    if(i == 0) return 0;
    if(i < 0 || i > n) return -1;
    return rankSuperBlock(i) + rankBlock(i) + rankInt(i);
}

int RankSelect::select(int i) {
    if(i < 1 || i > n || this->Rs[(sizeof(Rs) / sizeof(int)) -1] < i) return -1;
    int l = 1;
    int h = n;

    int idx = -1;

    while(l <= h){
        int m = (h-1) / 2 + l;
        if(rank(m) > i){
            h = m - 1;
        }else if(rank(m) == i){
            idx = m;
            h = m - 1;
        }else{
            l = m - 1;
        }
    }
    return idx;
}

int RankSelect::rankSuperBlock(int i) {
    int idx = i / s;
    return idx == 0 ? 0 : Rs[idx - 1];
}

int RankSelect::rankBlock(int i) {
    int start = (i /s) * k;
    int idx = i / 32;
    int sum = 0;
    if(idx == 0) return 0;
    for(int j = start; j < idx; j++){
        sum += popCount(bitvector[j]);
    }
    return sum;
}

int RankSelect::rankInt(int i) {
    int idx = i / 32;
    int x = i - (idx * 32);
    int shift = 32 - x;
    //return idx == (sizeof(bitvector) / sizeof(int)) || x == 0 ? 0 : popCount(bitvector[idx] >>> shift);
    return idx == (sizeof(bitvector) / sizeof(int)) || x == 0 ? 0 : popCount(bitvector[idx] >> shift);
}

int *RankSelect::buildBitVector(int n, long *vector) {
    int size = n/32;
    int* arr = new int[size];
    int previous = -1;
    for(int i = 0; i < size ; i++){
        int j = i >> 1;
        if(j != previous){
            //arr[i] = (int)(vector[j] >>> 32);
            arr[i] = (int)(vector[j] >> 32);
        }else{
            arr[i] = (int)(vector[j]);
        }
        previous = j;
    }

    return arr;
}

int *RankSelect::buildRs(int n, int s) {
    int sizeBitVector = (sizeof(bitvector) / sizeof(int));
    int sizeArr = (n/s) + 1;
    int* arr = new int[sizeArr];

    for(int i = 0; i < sizeBitVector; i++){
        int j = (i * 32) / s;
        arr[j] += popCount(bitvector[i]);
    }

    for(int j = sizeArr - 1; j >= 0; j--){
        for(int l = j - 1; l >= 0; l--){
            arr[j] += arr[l];
        }
    }
    return arr;
}

int8_t *RankSelect::buildPopc() {
    int sizeArr = 256;
    auto* arr = new int8_t[sizeArr];

    for(int i = 0; i < sizeArr; i++){
        for(int8_t j = 0; j < 9; j++){
            //arr[i] += (int8_t)((i >>> j) & 1);
            arr[i] += (int8_t)((i >> j) & 1);
        }
    }

    return arr;
}

int RankSelect::popCount(int x) {
    //return popc[x & 0xFF] + popc[(x >>> 8) & 0xFF] + popc[(x >>> 16) & 0xFF] + popc[(x >>> 24)];
    popc[x & 0xFF] + popc[(x >> 8) & 0xFF] + popc[(x >> 16) & 0xFF] + popc[(x >> 24)];
}