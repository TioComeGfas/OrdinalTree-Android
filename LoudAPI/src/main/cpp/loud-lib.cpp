#include <jni.h>
#include <string>
#include "loud/OrdinalTree.h"

static const int WORD_SIZE = 64;

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

    auto* bitArray = new BitArray(countNodes);

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

    int size = bitArray->getLength();
    jlong fill[size];
    long* arraySrc = bitArray->getBitArray();

    for (int i = 0; i < size; i++) {
        fill[i] = arraySrc[i]; // put whatever logic you want to populate the values here.
    }

    jlongArray array = env->NewLongArray(size);
    env->SetLongArrayRegion(array,0,size,fill);

    return array;
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_build(JNIEnv *env, jobject thiz, jlongArray _bitArray, jint size) {
    auto* bitArray = new BitArray(_bitArray,size);
}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_firstChild(JNIEnv *env, jobject thiz, jint x) {

}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_nextSibling(JNIEnv *env, jobject thiz, jint x) {

}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_parent(JNIEnv *env, jobject thiz, jint x) {

}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_child(JNIEnv *env, jobject thiz, jint x, jint i) {

}

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_loudapi_LoudAPI_data(JNIEnv *env, jobject thiz, jint x) {

}