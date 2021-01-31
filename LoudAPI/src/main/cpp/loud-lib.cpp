#include <jni.h>
#include <string>
#include <android/log.h>
#include "util/BitArray.h"
#include "util/RankSelect.h"

#define APP_NAME "module_loud jni"
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR,APP_NAME,__VA_ARGS__)

extern "C" JNIEXPORT jlongArray JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_init(JNIEnv *env, jobject thiz, jint countNodes) {
    if(countNodes <= 0){
        return nullptr;
    }

    jint cantidadNodosContruidos = 0;
    jint posicionBitVector = 2;
    jint posicionAuxBitVector = posicionBitVector;

    auto* bitArray = new BitArray(env, countNodes);

    //inserto el nodo ficticio
    //definiendo el 10 para el nodo ficticio
    bitArray->setBit(0,true);
    bitArray->setBit(0, false);

    for(int i = 0; i < countNodes; i++){
        int cantidadHijos = bitArray->getRandom(1,50);

        int restantes = countNodes - posicionBitVector;
        if(restantes <= cantidadHijos) {
            cantidadHijos = restantes;
        }

        if(restantes <= 0) break;


        for(int j = 0; j < cantidadHijos; j++){
            posicionAuxBitVector = posicionBitVector / 64;
            bitArray->setBit(posicionAuxBitVector,true);
            posicionBitVector++;
            cantidadNodosContruidos++;
        }

        posicionBitVector++;
    }

    LOG_E("Nodos creados: %i", (int)cantidadNodosContruidos);
    LOG_E("Bitarray creado");
    bitArray->toString();

    return bitArray->getBitArray();
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_firstChild(JNIEnv *env, jobject thiz, jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(env, bitArray, bitArray->getLength());

    return rankSelect->select0(rankSelect->rank1(x)) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_nextSibling(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(env,bitArray, bitArray->getLength());

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank0(x) + 1) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_parent(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(env, bitArray,bitArray->getLength());

    //valor segun el paper
    long value;

    try{
        value = rankSelect->select0(
                rankSelect->rank0(
                        rankSelect->select1(
                                rankSelect->rank0(x)))) + 1;
    } catch (IndexOutOfBoundsException e) {
        LOG_E("ERROR: %s",e.getMessage());
    }

    return value;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_child(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x, jint i) {
    auto* bitArray = new BitArray(env, _bitArray, size);
    auto* rankSelect = new RankSelect(env, bitArray, bitArray->getLength());

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank1(x + i - 1)) + 1;
}