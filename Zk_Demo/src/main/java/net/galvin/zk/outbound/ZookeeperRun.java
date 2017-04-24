package net.galvin.zk.outbound;

/**
 * 直接运行此文件，说明：
 * 1、运行前按需要修改sourceAddr，targetAddr，appNames三个参数
 * 例如在接入环境的时候：sourceAddr为独立环境项目注册的zookeeper地址，targetAddr为本地zookeeper地址，appNames为要接入的项目名称
 *
 * 2、在主方法里面，不需要的注释掉即可
 * 例如只需要接入环境，则将其他都注释掉即可
 *
 * 3、在协同开发一个需求时，也可以使用该文件接入环境
 * 例如A负责vst_back、B负责vst_ebooking。由于vst_ebooking需要调用vst_back时，B将sourceAddr改为A的zk地址，就可以接入本地了
 *
 *4、targetAddr注意不能随意修改，如果改到了环境或者其他人的地址，则会影响别人的开发
 *
 * Created by yecheng on 2016/12/8.
 */
public class ZookeeperRun {

    private static final String sourceAddr="10.113.10.190:2181";//源zookeeper地址 ip:port

    private static final String targetAddr="127.0.0.1:2181";//目标zookeeper地址 ip:port

    //运行指定的application名称
    private static String[] appNames ={"vst_prom"};

    public static void main(String[] args){
        /**
         * 这部分包含：zk环境接入、本地zk清理
         *
         */
        //接入环境zookeeper
        DubboTransferHelper.switchZK(sourceAddr,targetAddr,appNames);
        //清理本地zk
        //DubboTransferHelper.cleanLocalZK(targetAddr,appNames);

        /**
         * 这部分包含：从备份恢复到zk、清理本地zk的备份
         */
        //从本地备份恢复到zk
        //DubboTransferHelper.recoveryLocalZK(targetAddr,appNames);
        //清理本地备份的zk
        //DubboTransferHelper.cleanLocalZKBak(targetAddr,appNames);
        //清理本地所有的备份
        //DubboTransferHelper.cleanLocalZKBakAll(targetAddr);

    }
}
