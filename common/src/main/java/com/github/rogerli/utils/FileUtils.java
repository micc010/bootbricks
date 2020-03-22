package com.github.rogerli.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@Component
public class FileUtils {

    private final String LINE_SEP = System.getProperty("line.separator");

    /**
     * Return the file by path.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the file
     */
    public File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * Return whether the file exists.
     *
     * @param filePath
     *         The path of file.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * Return whether the file exists.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * Rename the file.
     *
     * @param filePath
     *         The path of file.
     * @param newName
     *         The new name of file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean rename(final String filePath, final String newName) {
        return rename(getFileByPath(filePath), newName);
    }

    /**
     * Rename the file.
     *
     * @param file
     *         The file.
     * @param newName
     *         The new name of file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean rename(final File file, final String newName) {
        // file is null then return false
        if (file == null) return false;
        // file doesn't exist then return false
        if (!file.exists()) return false;
        // the new name is space then return false
        if (isSpace(newName)) return false;
        // the new name equals old name then return true
        if (newName.equals(file.getName())) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        // the new name of file exists then return false
        return !newFile.exists()
                && file.renameTo(newFile);
    }

    /**
     * Return whether it is a directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isDir(final String dirPath) {
        return isDir(getFileByPath(dirPath));
    }

    /**
     * Return whether it is a directory.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * Return whether it is a file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * Return whether it is a file.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: yes<br>{@code false}: no
     */
    public boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param filePath
     *         The path of file.
     *
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * Create a file if it doesn't exist, otherwise do nothing.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create a file if it doesn't exist, otherwise delete old file before creating.
     *
     * @param filePath
     *         The path of file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * Create a file if it doesn't exist, otherwise delete old file before creating.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        // file exists and unsuccessfully delete then return false
        if (file.exists() && !file.delete()) return false;
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Delete the directory.
     *
     * @param filePath
     *         The path of file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean delete(final String filePath) {
        return delete(getFileByPath(filePath));
    }

    /**
     * Delete the directory.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean delete(final File file) {
        if (file == null) return false;
        if (file.isDirectory()) {
            return deleteDir(file);
        }
        return deleteFile(file);
    }

    /**
     * Delete the directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * Delete the directory.
     *
     * @param dir
     *         The directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteDir(final File dir) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Delete the file.
     *
     * @param srcFilePath
     *         The path of source file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFile(final String srcFilePath) {
        return deleteFile(getFileByPath(srcFilePath));
    }

    /**
     * Delete the file.
     *
     * @param file
     *         The file.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * Delete the all in directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteAllInDir(final String dirPath) {
        return deleteAllInDir(getFileByPath(dirPath));
    }

    /**
     * Delete the all in directory.
     *
     * @param dir
     *         The directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteAllInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    /**
     * Delete all files in directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFilesInDir(final String dirPath) {
        return deleteFilesInDir(getFileByPath(dirPath));
    }

    /**
     * Delete all files in directory.
     *
     * @param dir
     *         The directory.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFilesInDir(final File dir) {
        return deleteFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * Delete all files that satisfy the filter in directory.
     *
     * @param dirPath
     *         The path of directory.
     * @param filter
     *         The filter.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFilesInDirWithFilter(final String dirPath,
                                              final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * Delete all files that satisfy the filter in directory.
     *
     * @param dir
     *         The directory.
     * @param filter
     *         The filter.
     *
     * @return {@code true}: success<br>{@code false}: fail
     */
    public boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        // dir doesn't exist then return true
        if (!dir.exists()) return true;
        // dir isn't a directory then return false
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isFile()) {
                        if (!file.delete()) return false;
                    } else if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Return the files in directory. <p>Doesn't traverse subdirectories</p>
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return the files in directory
     */
    public List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(dirPath, false);
    }

    /**
     * Return the files in directory. <p>Doesn't traverse subdirectories</p>
     *
     * @param dir
     *         The directory.
     *
     * @return the files in directory
     */
    public List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    /**
     * Return the files in directory.
     *
     * @param dirPath
     *         The path of directory.
     * @param isRecursive
     *         True to traverse subdirectories, false otherwise.
     *
     * @return the files in directory
     */
    public List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    /**
     * Return the files in directory.
     *
     * @param dir
     *         The directory.
     * @param isRecursive
     *         True to traverse subdirectories, false otherwise.
     *
     * @return the files in directory
     */
    public List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    /**
     * Return the files that satisfy the filter in directory. <p>Doesn't traverse subdirectories</p>
     *
     * @param dirPath
     *         The path of directory.
     * @param filter
     *         The filter.
     *
     * @return the files that satisfy the filter in directory
     */
    public List<File> listFilesInDirWithFilter(final String dirPath,
                                               final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    /**
     * Return the files that satisfy the filter in directory. <p>Doesn't traverse subdirectories</p>
     *
     * @param dir
     *         The directory.
     * @param filter
     *         The filter.
     *
     * @return the files that satisfy the filter in directory
     */
    public List<File> listFilesInDirWithFilter(final File dir,
                                               final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    /**
     * Return the files that satisfy the filter in directory.
     *
     * @param dirPath
     *         The path of directory.
     * @param filter
     *         The filter.
     * @param isRecursive
     *         True to traverse subdirectories, false otherwise.
     *
     * @return the files that satisfy the filter in directory
     */
    public List<File> listFilesInDirWithFilter(final String dirPath,
                                               final FileFilter filter,
                                               final boolean isRecursive) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, isRecursive);
    }

    /**
     * Return the files that satisfy the filter in directory.
     *
     * @param dir
     *         The directory.
     * @param filter
     *         The filter.
     * @param isRecursive
     *         True to traverse subdirectories, false otherwise.
     *
     * @return the files that satisfy the filter in directory
     */
    public List<File> listFilesInDirWithFilter(final File dir,
                                               final FileFilter filter,
                                               final boolean isRecursive) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    list.add(file);
                }
                if (isRecursive && file.isDirectory()) {
                    //noinspection ConstantConditions
                    list.addAll(listFilesInDirWithFilter(file, filter, true));
                }
            }
        }
        return list;
    }

    /**
     * Return the time that the file was last modified.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the time that the file was last modified
     */

    public long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    /**
     * Return the time that the file was last modified.
     *
     * @param file
     *         The file.
     *
     * @return the time that the file was last modified
     */
    public long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    /**
     * Return the charset of file simply.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the charset of file simply
     */
    public String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    /**
     * Return the charset of file simply.
     *
     * @param file
     *         The file.
     *
     * @return the charset of file simply
     */
    public String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    /**
     * Return the number of lines of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the number of lines of file
     */
    public int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    /**
     * Return the number of lines of file.
     *
     * @param file
     *         The file.
     *
     * @return the number of lines of file
     */
    public int getFileLines(final File file) {
        int count = 1;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            int readChars;
            if (LINE_SEP.endsWith("\n")) {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\n') ++count;
                    }
                }
            } else {
                while ((readChars = is.read(buffer, 0, 1024)) != -1) {
                    for (int i = 0; i < readChars; ++i) {
                        if (buffer[i] == '\r') ++count;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    /**
     * Return the size of directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return the size of directory
     */
    public String getDirSize(final String dirPath) {
        return getDirSize(getFileByPath(dirPath));
    }

    /**
     * Return the size of directory.
     *
     * @param dir
     *         The directory.
     *
     * @return the size of directory
     */
    public String getDirSize(final File dir) {
        long len = getDirLength(dir);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * Return the length of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the length of file
     */
    public String getFileSize(final String filePath) {
        long len = getFileLength(filePath);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * Return the length of file.
     *
     * @param file
     *         The file.
     *
     * @return the length of file
     */
    public String getFileSize(final File file) {
        long len = getFileLength(file);
        return len == -1 ? "" : byte2FitMemorySize(len);
    }

    /**
     * Return the length of directory.
     *
     * @param dirPath
     *         The path of directory.
     *
     * @return the length of directory
     */
    public long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    /**
     * Return the length of directory.
     *
     * @param dir
     *         The directory.
     *
     * @return the length of directory
     */
    public long getDirLength(final File dir) {
        if (!isDir(dir)) return -1;
        long len = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    len += getDirLength(file);
                } else {
                    len += file.length();
                }
            }
        }
        return len;
    }

    /**
     * Return the length of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the length of file
     */
    public long getFileLength(final String filePath) {
        boolean isURL = filePath.matches("[a-zA-z]+://[^\\s]*");
        if (isURL) {
            try {
                HttpsURLConnection conn = (HttpsURLConnection) new URL(filePath).openConnection();
                conn.setRequestProperty("Accept-Encoding", "identity");
                conn.connect();
                if (conn.getResponseCode() == 200) {
                    return conn.getContentLength();
                }
                return -1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getFileLength(getFileByPath(filePath));
    }

    /**
     * Return the length of file.
     *
     * @param file
     *         The file.
     *
     * @return the length of file
     */
    public long getFileLength(final File file) {
        if (!isFile(file)) return -1;
        return file.length();
    }

    /**
     * Return the MD5 of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the md5 of file
     */
    public String getFileMD5ToString(final String filePath) {
        File file = isSpace(filePath) ? null : new File(filePath);
        return getFileMD5ToString(file);
    }

    /**
     * Return the MD5 of file.
     *
     * @param file
     *         The file.
     *
     * @return the md5 of file
     */
    public String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }

    /**
     * Return the MD5 of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the md5 of file
     */
    public byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    /**
     * Return the MD5 of file.
     *
     * @param file
     *         The file.
     *
     * @return the md5 of file
     */
    public byte[] getFileMD5(final File file) {
        if (file == null) return null;
        DigestInputStream dis = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Return the file's path of directory.
     *
     * @param file
     *         The file.
     *
     * @return the file's path of directory
     */
    public String getDirName(final File file) {
        if (file == null) return "";
        return getDirName(file.getAbsolutePath());
    }

    /**
     * Return the file's path of directory.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the file's path of directory
     */
    public String getDirName(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? "" : filePath.substring(0, lastSep + 1);
    }

    /**
     * Return the name of file.
     *
     * @param file
     *         The file.
     *
     * @return the name of file
     */
    public String getFileName(final File file) {
        if (file == null) return "";
        return getFileName(file.getAbsolutePath());
    }

    /**
     * Return the name of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the name of file
     */
    public String getFileName(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastSep = filePath.lastIndexOf(File.separator);
        return lastSep == -1 ? filePath : filePath.substring(lastSep + 1);
    }

    /**
     * Return the name of file without extension.
     *
     * @param file
     *         The file.
     *
     * @return the name of file without extension
     */
    public String getFileNameNoExtension(final File file) {
        if (file == null) return "";
        return getFileNameNoExtension(file.getPath());
    }

    /**
     * Return the name of file without extension.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the name of file without extension
     */
    public String getFileNameNoExtension(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastSep == -1) {
            return (lastPoi == -1 ? filePath : filePath.substring(0, lastPoi));
        }
        if (lastPoi == -1 || lastSep > lastPoi) {
            return filePath.substring(lastSep + 1);
        }
        return filePath.substring(lastSep + 1, lastPoi);
    }

    /**
     * Return the extension of file.
     *
     * @param file
     *         The file.
     *
     * @return the extension of file
     */
    public String getFileExtension(final File file) {
        if (file == null) return "";
        return getFileExtension(file.getPath());
    }

    /**
     * Return the extension of file.
     *
     * @param filePath
     *         The path of file.
     *
     * @return the extension of file
     */
    public String getFileExtension(final String filePath) {
        if (isSpace(filePath)) return "";
        int lastPoi = filePath.lastIndexOf('.');
        int lastSep = filePath.lastIndexOf(File.separator);
        if (lastPoi == -1 || lastSep >= lastPoi) return "";
        return filePath.substring(lastPoi + 1);
    }

    private final char HEX_DIGITS[] =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private String bytes2HexString(final byte[] bytes) {
        if (bytes == null) return "";
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = HEX_DIGITS[bytes[i] >> 4 & 0x0f];
            ret[j++] = HEX_DIGITS[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    private String byte2FitMemorySize(final long byteNum) {
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(Locale.getDefault(), "%.3fB", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(Locale.getDefault(), "%.3fKB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(Locale.getDefault(), "%.3fMB", (double) byteNum / 1048576);
        } else {
            return String.format(Locale.getDefault(), "%.3fGB", (double) byteNum / 1073741824);
        }
    }

    private boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean writeFileFromIS(final File file,
                                    final InputStream is) {
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            byte data[] = new byte[8192];
            int len;
            while ((len = is.read(data, 0, 8192)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}