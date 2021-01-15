//
// Created by tiocomegfas on 15-01-2021.
//

#ifndef LOUD_CRONOMETER_H
#define LOUD_CRONOMETER_H

#include <sys/resource.h>
#include <sys/time.h>

class Cronometer{
private:
    double utime0;
    double stime0;
    double wtime0;
    double utime1;
    double stime1;
    double wtime1;

private:
    /**
     * Contructor privado
     */
    Cronometer();

    void uswtime(double*, double*, double*);

public:
    /**
     * Entrega una nueva instancia
     * @return Instancia nueva de Cronometer
     */
    static Cronometer* getInstance();

    /**
     * inicia o reinicia el cronómetro cronom
     * @param cronom
     */
    void startClock(Cronometer*);

    /**
     * Detiene el cronómetro y devuelve el timepo de CPU (user+system) en segundos
     * si el cronometro no se ha inciado retorna un valor negativo.
     *
     * @param cronom
     * @return
     */
    double stopClock(Cronometer*);

    /**
     * Entrega los tiempos si el cronometro esta detenido, de otro modo entrega -1
     * @param cronom
     * @return
     */
    double userTime(Cronometer*);

    /**
     *
     * @param cronom
     * @return
     */
    double sysTime(Cronometer*);

    /**
     *
     * @param cronom
     * @return
     */
    double wallTime(Cronometer*);
};

#endif //LOUD_CRONOMETER_H
