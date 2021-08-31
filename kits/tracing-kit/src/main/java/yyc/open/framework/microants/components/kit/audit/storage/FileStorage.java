package yyc.open.framework.microants.components.kit.audit.storage;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Elias (siran0611@gmail.com)
 */
public class FileStorage extends AbstractStorage {
	private RandomAccessFile raf;


	@Override
	public void close() throws IOException {
		raf.close();
	}
}
