package net.galvin.es.demo.client;

import net.galvin.comm.ExceptionFormatUtil;
import net.galvin.comm.StringUtils;
import net.galvin.es.demo.EsClusterClient;
import net.galvin.es.demo.ProdQueryWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {

    private final static Logger LOGGER = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        ProdQueryWrapper.get().save(5l,2l,5l);
        ProdQueryWrapper.get().query(5l,2l);
    }

}
