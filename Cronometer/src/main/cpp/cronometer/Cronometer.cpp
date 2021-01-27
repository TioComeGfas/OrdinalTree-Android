//
// Created by tiocomegfas on 15-01-2021.
//

#include <malloc.h>
#include <cstdlib>
#include "Cronometer.h"

Cronometer::Cronometer() {
    this->utime0 = 0;
    this->utime1 = 0;
    this->stime0 = 0;
    this->stime1 = 0;
    this->wtime0 = 0;
    this->wtime1 = 0;
}

Cronometer *Cronometer::getInstance() {
    return new Cronometer();
}

void Cronometer::startClock(Cronometer *cronom) {
    uswtime(&cronom->utime0, &cronom->stime0, &cronom->wtime0);
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

void Cronometer::init(double utime0, double utime1, double stime0, double stime1, double wtime0, double wtime1) {
    this->utime0 = utime0;
    this->utime1 = utime1;
    this->stime0 = stime0;
    this->stime1 = stime1;
    this->wtime0 = wtime0;
    this->wtime1 = wtime1;
}

void Cronometer::uswtime(double *usertime, double *systime, double *walltime) {
    struct rusage buffer;
    struct timeval tp;
    struct timezone tzp;

    getrusage(RUSAGE_SELF, &buffer);
    gettimeofday(&tp, &tzp);
    *usertime = (double) buffer.ru_utime.tv_sec + 1.0e-6 * buffer.ru_utime.tv_usec;
    *systime = (double) buffer.ru_stime.tv_sec + 1.0e-6 * buffer.ru_stime.tv_usec;
    *walltime = (double) tp.tv_sec + 1.0e-6 * tp.tv_usec;
}

double Cronometer::getUtime0() const {
    return utime0;
}

double Cronometer::getStime0() const {
    return stime0;
}

double Cronometer::getWtime0() const {
    return wtime0;
}

double Cronometer::getUtime1() const {
    return utime1;
}

double Cronometer::getStime1() const {
    return stime1;
}

double Cronometer::getWtime1() const {
    return wtime1;
}
