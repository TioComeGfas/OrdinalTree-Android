package cl.tiocomegfas.ubb.loud.backend.constants;

public interface Codes {

    String codeBitArray = ""+
            "package cl.tiocomegfas.bitarray;\n" +
            "\n" +
            "/**\n" +
            " * @author Luis Gajardo, Miguel Romero y Fernando Santolaya\n" +
            " * Basado en\n" +
            " * http://stackoverflow.com/questions/15736626/java-how-to-create-and-manipulate-a-bit-array-with-length-of-10-million-bits\n" +
            " */\n" +
            "public class BitArray {\n" +
            "    private static final int WORD_SIZE = 64;\n" +
            "    private final long length;\n" +
            "    protected long[] bits;\n" +
            "\n" +
            "    /**\n" +
            "     * Crea un Array de bits.\n" +
            "     *\n" +
            "     * @param size es la cantidad de bits que tiene el ubiobio.cl.bitArray.BitArray this.\n" +
            "     */\n" +
            "    public BitArray(long size) {\n" +
            "        this.length = size;\n" +
            "        //se constuye un arreglo con ceil(length / word_size) bloques.\n" +
            "        bits = new long[(int) (size / WORD_SIZE + 1)];\n" +
            "        build();\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Permite conocer el valor 0 o 1 de la i-ésima posición del ubiobio.cl.bitArray.BitArray\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     * @return\n" +
            "     */\n" +
            "    public boolean getBit(int pos) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos >= length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "\n" +
            "        return (bits[pos / WORD_SIZE] & (1L << (pos % WORD_SIZE))) != 0;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Pone en 1 el bits de la posición @pos del arreglo\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     */\n" +
            "    public void setBit(int pos) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos > length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "\n" +
            "        long block = bits[pos / WORD_SIZE];\n" +
            "        long mask = (long) (1L << (pos % WORD_SIZE));\n" +
            "        block |= mask;\n" +
            "        bits[pos / WORD_SIZE] = block;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Pone en 0 o 1 dependiendo si b es falso o verdadero respectivamente\n" +
            "     * la posición pos del ubiobio.cl.bitArray.BitArray.\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     * @param b\n" +
            "     */\n" +
            "    public void setBit(int pos, boolean b) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos > length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "\n" +
            "        long block = bits[pos / WORD_SIZE];\n" +
            "        long mask = (long) (1L << (pos % WORD_SIZE));\n" +
            "        if (b) {\n" +
            "            block |= mask;\n" +
            "        } else {\n" +
            "            block &= ~mask;\n" +
            "        }\n" +
            "        bits[pos / WORD_SIZE] = block;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Pone en 0 el bits de la posición pos del ubiobio.cl.bitArray.BitArray\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     */\n" +
            "    public void clearBit(int pos) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos > length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "\n" +
            "        long block = bits[pos / WORD_SIZE];\n" +
            "        long mask = (long) (1L << (pos % WORD_SIZE));\n" +
            "        block &= ~mask;\n" +
            "        bits[pos / WORD_SIZE] = block;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna la cantidad de bits en el ubiobio.cl.bitArray.BitArray.\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long length() {\n" +
            "        return length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el tamaño del ubiobio.cl.bitArray.BitArray en byte.\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long size() {\n" +
            "        //8 por variable this.length\n" +
            "        //4 por bits.length\n" +
            "        //8 por la referencia a bits (pensando en arquitectura de 64 bits, peor caso).\n" +
            "\n" +
            "        return (bits.length * WORD_SIZE) / 8 + 8 + 4 + 8;\n" +
            "    }\n" +
            "\n" +
            "    private void build(){\n" +
            "        setBit(0); //inserto el nodo ficticio\n" +
            "        setBit(2); //inserto el nodo raiz\n" +
            "        int posArray = 4;\n" +
            "\n" +
            "        while (posArray < length) {\n" +
            "            long cantidadHijos = (int) Math.floor(Math.random() * (50 - 1 + 1) + 1);\n" +
            "\n" +
            "            long diferencia = length - posArray;\n" +
            "            if(cantidadHijos > diferencia) cantidadHijos = diferencia;\n" +
            "\n" +
            "            for(int i=0; i < cantidadHijos; i++){\n" +
            "                setBit(posArray);\n" +
            "                posArray++;\n" +
            "            }\n" +
            "            posArray++;\n" +
            "        }\n" +
            "\n" +
            "        setBit((int)length - 1,false);\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        StringBuilder out = new StringBuilder();\n" +
            "        for (int i = 0; i < length; i++) {\n" +
            "            out.append(getBit(i) ? \"1\" : \"0\");\n" +
            "        }\n" +
            "        return out.toString();\n" +
            "    }\n" +
            "\n" +
            "}"
            ;

    String codeRankSelect = ""+
            "package cl.tiocomegfas.bitarray;\n" +
            "\n" +
            "/**\n" +
            " * Grupo de investigación ALBA,\n" +
            " * Proyecto 2030 ...\n" +
            " *\n" +
            " * @author Luis Gajardo, Miguel Romero y Fernando Santolaya\n" +
            " * Basado en\n" +
            " * BitSecuenceRG de libcds Autor Francisco Claude\n" +
            " * https://github.com/fclaude/libcds/blob/master/src/static/bitsequence/BitSequenceRG.cpp\n" +
            " */\n" +
            "\n" +
            "public class RankSelect {\n" +
            "    private static final int WORD_SIZE = 64;\n" +
            "    /**\n" +
            "     * mask for obtaining the first 5 bits\n" +
            "     */\n" +
            "    private static final long mask63 = 63L;\n" +
            "    private final long length;\n" +
            "    private final int factor;\n" +
            "    private final int s;\n" +
            "    private final long ones;\n" +
            "    private final long[] bits;\n" +
            "    private long[] Rs; //arreglo de superBlock\n" +
            "\n" +
            "    /**\n" +
            "     * Crea un Array de bits estático con soporte para las\n" +
            "     * operaciones de rank y select.\n" +
            "     *\n" +
            "     * @param ba\n" +
            "     */\n" +
            "    public RankSelect(BitArray ba) {\n" +
            "        this(ba, 20);\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Crea un Array de bits estático con soporte para las\n" +
            "     * operaciones de rank y select.\n" +
            "     *\n" +
            "     * @param ba,    bit array a clonar, sobre el cual se opera\n" +
            "     * @param factor factor con el cual se determina la redundancia de la estructura\n" +
            "     *               si factor=2, redundancia 50%\n" +
            "     *               factor=3, redundancia 33%\n" +
            "     *               factor=4, redundancia 25%\n" +
            "     *               factor=20, redundancia 5%;\n" +
            "     */\n" +
            "    public RankSelect(BitArray ba, int factor) {\n" +
            "        this.length = ba.length();\n" +
            "        bits = ba.bits.clone();\n" +
            "        this.factor = factor;\n" +
            "        if (factor == 0) factor = 20;\n" +
            "        s = WORD_SIZE * factor;\n" +
            "        buildRank();\n" +
            "        ones = rank1(length - 1);\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "    private void buildRank() {\n" +
            "        int num_sblock = (int) (length / s);\n" +
            "        // +1 pues sumo la pos cero\n" +
            "        Rs = new long[num_sblock + 5];\n" +
            "        int j;\n" +
            "        Rs[0] = 0;\n" +
            "        for (j = 1; j <= num_sblock; j++) {\n" +
            "            Rs[j] = Rs[j - 1];\n" +
            "            Rs[j] += BuildRankSub((j - 1) * factor, factor);\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    private long BuildRankSub(int ini, int bloques) {\n" +
            "        long rank = 0, aux;\n" +
            "        for (int i = ini; i < ini + bloques; i++) {\n" +
            "            if (i < bits.length) {\n" +
            "                aux = bits[i];\n" +
            "                rank += Long.bitCount(aux);\n" +
            "            }\n" +
            "        }\n" +
            "        return rank;             //retorna el numero de 1's del intervalo\n" +
            "    }\n" +
            "\n" +
            "    public long numberOfOnes() {\n" +
            "        return ones;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Permite conocer el valor 0 o 1 de la i-ésima posición del ubiobio.cl.bitArray.BitArray\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     * @return\n" +
            "     */\n" +
            "    public boolean access(long pos) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos >= length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "        return (bits[(int) (pos / WORD_SIZE)] & (1l << (pos % WORD_SIZE))) != 0;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna la cantidad de unos que hay hasta pos inclusive.\n" +
            "     *\n" +
            "     * @param pos\n" +
            "     * @return\n" +
            "     * @throws IndexOutOfBoundsException, si pos esta fuera del rango [0..length)\n" +
            "     */\n" +
            "    public long rank1(long pos) {\n" +
            "        if (pos < 0) throw new IndexOutOfBoundsException(\"pos < 0: \" + pos);\n" +
            "        if (pos >= length) throw new IndexOutOfBoundsException(\"pos >= length():\" + pos);\n" +
            "        long i = pos + 1;\n" +
            "        int p = (int) (i / s);\n" +
            "        long resp = Rs[p];\n" +
            "        int aux = p * factor;\n" +
            "        for (int a = aux; a < i / WORD_SIZE; a++)\n" +
            "            resp += Long.bitCount(bits[a]);\n" +
            "        resp += Long.bitCount(bits[(int) (i / WORD_SIZE)] & ((1l << (i & mask63)) - 1l));\n" +
            "        return resp;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna la posición en el bit array en la que ocurre el i-ésimo uno.\n" +
            "     *\n" +
            "     * @param i\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long select1(long i) {\n" +
            "        long x = i;\n" +
            "        // returns i such that x=rank(i) && rank(i-1)<x or n if that i not exist\n" +
            "        // first binary search over first level rank structure\n" +
            "        // then sequential search using popcount over a int\n" +
            "        // then sequential search using popcount over a char\n" +
            "        // then sequential search bit a bit\n" +
            "        if (i <= 0) throw new IndexOutOfBoundsException(\"i <= 0: \" + i);\n" +
            "        if (i > ones) throw new IndexOutOfBoundsException(\"i > amount of ones:\" + i);\n" +
            "        //binary search over first level rank structure\n" +
            "        int l = 0, r = (int) (length / s);\n" +
            "        int mid = (l + r) / 2;\n" +
            "        long rankmid = Rs[mid];\n" +
            "        while (l <= r) {\n" +
            "            if (rankmid < x)\n" +
            "                l = mid + 1;\n" +
            "            else\n" +
            "                r = mid - 1;\n" +
            "            mid = (l + r) / 2;\n" +
            "            rankmid = Rs[mid];\n" +
            "        }\n" +
            "        //sequential search using popcount over a int\n" +
            "        int left;\n" +
            "        left = mid * factor;\n" +
            "        x -= rankmid;\n" +
            "        long j = bits[left];\n" +
            "        int onesJ = Long.bitCount(j);\n" +
            "        while (onesJ < x) {\n" +
            "            x -= onesJ;\n" +
            "            left++;\n" +
            "            if (left > bits.length) return length;\n" +
            "            j = bits[left];\n" +
            "            onesJ = Long.bitCount(j);\n" +
            "        }\n" +
            "        //sequential search using popcount over a char\n" +
            "        left = left * WORD_SIZE;\n" +
            "        rankmid = Long.bitCount(j);\n" +
            "        if (rankmid < x) {\n" +
            "            j = j >>> 8l;\n" +
            "            x -= rankmid;\n" +
            "            left += 8l;\n" +
            "            rankmid = Long.bitCount(j);\n" +
            "            if (rankmid < x) {\n" +
            "                j = j >>> 8l;\n" +
            "                x -= rankmid;\n" +
            "                left += 8l;\n" +
            "                rankmid = Long.bitCount(j);\n" +
            "                if (rankmid < x) {\n" +
            "                    j = j >>> 8l;\n" +
            "                    x -= rankmid;\n" +
            "                    left += 8l;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        // then sequential search bit a bit\n" +
            "        while (x > 0) {\n" +
            "            if ((j & 1) > 0) x--;\n" +
            "            j = j >>> 1l;\n" +
            "            left++;\n" +
            "        }\n" +
            "        return left - 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna la cantidad de ceros que hay hasta pos inclusive.\n" +
            "     *\n" +
            "     * @param pos posición hasta donde se quiere contar la cantidad de ceros.\n" +
            "     * @return total de ceros hasta la posicion pos\n" +
            "     */\n" +
            "    public long rank0(long pos) {\n" +
            "        return pos - rank1(pos) + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna la posición en el bit array en la que ocurre el i-ésimo cero.\n" +
            "     *\n" +
            "     * @param i número ordinal del cero buscado.\n" +
            "     * @return posición del i-esimo cero\n" +
            "     */\n" +
            "    public long select0(long i) {\n" +
            "        long x = i;\n" +
            "        // returns i such that x=rank_0(i) && rank_0(i-1)<x or exception if that i not exist\n" +
            "        // first binary search over first level rank structure\n" +
            "        // then sequential search using popcount over a int\n" +
            "        // then sequential search using popcount over a char\n" +
            "        // then sequential search bit a bit\n" +
            "        if (i <= 0) throw new IndexOutOfBoundsException(\"i < 1: \" + i);\n" +
            "        if (i > length - ones) throw new IndexOutOfBoundsException(\"i > amount of 0:\" + i);\n" +
            "        //binary search over first level rank structure\n" +
            "        if (x == 0) return 0;\n" +
            "        int l = 0, r = (int) (length / s);\n" +
            "        int mid = (l + r) / 2;\n" +
            "        long rankmid = mid * factor * WORD_SIZE - Rs[mid];\n" +
            "        while (l <= r) {\n" +
            "            if (rankmid < x)\n" +
            "                l = mid + 1;\n" +
            "            else\n" +
            "                r = mid - 1;\n" +
            "            mid = (l + r) / 2;\n" +
            "            rankmid = mid * factor * WORD_SIZE - Rs[mid];\n" +
            "        }\n" +
            "        //sequential search using popcount over a int\n" +
            "        int left;\n" +
            "        left = mid * factor;\n" +
            "        x -= rankmid;\n" +
            "        long j = bits[left];\n" +
            "        int zeros = WORD_SIZE - Long.bitCount(j);\n" +
            "        while (zeros < x) {\n" +
            "            x -= zeros;\n" +
            "            left++;\n" +
            "            if (left > bits.length) return length;\n" +
            "            j = bits[left];\n" +
            "            zeros = WORD_SIZE - Long.bitCount(j);\n" +
            "        }\n" +
            "        //sequential search using popcount over a char\n" +
            "        left = left * WORD_SIZE;\n" +
            "        rankmid = WORD_SIZE - Long.bitCount(j);\n" +
            "        if (rankmid < x) {\n" +
            "            j = j >> 8l;\n" +
            "            x -= rankmid;\n" +
            "            left += 8;\n" +
            "            rankmid = WORD_SIZE - Long.bitCount(j);\n" +
            "            if (rankmid < x) {\n" +
            "                j = j >> 8l;\n" +
            "                x -= rankmid;\n" +
            "                left += 8;\n" +
            "                rankmid = WORD_SIZE - Long.bitCount(j);\n" +
            "                if (rankmid < x) {\n" +
            "                    j = j >> 8l;\n" +
            "                    x -= rankmid;\n" +
            "                    left += 8;\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        // then sequential search bit a bit\n" +
            "        while (x > 0) {\n" +
            "            if (j % 2 == 0) x--;\n" +
            "            j = j >> 1l;\n" +
            "            left++;\n" +
            "        }\n" +
            "        left--;\n" +
            "        if (left > length) return length;\n" +
            "        return left;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna el menor indice i>=start tal que access(i) es true;\n" +
            "     *\n" +
            "     * @param start\n" +
            "     * @return posición en el bitArray del 1 siguiente o igual a start.\n" +
            "     * En el caso de no haber un 1 posterior retorna el largo\n" +
            "     * en bits de la secuencia.\n" +
            "     */\n" +
            "    public long selectNext1(long start) {\n" +
            "        if (start < 0) throw new IndexOutOfBoundsException(\"start < 0: \" + start);\n" +
            "        if (start >= length) throw new IndexOutOfBoundsException(\"start >= length:\" + start);\n" +
            "        long count = start;\n" +
            "        long des;\n" +
            "        long aux2;\n" +
            "        des = (int) (count % WORD_SIZE);\n" +
            "        aux2 = bits[(int) (count / WORD_SIZE)] >>> des;\n" +
            "        if (aux2 != 0) {\n" +
            "            return count + Long.numberOfTrailingZeros(aux2);\n" +
            "        }\n" +
            "\n" +
            "        for (int i = (int) (count / WORD_SIZE) + 1; i < bits.length; i++) {\n" +
            "            aux2 = bits[i];\n" +
            "            if (aux2 != 0) {\n" +
            "                return i * WORD_SIZE + Long.numberOfTrailingZeros(aux2);\n" +
            "            }\n" +
            "        }\n" +
            "        return length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna el mayor indice i<=start tal que access(i) es true;\n" +
            "     *\n" +
            "     * @param start\n" +
            "     * @return retorna la posición i del 1 previo a start.  Si no\n" +
            "     * hay un 1 previo a la posción start retorna  -1.\n" +
            "     */\n" +
            "    public long selectPrev1(long start) {\n" +
            "        // returns the position of the previous 1 bit before and including start.\n" +
            "        if (start < 0) throw new IndexOutOfBoundsException(\"start < 0: \" + start);\n" +
            "        if (start >= length) throw new IndexOutOfBoundsException(\"start > length:\" + start);\n" +
            "        if (start == 0) return -1;\n" +
            "        int i = (int) (start / WORD_SIZE);\n" +
            "        int offset = (int) (start % WORD_SIZE);\n" +
            "        //64 unos\n" +
            "        long mask = 0xffffffffffffffffL;\n" +
            "        long aux2 = bits[i] & (mask >>> (WORD_SIZE - offset));\n" +
            "\n" +
            "        if (aux2 != 0) {\n" +
            "            return i * WORD_SIZE + 63 - Long.numberOfLeadingZeros(aux2);\n" +
            "        }\n" +
            "        for (int k = i - 1; k >= 0; k--) {\n" +
            "            aux2 = bits[k];\n" +
            "            if (aux2 != 0) {\n" +
            "                return k * WORD_SIZE + 63 - Long.numberOfLeadingZeros(aux2);\n" +
            "            }\n" +
            "        }\n" +
            "        return -1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna el menor indice i>start tal que access(i) es false;\n" +
            "     *\n" +
            "     * @param start\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long selectNext0(long start) {\n" +
            "        if (start < 0) throw new IndexOutOfBoundsException(\"start < 0: \" + start);\n" +
            "        if (start >= length) throw new IndexOutOfBoundsException(\"start >= length:\" + start);\n" +
            "        long count = start;\n" +
            "        long des;\n" +
            "        long aux2;\n" +
            "        des = (int) (count % WORD_SIZE);\n" +
            "        aux2 = ~bits[(int) (count / WORD_SIZE)] >>> des;\n" +
            "        if (aux2 != 0) {\n" +
            "            return count + Long.numberOfTrailingZeros(aux2);\n" +
            "        }\n" +
            "\n" +
            "        for (int i = (int) (count / WORD_SIZE) + 1; i < bits.length; i++) {\n" +
            "            aux2 = ~bits[i];\n" +
            "            if (aux2 != 0) {\n" +
            "                return i * WORD_SIZE + Long.numberOfTrailingZeros(aux2);\n" +
            "            }\n" +
            "        }\n" +
            "        return length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * retorna el mayor indice i<start tal que access(i) es false;\n" +
            "     *\n" +
            "     * @param start\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long selectPrev0(long start) {\n" +
            "        // returns the position of the previous 1 bit before and including start.\n" +
            "        if (start < 0) throw new IndexOutOfBoundsException(\"start < 0: \" + start);\n" +
            "        if (start >= length) throw new IndexOutOfBoundsException(\"start > length:\" + start);\n" +
            "        if (start == 0) return -1;\n" +
            "        int i = (int) (start / WORD_SIZE);\n" +
            "        long offset = (start % WORD_SIZE);\n" +
            "        //64 unos\n" +
            "        long mask = 0xffffffffffffffffL;\n" +
            "        long aux2 = ~bits[i] & (mask >>> (WORD_SIZE - offset));\n" +
            "\n" +
            "        if (aux2 != 0) {\n" +
            "            return i * WORD_SIZE + 63 - Long.numberOfLeadingZeros(aux2);\n" +
            "        }\n" +
            "        for (int k = i - 1; k >= 0; k--) {\n" +
            "            aux2 = ~bits[k];\n" +
            "            if (aux2 != 0) {\n" +
            "                return k * WORD_SIZE + 63 - Long.numberOfLeadingZeros(aux2);\n" +
            "            }\n" +
            "        }\n" +
            "        return -1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna la cantidad de bits en el ubiobio.cl.bitArray.BitArray.\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long length() {\n" +
            "        return length;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el tamaño del ubiobio.cl.bitArray.BitArray en byte.\n" +
            "     *\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long size() {\n" +
            "        long bitmapSize = (bits.length * WORD_SIZE) / 8 + 4;\n" +
            "        long sbSize = Rs.length * WORD_SIZE / 8 + 4;\n" +
            "        //variables:long: length, ones =2*8\n" +
            "        //int: factor y s =2*4\n" +
            "        //referencias a los arreglos (puntero): Rs, bits= 2*8 (word ram 64 bits)\n" +
            "        long otros = 8 + 8 + 4 + 4 + 8 + 8;\n" +
            "        return bitmapSize + sbSize + otros;\n" +
            "    }\n" +
            "\n" +
            "    @Override\n" +
            "    public String toString() {\n" +
            "        StringBuilder out = new StringBuilder();\n" +
            "        for (int i = 0; i < length; i++) {\n" +
            "            out.append(access(i) ? \"1\" : \"0\");\n" +
            "        }\n" +
            "        return out.toString();\n" +
            "    }\n" +
            "\n" +
            "    public long numberOfZeroes() {\n" +
            "        return length - ones;\n" +
            "    }\n" +
            "\n" +
            "}";

    String codeLoudsTree = ""+
            "package cl.tiocomegfas.bitarray;\n" +
            "\n" +
            "public class LoudsTree {\n" +
            "    private final RankSelect rankSelect;\n" +
            "\n" +
            "    public LoudsTree(long size){\n" +
            "        rankSelect = new RankSelect(new BitArray(size));\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el padre de cada posicion\n" +
            "     * @param position\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long parent(int position){\n" +
            "        return rankSelect.select0(rankSelect.rank0(rankSelect.select1(rankSelect.rank0(position)))) + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna los hijos de cada posicion hacia abajo\n" +
            "     * @param position\n" +
            "     * @param indexChild\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long child(int position, int indexChild){\n" +
            "        return rankSelect.select0(rankSelect.rank1(position + indexChild - 1)) + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el primer hijo\n" +
            "     * @param position\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long firstChild(int position){\n" +
            "        return rankSelect.select0(rankSelect.rank1(position)) + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el ultimo hermano de la posicion\n" +
            "     * @param position\n" +
            "     * @return\n" +
            "     */\n" +
            "    public long nextSibling(int position){\n" +
            "        return rankSelect.select0(rankSelect.rank0(position) + 1) + 1;\n" +
            "    }\n" +
            "\n" +
            "    /**\n" +
            "     * Retorna el bitarray entero\n" +
            "     * @return\n" +
            "     */\n" +
            "    public String printAll(){\n" +
            "        StringBuilder sb = new StringBuilder();\n" +
            "        return rankSelect.toString();\n" +
            "    }\n" +
            "}";

    String codeChronometerCpp = ""+
            "#include \"Chronometer.h\"\n" +
            "\n" +
            "Chronometer::Chronometer() {\n" +
            "    this->utime0 = 0;\n" +
            "    this->utime1 = 0;\n" +
            "    this->stime0 = 0;\n" +
            "    this->stime1 = 0;\n" +
            "    this->wtime0 = 0;\n" +
            "    this->wtime1 = 0;\n" +
            "}\n" +
            "\n" +
            "Chronometer *Chronometer::getInstance() {\n" +
            "    return new Chronometer();\n" +
            "}\n" +
            "\n" +
            "void Chronometer::startClock(Chronometer *cronom) {\n" +
            "    uswtime(&cronom->utime0, &cronom->stime0, &cronom->wtime0);\n" +
            "}\n" +
            "\n" +
            "double Chronometer::stopClock(Chronometer *cronom) {\n" +
            "    uswtime(&cronom->utime1, &cronom->stime1, &cronom->wtime1);\n" +
            "    return (cronom->utime1 - cronom->utime0 + cronom->stime1 - cronom->stime0);\n" +
            "}\n" +
            "\n" +
            "double Chronometer::userTime(Chronometer *cronom) {\n" +
            "    return (cronom->utime1!=0) ? cronom->utime1 - cronom->utime0: - 1;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::sysTime(Chronometer *cronom) {\n" +
            "    return (cronom->stime1!=0) ? cronom->stime1 - cronom->stime0: -1;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::wallTime(Chronometer *cronom) {\n" +
            "    return (cronom->wtime1!=0) ? cronom->wtime1 - cronom->wtime0: -1;\n" +
            "}\n" +
            "\n" +
            "void Chronometer::init(double utime0, double utime1, double stime0, double stime1, double wtime0, double wtime1) {\n" +
            "    this->utime0 = utime0;\n" +
            "    this->utime1 = utime1;\n" +
            "    this->stime0 = stime0;\n" +
            "    this->stime1 = stime1;\n" +
            "    this->wtime0 = wtime0;\n" +
            "    this->wtime1 = wtime1;\n" +
            "}\n" +
            "\n" +
            "void Chronometer::uswtime(double *usertime, double *systime, double *walltime) {\n" +
            "    struct rusage buffer;\n" +
            "    struct timeval tp;\n" +
            "    struct timezone tzp;\n" +
            "\n" +
            "    getrusage(RUSAGE_SELF, &buffer);\n" +
            "    gettimeofday(&tp, &tzp);\n" +
            "    *usertime = (double) buffer.ru_utime.tv_sec + 1.0e-6 * buffer.ru_utime.tv_usec;\n" +
            "    *systime = (double) buffer.ru_stime.tv_sec + 1.0e-6 * buffer.ru_stime.tv_usec;\n" +
            "    *walltime = (double) tp.tv_sec + 1.0e-6 * tp.tv_usec;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getUtime0() const {\n" +
            "    return utime0;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getStime0() const {\n" +
            "    return stime0;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getWtime0() const {\n" +
            "    return wtime0;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getUtime1() const {\n" +
            "    return utime1;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getStime1() const {\n" +
            "    return stime1;\n" +
            "}\n" +
            "\n" +
            "double Chronometer::getWtime1() const {\n" +
            "    return wtime1;\n" +
            "}";

    String codeChronometerH = ""+
            "#ifndef CHRONOMETER_CHRONOMETER_H\n" +
            "#define CHRONOMETER_CHRONOMETER_H\n" +
            "\n" +
            "#include <sys/resource.h>\n" +
            "#include <sys/time.h>\n" +
            "#include <malloc.h>\n" +
            "#include <cstdlib>\n" +
            "\n" +
            "class Chronometer {\n" +
            "private:\n" +
            "    double utime0;\n" +
            "    double stime0;\n" +
            "    double wtime0;\n" +
            "    double utime1;\n" +
            "    double stime1;\n" +
            "    double wtime1;\n" +
            "\n" +
            "private:\n" +
            "    /**\n" +
            "     * Contructor privado\n" +
            "     */\n" +
            "    Chronometer();\n" +
            "\n" +
            "    void uswtime(double *usertime, double *systime, double *walltime);\n" +
            "\n" +
            "public:\n" +
            "\n" +
            "    void init(double utime0, double utime1, double stime0, double stime1,double wtime0, double wtime1);\n" +
            "\n" +
            "    /**\n" +
            "     * Entrega una nueva instancia\n" +
            "     * @return Instancia nueva de Cronometer\n" +
            "     */\n" +
            "    static Chronometer* getInstance();\n" +
            "\n" +
            "    /**\n" +
            "     * inicia o reinicia el cronómetro cronom\n" +
            "     * @param cronom\n" +
            "     */\n" +
            "    void startClock(Chronometer*);\n" +
            "\n" +
            "    /**\n" +
            "     * Detiene el cronómetro y devuelve el timepo de CPU (user+system) en segundos\n" +
            "     * si el cronometro no se ha inciado retorna un valor negativo.\n" +
            "     *\n" +
            "     * @param cronom\n" +
            "     * @return\n" +
            "     */\n" +
            "    double stopClock(Chronometer*);\n" +
            "\n" +
            "    /**\n" +
            "     * Entrega los tiempos si el cronometro esta detenido, de otro modo entrega -1\n" +
            "     * @param cronom\n" +
            "     * @return\n" +
            "     */\n" +
            "    double userTime(Chronometer*);\n" +
            "\n" +
            "    /**\n" +
            "     *\n" +
            "     * @param cronom\n" +
            "     * @return\n" +
            "     */\n" +
            "    double sysTime(Chronometer*);\n" +
            "\n" +
            "    /**\n" +
            "     *\n" +
            "     * @param cronom\n" +
            "     * @return\n" +
            "     */\n" +
            "    double wallTime(Chronometer*);\n" +
            "\n" +
            "    double getUtime0() const;\n" +
            "\n" +
            "    double getStime0() const;\n" +
            "\n" +
            "    double getWtime0() const;\n" +
            "\n" +
            "    double getUtime1() const;\n" +
            "\n" +
            "    double getStime1() const;\n" +
            "\n" +
            "    double getWtime1() const;\n" +
            "};\n" +
            "\n" +
            "\n" +
            "#endif //CHRONOMETER_CHRONOMETER_H";
}
