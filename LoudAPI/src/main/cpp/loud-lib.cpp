#include <jni.h>
#include <string>
#include "loud/OrdinalTree.h"

extern "C" JNIEXPORT jlongArray JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_init(JNIEnv *env, jobject thiz, jint countNodes) {
    if(countNodes <= 0){
        return nullptr;
    }

    char** names = (char**) malloc(sizeof(char*) * countNodes); //un arreglo con la cantidad de nombres y apellidos
    for(int i = 0; i < countNodes; i++){
        names[i] = (char*) malloc(sizeof(char) * 30); //asigacion de espacio para generar un string
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

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_build(JNIEnv *env, jobject thiz, jlongArray _bitArray, jint size) {
    auto* bitArray = new BitArray(env, _bitArray, size);
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_firstChild(JNIEnv *env, jobject thiz, jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(bitArray);

    //valor segun el paper
    long position= rankSelect->select0(rankSelect->rank1(x)) + 1;
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_nextSibling(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(bitArray);

    //valor segun el paper
    long position = rankSelect->select0(rankSelect->rank0(x) + 1) + 1;
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_parent(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(bitArray);

    //valor segun el paper
    long position = rankSelect->select0(rankSelect->rank0(rankSelect->select1(rankSelect->rank0(x)))) + 1;
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_child(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x, jint i) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(bitArray);

    //valor segun el paper
    long position = rankSelect->select0(rankSelect->rank1(x + i - 1)) + 1;
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_data(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(bitArray);

    long position = 0;
}