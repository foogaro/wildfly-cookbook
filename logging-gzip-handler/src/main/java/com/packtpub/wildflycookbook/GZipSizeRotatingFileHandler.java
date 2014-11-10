package com.packtpub.wildflycookbook;

import org.jboss.logmanager.ExtLogRecord;
import org.jboss.logmanager.handlers.SizeRotatingFileHandler;

import java.io.*;
import java.util.zip.GZIPOutputStream;

/**
 * Created by lfugaro on 9/21/14.
 */
public class GZipSizeRotatingFileHandler extends SizeRotatingFileHandler {

    // by default, rotate at 10MB
    private long rotateSize = 0xa0000L;
    private int maxBackupIndex = 1;

    public GZipSizeRotatingFileHandler() {
        super();
    }

    public GZipSizeRotatingFileHandler(final File file) throws FileNotFoundException {
        super(file);
    }

    public GZipSizeRotatingFileHandler(final File file, final boolean append) throws FileNotFoundException {
        super(file, append);
    }

    public GZipSizeRotatingFileHandler(final String fileName) throws FileNotFoundException {
        super(fileName);
    }

    public GZipSizeRotatingFileHandler(final String fileName, final boolean append) throws FileNotFoundException {
        super(fileName, append);
    }

    public GZipSizeRotatingFileHandler(final long rotateSize, final int maxBackupIndex) {
        this.rotateSize = rotateSize;
        this.maxBackupIndex = maxBackupIndex;
    }

    public GZipSizeRotatingFileHandler(final File file, final long rotateSize, final int maxBackupIndex) throws FileNotFoundException {
        super(file);
        this.rotateSize = rotateSize;
        this.maxBackupIndex = maxBackupIndex;
    }

    public GZipSizeRotatingFileHandler(final File file, final boolean append, final long rotateSize, final int maxBackupIndex) throws FileNotFoundException {
        super(file, append);
        this.rotateSize = rotateSize;
        this.maxBackupIndex = maxBackupIndex;
    }

    @Override
    protected void preWrite(ExtLogRecord record) {
        super.preWrite(record);

        final File file = getFile();
        final long currentSize = (file == null ? Long.MIN_VALUE : file.length());

        if (currentSize > rotateSize && maxBackupIndex > 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                FileOutputStream fos = new FileOutputStream(file.getAbsolutePath()+".gz");
                GZIPOutputStream gzipOS = new GZIPOutputStream(fos);
                byte[] buffer = new byte[1024];
                int len;
                while((len=fis.read(buffer)) != -1){
                    gzipOS.write(buffer, 0, len);
                }
                gzipOS.close();
                fos.close();
                fis.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
