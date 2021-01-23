//
// Created by Fredy Camilo Moncada Contreras on 1/23/21.
//

#ifndef LOUD_INDEXOUTOFBOUNDSEXCEPTION_H
#define LOUD_INDEXOUTOFBOUNDSEXCEPTION_H

#include "../../../../../../../../Library/Android/sdk/ndk/21.1.6352462/toolchains/llvm/prebuilt/darwin-x86_64/sysroot/usr/include/c++/v1/exception"

using namespace std;

struct IndexOutOfBoundsException: public exception{
    const char* msg;

    IndexOutOfBoundsException(const char* msg){
        this->msg = msg;
    }

    const char* getMessage() const throw(){
        return msg;
    }
};


#endif //LOUD_INDEXOUTOFBOUNDSEXCEPTION_H
