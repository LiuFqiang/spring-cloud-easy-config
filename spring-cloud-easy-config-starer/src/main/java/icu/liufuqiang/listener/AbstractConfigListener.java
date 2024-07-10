package icu.liufuqiang.listener;

/**
 * @author liufuqiang
 * @Date 2024-07-09 17:29:17
 */
public abstract class AbstractConfigListener implements Listener{

    private String dataId;
    private String configInfo;

    @Override
    public void receiveConfigInfo(String dataId, String configInfo) {
        this.dataId = dataId;
        this.configInfo = configInfo;
        this.innerReceiveConfigInfo(dataId, configInfo);
    }

    public abstract void innerReceiveConfigInfo(String dataId, String configInfo);
}
