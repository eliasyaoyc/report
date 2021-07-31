package yyc.open.framework.microants.components.kit.compress;

import net.sf.sevenzipjbinding.*;
import yyc.open.framework.microants.components.kit.common.file.FilenameKit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class ExtractCallback implements IArchiveExtractCallback {

    private static final Logger LOG = Logger.getLogger(ExtractCallback.class.getName());

    private final boolean ignoreFolder;
    private int hash = 0;
    private int size = 0;
    private int index;
    private boolean isFolder;
    private IInArchive inArchive;
    private Map<String, byte[]> filesExtracteds;

    public ExtractCallback(final IInArchive inArchive, final boolean ignoreFolder) {
        this.inArchive = inArchive;
        this.ignoreFolder = ignoreFolder;
        this.filesExtracteds = new HashMap<>();
    }

    @Override
    public ISequentialOutStream getStream(int index, ExtractAskMode extractAskMode) throws SevenZipException {
        this.index = index;
        this.isFolder = (Boolean) inArchive.getProperty(index, PropID.IS_FOLDER);

        if (isIgnoreFolder() || extractAskMode != ExtractAskMode.EXTRACT) {
            return null;
        }

        final String fileName = FilenameKit.getName((String) inArchive.getProperty(index, PropID.PATH));
        return data -> {
            hash ^= Arrays.hashCode(data);
            size += data.length;
            filesExtracteds.put(fileName, data);
            return data.length;
        };
    }

    private boolean isIgnoreFolder() {
        return isFolder && ignoreFolder;
    }

    @Override
    public void prepareOperation(ExtractAskMode extractAskMode) {
    }

    @Override
    public void setOperationResult(ExtractOperationResult extractOperationResult) throws SevenZipException {
        if (isIgnoreFolder()) {
            return;
        }
        if (extractOperationResult != ExtractOperationResult.OK) {
            LOG.severe("Extraction error");
        } else {
            LOG.info(String.format("%9X\t|\t%10s\t|\t%s", hash, size, inArchive.getProperty(index, PropID.PATH)));
            hash = 0;
            size = 0;
        }
    }

    @Override
    public void setTotal(long total) {
    }

    @Override
    public void setCompleted(long completeValue) {
    }

    public Map<String, byte[]> getFilesExtracteds() {
        return filesExtracteds;
    }

}