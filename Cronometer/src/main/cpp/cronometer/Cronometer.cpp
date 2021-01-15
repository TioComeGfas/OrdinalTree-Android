//
// Created by tiocomegfas on 15-01-2021.
//

#include <malloc.h>
#include <cstdlib>
#include "Cronometer.h"

Cronometer::Cronometer() {
    this-> utime0 = 0;
    this-> utime1=0;
    this-> stime0=0;
    this-> stime1=0;
    this-> wtime0=0;
    this-> wtime1=0;
}

Cronometer *Cronometer::getInstance() {
    return (Cronometer*) malloc(sizeof(Cronometer));;
}

void Cronometer::startClock(Cronometer *cronom) {
    uswtime(&cronom->utime0,&cronom->stime0, &cronom->wtime0);
}

double Cronometer::stopClock(Cronometer *cronom) {
    uswtime(&cronom->utime1, &cronom->stime1, &cronom->wtime1);
    return (cronom->utime1 - cronom->utime0 + cronom->stime1 - cronom->stime0);
}

double Cronometer::userTime(Cronometer *cronom) {
    return (cronom->utime1!=0) ? cronom->utime1 - cronom->utime0: - 1;
}

double Cronometer::sysTime(Cronometer *cronom) {
    return (cronom->stime1!=0) ? cronom->stime1 - cronom->stime0: -1;
}

double Cronometer::wallTime(Cronometer *cronom) {
    return (cronom->wtime1!=0) ? cronom->wtime1 - cronom->wtime0: -1;
}

void Cronometer::uswtime(double *usertime, double *systime, double *walltime) {
//double mega = 1.0e-6;
    struct rusage buffer;
    struct timeval tp;
    struct timezone tzp;

    getrusage(RUSAGE_SELF, &buffer);
    gettimeofday(&tp, &tzp);
    *usertime = (double) buffer.ru_utime.tv_sec + 1.0e-6 * buffer.ru_utime.tv_usec;
    *systime = (double) buffer.ru_stime.tv_sec + 1.0e-6 * buffer.ru_stime.tv_usec;
    *walltime = (double) tp.tv_sec + 1.0e-6 * tp.tv_usec;
}