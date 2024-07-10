package icu.liufuqiang.cache;

import cn.hutool.crypto.digest.MD5;
import icu.liufuqiang.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author liufuqiang
 * @Date 2024-07-09 09:39:05
 */
public class CacheData {

	private static final Logger log = LoggerFactory.getLogger(CacheData.class);

	private String dataId;

	private String content;

	private String md5;

	public CopyOnWriteArrayList<ManagerListenerWrap> listeners;

	public CacheData(String dataId) {
		this.dataId = dataId;
		listeners = new CopyOnWriteArrayList<ManagerListenerWrap>();
		content = LocalFileCache.getContentFromDisk(dataId);
		md5 = MD5.create().digestHex(content);
	}

	public void addListener(Listener listener) {
		if (listener == null) {
			throw new IllegalArgumentException("listener is null");
		}
		try {
			ManagerListenerWrap wrap = new ManagerListenerWrap(listener, md5);
			listeners.addIfAbsent(wrap);
		} catch (Exception e) {

		}
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		this.md5 = MD5.create().digestHex(content);
	}

	class ManagerListenerWrap {

		private Listener listener;

		private String lastMd5;

		public ManagerListenerWrap(Listener listener, String md5) {
			this.listener = listener;
			this.lastMd5 = md5;
		}

		public Listener getListener() {
			return listener;
		}

		@Override
		public boolean equals(Object obj) {
			if (null == obj || obj.getClass() != getClass()) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			ManagerListenerWrap other = (ManagerListenerWrap) obj;
			return listener.equals(other.listener);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}
	}

	public void checkListenerMd5() {
		for (ManagerListenerWrap listener : listeners) {
			if (!listener.lastMd5.equals(md5)) {
				sendNotifyListener(listener);
				LocalFileCache.saveSnapshotFile(dataId, content);
			}
		}
	}

	private void sendNotifyListener(ManagerListenerWrap listenerWrap) {
		Listener listener = listenerWrap.getListener();
		Runnable runnable = () -> {
			listenerWrap.lastMd5 = md5;
			listener.receiveConfigInfo(dataId, content);
		};
		runnable.run();
		log.info("notify listener success.");
	}

}
