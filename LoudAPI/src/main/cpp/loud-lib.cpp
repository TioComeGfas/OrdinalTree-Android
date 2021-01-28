#include <jni.h>
#include <string>
#include <android/log.h>
#include "loud/OrdinalTree.h"

#define APP_NAME "module_loud jni"
#define LOG_E(TAG) __android_log_print(ANDROID_LOG_ERROR, APP_NAME, TAG);

extern "C" JNIEXPORT jlongArray JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_init(JNIEnv *env, jobject thiz, jint countNodes) {
    if(countNodes <= 0){
        return nullptr;
    }

    jint cantidadNodosContruidos = 0;
    jint posicionBitVector = 2;

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

    LOG_E("Bitarray creado");
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
    LOG_E("pre bitarray");
    auto* bitArray = new BitArray(env, _bitArray, size);
    LOG_E("post bitarray");

    LOG_E("pre rankselect");
    auto* rankSelect = new RankSelect(env, bitArray);
    LOG_E("post rankselect");

    LOG_E("instancia creada rankselect");
    //valor segun el paper
    return rankSelect->select0(rankSelect->rank0(rankSelect->select1(rankSelect->rank0(x)))) + 1;
}

extern "C" JNIEXPORT jint JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_child(JNIEnv *env, jobject thiz,  jlongArray _bitArray, jint size, jint x, jint i) {
    auto* rankSelect = new RankSelect(env, new BitArray(env, _bitArray, size));

    //valor segun el paper
    return rankSelect->select0(rankSelect->rank1(x + i - 1)) + 1;
}