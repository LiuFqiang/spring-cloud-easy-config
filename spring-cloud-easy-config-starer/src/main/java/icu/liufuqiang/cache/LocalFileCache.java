package icu.liufuqiang.cache;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * @author liufuqiang
 * @Date 2024-07-09 11:35:04
 */
public class LocalFileCache {

	public static final String LOCAL_SNAPSHOT_PATH;

	static {
		LOCAL_SNAPSHOT_PATH = System.getProperty("JM.SNAPSHOT.PATH",
				System.getProperty("user.home")) + File.separator + "jdbcconfig"
				+ File.separator + "config";
	}

	public static void saveSnapshotFile(String dataId, String content) {
		File file = new File(LOCAL_SNAPSHOT_PATH + File.separator + dataId + ".yaml");
		if (StrUtil.isBlank(content)) {
			file.delete();
		}
		FileUtil.writeString(content, file, Charset.defaultCharset());
	}

	public static File getSnapshotFileString(String dataId) {
		return new File(LOCAL_SNAPSHOT_PATH + File.separator + dataId + ".yaml");
	}

	public static String getContentFromDisk(String dataId) {
		File file = getSnapshotFileString(dataId);
		if (!file.exists() || !file.isFile()) {
			return null;
		}

		try {
			return IoUtil.read(Files.newInputStream(file.toPath()),
					Charset.defaultCharset());
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
