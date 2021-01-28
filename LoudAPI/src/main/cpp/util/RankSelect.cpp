//
// Created by Fredy Camilo Moncada Contreras on 1/16/21.
//

#include "RankSelect.h"

RankSelect::RankSelect(JNIEnv* env, BitArray* bitArray) : RankSelect(env, bitArray, 20){ }

RankSelect::RankSelect(JNIEnv* env, BitArray* bitArray, int factor) {
    LOG_E("init rankselect");
    this->env = env;
    LOG_E("length rankselect");
    this->length = env->GetArrayLength(bitArray->getBitArray()) * WORD_SIZE;
    this->factor = factor;
    LOG_E("clone bits rank");
    bits = bitArray->cloneBits();
    if(factor == 0) factor = 20;
    s = WORD_SIZE * factor;
    LOG_E("build rank");
    buildRank();
    ones = rank1(length - 1);
}

long RankSelect::numberOfOnes() {
    return this->ones;
}

bool RankSelect::access(long pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: " [ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);
    return ((this->env->GetLongArrayElements(bits,(jboolean *)false)[(int)(pos / WORD_SIZE)]) & (1l << (pos % WORD_SIZE))) != 0;
}

long RankSelect::rank1(long pos) {
    if(pos < 0) throw IndexOutOfBoundsException(&"pos < 0: " [ pos]);
    if(pos >= length) throw IndexOutOfBoundsException(&"pos >= length():"[ pos]);

    jlong* arrayRs = this->env->GetLongArrayElements(Rs,(jboolean *) false);
    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    long i = pos + 1;
    int p = (int)(i/s);
    long resp = arrayRs[p];
    int aux = p * factor;
    for (int a = aux; a < (i / WORD_SIZE) ; a++){
        resp += countBits(arrayBits[a]);
    }

    resp += countBits(arrayBits[(int)(i / WORD_SIZE) & ((1l << (i & mask63)) - 1l)]);
    return resp;
}

long RankSelect::select1(long i) {
    long x=i;
    // returns i such that x=rank(i) && rank(i-1)<x or n if that i not exist
    // first binary search over first level rank structure
    // then sequential search using popcount over a int
    // then sequential search using popcount over a char
    // then sequential search bit a bit
    if(i <= 0) throw IndexOutOfBoundsException(&"i <= 0: " [ i]);;
    if(i > ones) throw IndexOutOfBoundsException(&"i > amount of ones:"[ i]);

    jlong* arrayRs = this->env->GetLongArrayElements(Rs,(jboolean *) false);
    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    //binary search over first level rank structure
    int l = 0;
    int r = (int)(length/s);
    int mid = (l + r) / 2;
    long rankmid =  arrayRs[mid]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(Rs), mid);  //Rs[mid];
    while (l <= r) {
        if (rankmid < x)
            l = mid + 1;
        else
            r = mid - 1;
        mid = (l + r) / 2;
        rankmid = arrayRs[mid]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(Rs), mid); //Rs[mid];
    }
    //sequential search using popcount over a int
    int left;
    left = mid * factor;
    x -= rankmid;
    long j = arrayBits[left]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), left); //bits[left];
    int onesJ = countBits(j);
    while (onesJ < x) {
        x -= onesJ;
        left++;
        if (left > getLength()) return length;
        j = arrayBits[left]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), left);  //bits[left];
        onesJ = countBits(j);
    }
    //sequential search using popcount over a char
    left = left * WORD_SIZE;
    rankmid = countBits(j);
    if (rankmid < x) {
        j = j >> 8l; //j=j>>>8l;
        x -= rankmid;
        left += 8l;
        rankmid = countBits(j);
        if (rankmid < x) {
            unsigned long aux = 0xFFFFFFFFFFFFFFFF >> 8L;
            j = (j >> 8L) & aux; //j=j>>>8l;
            x-=rankmid;
            left+=8l;
            rankmid = countBits(j);
            if (rankmid < x) {
                j = (j >> 8l) & aux; //j=j>>>8l;
                x -= rankmid;
                left += 8l;
            }
        }
    }

    // then sequential search bit a bit
    unsigned long aux = 0xFFFFFFFFFFFFFFFFL >> 1L;
    while (x > 0) {
        if((j & 1) > 0) x--;
        j = (j >> 1l) & aux;  //j=j>>>1l;
        left++;
    }
    return left - 1;
}

int RankSelect::countBits(long value) {
    int count = 0;
    while (value) {
        count += value & 0x1u;
        value >>= 1;
    }
    return count;
}

long RankSelect::rank0(long pos) {
    return pos - rank1(pos) + 1;
}

long RankSelect::select0(long i) {
    long x = i;
    // returns i such that x=rank_0(i) && rank_0(i-1)<x or exception if that i not exist
    // first binary search over first level rank structure
    // then sequential search using popcount over a int
    // then sequential search using popcount over a char
    // then sequential search bit a bit
    if(i <= 0) throw IndexOutOfBoundsException(&"i < 1: " [ i]);
    if(i > length - ones) throw IndexOutOfBoundsException(&"i > amount of 0:"[i]);

    jlong* arrayRs = this->env->GetLongArrayElements(Rs,(jboolean *) false);
    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    //binary search over first level rank structure
    if(x == 0) return 0;
    int l = 0;
    int r = (int)(length/s);
    int mid = (l + r) / 2;
    long rankmid = mid * factor * WORD_SIZE - arrayRs[mid]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(Rs), mid);  //Rs[mid];
    while (l <= r) {
        if (rankmid < x)
            l = mid + 1;
        else
            r = mid - 1;
        mid = (l + r) / 2;
        rankmid = mid * factor * WORD_SIZE - arrayRs[mid]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(Rs), mid); //Rs[mid];
    }
    //sequential search using popcount over a int
    int left;
    left = mid * factor;
    x -= rankmid;
    long j = arrayBits[left]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), left); //bits[left];
    int zeros = WORD_SIZE - countBits(j); //Long.bitCount(j);
    while (zeros < x) {
        x -= zeros;
        left++;
        //if (left > bits.length) return length;
        j = arrayBits[left]; //(long)this->env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), left); //bits[left];
        zeros = WORD_SIZE - countBits(j); //Long.bitCount(j);
    }
    //sequential search using popcount over a char
    left = left * WORD_SIZE;
    rankmid = WORD_SIZE - countBits(j); //Long.bitCount(j);
    if (rankmid < x) {
        j = j >> 8l;
        x -= rankmid;
        left += 8;
        rankmid = WORD_SIZE - countBits(j); //Long.bitCount(j);
        if (rankmid < x) {
            j = j >> 8l;
            x -= rankmid;
            left += 8;
            rankmid = WORD_SIZE - countBits(j); //Long.bitCount(j);
            if (rankmid < x) {
                j = j >> 8l;
                x -= rankmid;
                left += 8;
            }
        }
    }

    // then sequential search bit a bit
    while (x > 0) {
        if (j%2 == 0 ) x--;
        j=j>>1l;
        left++;
    }
    left--;
    if (left > length)  return length;
    return left;
}

long RankSelect::selectNext1(long start) {
    if(start<0) throw IndexOutOfBoundsException(&"start < 0: " [ start]);
    if(start>=length) throw IndexOutOfBoundsException(&"start >= length:"[ start]);

    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    long count = start;
    long des;
    long aux2;
    des = (int)(count % WORD_SIZE);
    long auxMask = 0xFFFFFFFFFFFFFFFFL >> des;
    aux2 = (arrayBits[(int)(count / WORD_SIZE)] >> des) & auxMask; //((long) env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), (int)(count / WORD_SIZE)) >> des) & auxMask;
    if (aux2 != 0) {
        return count + trailZeros(aux2); //Long.numberOfTrailingZeros(aux2);
    }

    for (int i =(int)(count/WORD_SIZE)+1 ; i < env->GetArrayLength(bits); i++) {
        aux2 = arrayBits[i]; //(long) env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), i); //bits[i];
        if (aux2 != 0) {
            return i * WORD_SIZE + trailZeros(aux2); //Long.numberOfTrailingZeros(aux2);
        }
    }
    return length;
}

long RankSelect::selectPrev1(long start) {
    // returns the position of the previous 1 bit before and including start.
    if(start < 0) throw IndexOutOfBoundsException(&"start < 0: " [ start]);
    if(start >= length) throw new IndexOutOfBoundsException(&"start > length:"[ start]);

    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    if(start == 0) return -1;
    int i = (int)(start / WORD_SIZE);
    int offset = (int)(start % WORD_SIZE);
    //64 unos
    long mask = 0xffffffffffffffffL;
    long auxMask = 0xFFFFFFFFFFFFFFFFL >> (WORD_SIZE - offset);
    //long aux2 = bits[i] & (mask >>> (WORD_SIZE-offset));
    long aux2 = arrayBits[i] & (mask & auxMask); //(long) env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), i) & (mask & auxMask);

    if (aux2 != 0) {
        return i*WORD_SIZE+63 - leadingZeros(aux2);  //Long.numberOfLeadingZeros(aux2);
    }

    for (int k = i - 1; k >= 0; k--) {
        aux2 = arrayBits[k]; //(long) env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), k); //bits[k];
        if (aux2 != 0) {
            return k * WORD_SIZE + 63 - leadingZeros(aux2); //Long.numberOfLeadingZeros(aux2);
        }
    }
    return -1;
}

int RankSelect::leadingZeros(long x) {
    int total_bits = sizeof(x) * 8;
    int res = 0;
    while ( !(x & (1 << (total_bits - 1)))){
        x = (x << 1);
        res++;
    }

    return res;
}

int RankSelect::trailZeros(long value) {
    int count = 0;
    int twos = 0;
    int fives = 0;
    while(value % 2 == 0 || value % 5 == 0){
        if(value % 2 == 0){
            value = value / 2;
            twos++;
        }
        if(value % 5 == 0){
            value = value / 5;
            fives++;
        }
    }
    count = twos < fives ? twos : fives;
    return count;
}

long RankSelect::selectNext0(long start) {
    if(start < 0) throw IndexOutOfBoundsException(&"start < 0: " [ start]);
    if(start >= length) throw IndexOutOfBoundsException(&"start >= length:"[ start]);

    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    long count = start;
    long des;
    long aux2;
    des = (int)(count % WORD_SIZE);
    long auxMask = 0xFFFFFFFFFFFFFFFFL >> des;
    aux2 = ~arrayBits[(int)(count / WORD_SIZE)] >> auxMask;   //(long)env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), (int)(count / WORD_SIZE)) >> auxMask; //aux2 = ~bits[(int)(count/WORD_SIZE)] >>> des;

    if (aux2 != 0) {
        return count + trailZeros(aux2); //Long.numberOfTrailingZeros(aux2);
    }

    for (int i = (int)(count/WORD_SIZE)+1; i < env->GetArrayLength(bits); i++) {
        aux2 = ~arrayBits[i];  //(long)env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), i);  //bits[i];
        if (aux2 != 0) {
            return i*WORD_SIZE + trailZeros(aux2); //Long.numberOfTrailingZeros(aux2);
        }
    }

    return length;
}

long RankSelect::selectPrev0(long start) {
    // returns the position of the previous 1 bit before and including start.
    if(start < 0) throw IndexOutOfBoundsException(&"start < 0: " [ start]);
    if(start >= length) throw IndexOutOfBoundsException(&"start > length:"[ start]);

    jlong* arrayBits = this->env->GetLongArrayElements(bits,(jboolean *) false);

    if(start == 0) return -1;
    int i = (int)(start / WORD_SIZE);
    long offset = (start % WORD_SIZE);
    //64 unos
    long mask = 0xffffffffffffffffL;
    long auxMask = 0xFFFFFFFFFFFFFFFFL >> (WORD_SIZE - offset);
    long aux2 = ~arrayBits[i] & (mask & auxMask);    //(long)env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), i) & (mask & auxMask); //long aux2 = ~bits[i] & (mask >>> (WORD_SIZE-offset));

    if (aux2 != 0) {
        return i * WORD_SIZE + 63 - leadingZeros(aux2); //Long.numberOfLeadingZeros(aux2);
    }
    for (int k = i - 1; k >= 0 ;k--) {
        aux2 = ~arrayBits[k];   //(long)env->GetObjectArrayElement(reinterpret_cast<jobjectArray>(bits), k);  //~  bits[k];
        if (aux2 != 0) {
            return k * WORD_SIZE + 63 - leadingZeros(aux2); //Long.numberOfLeadingZeros(aux2);
        }
    }
    return -1;
}

long RankSelect::getLength() {
    return this->length;
}

long RankSelect::size() {
    long bitmapSize = (env->GetArrayLength(bits) * WORD_SIZE) / 8+4; //long bitmapSize = (bits.length*WORD_SIZE)/8+4;
    long sbSize = (env->GetArrayLength(Rs) * WORD_SIZE) / 8+4;  //long sbSize = Rs.length * WORD_SIZE / 8+4;

    //variables:long: length, ones =2*8
    //int: factor y s =2*4
    //referencias a los arreglos (puntero): Rs, bits= 2*8 (word ram 64 bits)
    long otros=8+8+4+4+8+8;
    return bitmapSize + sbSize + otros;
}

char *RankSelect::toString() {
    char* out = "";
    for (int i = 0; i < length; i++) {
        out[i] = access(i) ? '1' : '0';
    }
    return out;
}

long RankSelect::numberOfZeroes() {
    return length - ones;
}

void RankSelect::buildRank() {
    int num_sblock = (int)(length/s);
    // +1 pues sumo la pos cero
    Rs = env->NewLongArray(num_sblock+5);

    jlong* arrayRs = this->env->GetLongArrayElements(Rs,(jboolean *) false);

    int j;
    arrayRs[0] = 0;
    for (j=1; j <= num_sblock; j++) {
        arrayRs[j] = arrayRs[j - 1];  //Rs[j] = Rs[j-1];
        arrayRs[j] += BuildRankSub((j - 1) * factor, factor);
    }
}

long RankSelect::BuildRankSub(int ini, int bloques) {
    jlong* array = this->env->GetLongArrayElements(bits,(jboolean *) false);
    long rank = 0;
    long aux;
    for(int i = ini; i < ini + bloques; i++) {
        if (i < env->GetArrayLength(bits)){
            aux = array[i];
            rank += countBits(aux); //Long.bitCount(aux);
        }
    }
    return rank; //retorna el numero de 1's del intervalo
}