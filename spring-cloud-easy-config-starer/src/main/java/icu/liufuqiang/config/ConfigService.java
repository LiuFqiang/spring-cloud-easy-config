package icu.liufuqiang.config;

import icu.liufuqiang.listener.Listener;

/**
 * @author liufuqiang
 * @Date 2024-07-08 15:52:27
 */
public interface ConfigService {

	String getConfig(String dataId);

	boolean addListener(String dataId, Listener listener);

}
