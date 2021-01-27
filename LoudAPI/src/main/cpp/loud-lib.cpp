#include <jni.h>
#include <string>
#include "loud/OrdinalTree.h"

extern "C" JNIEXPORT jlongArray JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_init(JNIEnv *env, jobject thiz, jint countNodes) {
    if(countNodes <= 0){
        return nullptr;
    }

    int cantidadNodosContruidos = 0;
    int posicionBitVector = 2;

    auto* bitArray = new BitArray(env, countNodes);

    //inserto el nodo ficticio
    bitArray->setBit(0); //definiendo el 10 para el nodo ficticio

    while(cantidadNodosContruidos < countNodes){
        int cantidadHijos = bitArray->getRandom(1,50);

        for(int i = 1; i <= cantidadHijos; i++){
            bitArray->setBit(posicionBitVector);
            posicionBitVector++;
            cantidadNodosContruidos++;
        }

        posicionBitVector++;
    }

    return bitArray->getBitArray();
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_firstChild(JNIEnv *env, jobject thiz, jlongArray _bitArray, jint size, jint x) {
    auto* rankSelect = new RankSelect(env, new BitArray(env, _bitArray, size));

    return rankSelect->select0(rankSelect->rank1(x)) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_nextSibling(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* rankSelect = new RankSelect(env, new BitArray(env, _bitArray, size));

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank0(x) + 1) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_parent(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* rankSelect = new RankSelect(env, new BitArray(env, _bitArray, size));

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank0(rankSelect->select1(rankSelect->rank0(x)))) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_child(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x, jint i) {
    auto* rankSelect = new RankSelect(env, new BitArray(env, _bitArray, size));

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank1(x + i - 1)) + 1;
}