/*
package com.yizhao.services.fileprocessservice;

import org.apache.commons.lang3.text.StrTokenizer;

import java.io.*;

*/
/**
 * Created by yzhao on 10/17/16.
 *//*

public abstract class FileParser {
    boolean isValidator = false;

    public FileParser(boolean isValidator) {
        this.isValidator = isValidator;
    }

    */
/**
     * process a specific file
     *
     * @param file the file to process
     * @return whether the processing is successful
     *//*

    public boolean processFile(File file, StrTokenizer pipeTokenizer)
    {
        boolean success = false;
        BufferedReader bufferedReader = null;

        String line = null;
        int lineNo = 0;

        try {
            String host = helper.getHostFromFileName(file);
            boolean needsFilter = false;
            if (cookieFilterHostsArray != null) {
                for (String filterHost : cookieFilterHostsArray) {
                    if (host!=null && host.startsWith(filterHost)) {
                        needsFilter = true;
                        break;
                    }
                }
            }

            bufferedReader = new BufferedReader(new FileReader(file));
            // Read each line of text in the file
            while((line = bufferedReader.readLine()) != null)
            {
                // process the row data
                // if it's validator, then we only want to validate the first 1000 lines of data
                if (!isValidator || (lineNo < 1000)) {
                    processData(line, pipeTokenizer, file.getName(), lineNo, needsFilter);
                }
                lineNo++;

                if (lineNo % 100000 == 0)
                    log.info("processed " + lineNo + " lines for file: " + file);
            }

            success = true;
        } catch (FileNotFoundException e) {
            log.error("can't find the file " + file + ", line number " + lineNo + ". line content is " + line, e);
        } catch (IOException e) {
            log.error("can't read from the file " + file + ", line number " + lineNo + ". line content is " + line, e);
        } catch (Exception e) {
            log.error("failed to process file " + file + ", line number " + lineNo + ". line content is " + line, e);
        }
        finally {
            OpinmindUtil.close(bufferedReader);
        }

        return success;
    }

    abstract public void processData(String line, StrTokenizer pipeTokenizer, String fileName, int lineNo, boolean needsFilter) throws Exception;
}*/
