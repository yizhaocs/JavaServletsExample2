/*
package com.yizhao.services.fileprocessservice;

import org.apache.commons.lang3.text.StrTokenizer;

import java.io.File;

*/
/**
 * Created by yzhao on 10/17/16.
 *//*

public class FileProcessingTask implements Runnable {
    private File file;
    private Boolean isValidator;
    private Boolean shouldWriteClog;

    private StrTokenizer pipeTokenizer;

    public FileProcessingTask(File file,  Boolean isValidator, Boolean shouldWriteClog) {
        this.file = file;
        this.isValidator = isValidator;
        this.shouldWriteClog = shouldWriteClog;

        pipeTokenizer = StrTokenizer.getCSVInstance();
        pipeTokenizer.setDelimiterChar('|');
    }

    public void run() {
        // if it's the validator thread, then we want to wait 30 seconds first, giving it some time
        // to drain the async queue from udcu to bidgen
        if (isValidator) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                // do nothing
            }
        }

        boolean success =
                new FileParser(isValidator) {
                    */
/**
                     * process the line data in the CSV file
                     * we will get the specific data processor to process this line
                     *
                     * @param line the data line
                     * @param fileName the file name
                     * @param lineNo the line number
                     * @throws Exception
                     *//*

                    public void processData(String line, StrTokenizer pipeTokenizer, String fileName, int lineNo, boolean needsFilter) throws Exception {
                        if (line != null) {

                        }
                    }

                }.processFile(file, pipeTokenizer);

        if (!isValidator) {
            synchronized (processingFiles) {
                if (success) {
                    File archiveFile = helper.moveFileToArchiveFolder(file);
                    processingFiles.remove(file.getName());

//						// only when we really processed the file, we add it to the udcu_files_processed table
//						// in the case we skip the file because of source not match, we won't add it
//						try {
//							UdcuUtil.addToProcessedFiles(file.getName(), helper.getDataSource());
//						} catch (Exception e) {
//							// in case of exception, we will continue processing
//							log.error("error adding file to the udcu_file_processed table", e);
//						}

                    processValidation(archiveFile);

                    log.info("finished processing " + file);
                }
                else {
                    helper.moveFileToErrorFolder(file);
                    processingFiles.remove(file.getName());

                    log.error("error processing " + file);
                }
            }
        }
    }

}*/
