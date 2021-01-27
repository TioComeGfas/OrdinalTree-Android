#include <jni.h>
#include <string>
#include "cronometer/Cronometer.h"

extern "C" JNIEXPORT void JNICALL Java_cl_tiocomegfas_ubb_cronometer_CronometerAPI_startClock(JNIEnv *env, jobject thiz, jobject cronometer_api) {
    jclass cls = env->GetObjectClass(cronometer_api);

    jmethodID mid = env->GetMethodID(cls, "getUtime0", "()D");
    double utime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getUtime1", "()D");
    double utime1 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getWtime0", "()D");
    double wtime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getWtime1", "()D");
    double wtime1 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getStime0", "()D");
    double stime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getStime1", "()D");
    double stime1 = env->CallDoubleMethod(cronometer_api,mid);

    auto* cronometer = Cronometer::getInstance();
    cronometer->init(utime0,utime1,stime0,stime1,wtime0,wtime1);
    cronometer->startClock(cronometer);

    mid = env->GetMethodID(cls, "setUtime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getUtime0());

    mid = env->GetMethodID(cls, "setUtime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getUtime1());

    mid = env->GetMethodID(cls, "setWtime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getWtime0());

    mid = env->GetMethodID(cls, "setWtime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getWtime1());

    mid = env->GetMethodID(cls, "setStime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getStime0());

    mid = env->GetMethodID(cls, "setStime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getStime1());

    //limpieza de la memoria
    env->DeleteLocalRef(cls);
    env->DeleteLocalRef(reinterpret_cast<jobject>(mid));
    env->DeleteLocalRef(reinterpret_cast<jobject>(cronometer));
}

extern "C" JNIEXPORT jdouble JNICALL Java_cl_tiocomegfas_ubb_cronometer_CronometerAPI_stopClock(JNIEnv *env, jobject thiz, jobject cronometer_api) {
    jclass cls = env->GetObjectClass(cronometer_api);

    jmethodID mid = env->GetMethodID(cls, "getUtime0", "()D");
    double utime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getUtime1", "()D");
    double utime1 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getWtime0", "()D");
    double wtime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getWtime1", "()D");
    double wtime1 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getStime0", "()D");
    double stime0 = env->CallDoubleMethod(cronometer_api,mid);

    mid = env->GetMethodID(cls, "getStime1", "()D");
    double stime1 = env->CallDoubleMethod(cronometer_api,mid);

    auto* cronometer = Cronometer::getInstance();
    cronometer->init(utime0,utime1,stime0,stime1,wtime0,wtime1);
    jdouble request = cronometer->stopClock(cronometer);

    mid = env->GetMethodID(cls, "setUtime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getUtime0());

    mid = env->GetMethodID(cls, "setUtime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getUtime1());

    mid = env->GetMethodID(cls, "setWtime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getWtime0());

    mid = env->GetMethodID(cls, "setWtime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getWtime1());

    mid = env->GetMethodID(cls, "setStime0", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getStime0());

    mid = env->GetMethodID(cls, "setStime1", "(D)V");
    env->CallVoidMethod(cronometer_api,mid,cronometer->getStime1());

    //limpieza de la memoria
    env->DeleteLocalRef(cls);
    env->DeleteLocalRef(reinterpret_cast<jobject>(mid));
    env->DeleteLocalRef(reinterpret_cast<jobject>(cronometer));

    return request;
}