package cn.huangchaosuper;

import cn.huangchaosuper.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by I311579 on 10/19/2015.
 */
public class Elasticollect {
    private static Logger logger = LoggerFactory.getLogger(Elasticollect.class);
    public static void main(String[] args) {
        String configPath = System.getProperty("config.path");
        if (configPath == null || configPath.equals("")) {
            logger.error(Utils.getElasticollectResourceString("config.configFileParamNotDefine"));
            return;
        }
        if(!Utils.loadConfigFile(configPath)){
            logger.error(Utils.getElasticollectResourceString("config.configFileNotBeingFound"));
            return;
        }

        TaskRegisterFactory taskRegisterFactory = TaskRegisterFactory.getInstance();
        try {
            taskRegisterFactory.initialization();
            taskRegisterFactory.execution();
        } catch (IOException e) {
            logger.error("taskRegisterFactory",e.fillInStackTrace());
        }
    }
}
