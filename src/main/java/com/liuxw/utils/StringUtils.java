package com.liuxw.utils;

import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Author  XiaoWen
 * Date  2020/4/24 15:00
 * Version 1.0
 */
public class StringUtils {


    /**
     * 将文本用xxhash32算法转为int值
     * @param text 需要xxhash的文本
     * @param seed xxhash的种子
     * @return
     * @throws IOException
     */
    public long xxHash32(String text,int seed) throws IOException {

        XXHashFactory factory = XXHashFactory.fastestInstance();

        byte[] data = text.getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(data);


        //int seed = 0; // used to initialize the hash value, use whatever
                         // value you want, but always the same
                        //  seed clickhouse默认是0
        StreamingXXHash32 hash32 = factory.newStreamingHash32(seed);
        byte[] buf = new byte[8]; // for real-world usage, use a larger buffer, like 8192 bytes
        for (;;) {
            int read = in.read(buf);
            if (read == -1) {
                break;
            }
            hash32.update(buf, 0, read);
        }
        int hash = hash32.getValue();
        return intUnsigned(hash);
    }

    /**
     * 有符号int转无符号uint32, 用java的long表示
     * @param v
     * @return
     */
    public static long intUnsigned(int v) {
        long v1 = (byte) v & 255L;
        long v2 = (byte) (v >> 8) & 255L;
        long v3 = (byte) (v >> 16) & 255L;
        long v4 = (byte) (v >> 24) & 255L;
        return v1 | v2 << 8 | v3 << 16 | v4 << 24;
    }
}
