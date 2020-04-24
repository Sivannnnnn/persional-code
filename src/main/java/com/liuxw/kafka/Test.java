package com.liuxw.kafka;

import com.liuxw.utils.StringUtils;
import net.jpountz.xxhash.StreamingXXHash32;
import net.jpountz.xxhash.XXHashFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author  XiaoWen
 * Date  2019/8/1 16:58
 * Version 1.0
 */
public class Test {

    public static void main(String[] args) throws IOException {
        XXHashFactory factory = XXHashFactory.fastestInstance();

        byte[] data = "54065-0".getBytes("UTF-8");
        ByteArrayInputStream in = new ByteArrayInputStream(data);

        int seed = 0; // used to initialize the hash value, use whatever
        // value you want, but always the same
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
        System.out.println(StringUtils.intUnsigned(hash));
    }

    public static void mainxx(String[] args) {
        Logger logger = LogManager.getLogger(Test.class);
        logger.debug("Debug Level");
        logger.info("Info Level");
        logger.warn("Warn Level");
        logger.error("Error Level");
    }

    public static void main2(String[] args) {
        String a = "xxx";
        a.replace('x','a');
        System.out.println(a);
        a = a.replace('x','a');
        System.out.println(a);

    }
}
