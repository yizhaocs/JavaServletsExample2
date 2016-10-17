package com.yizhao.services.refreshdataservice;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yzhao on 10/17/16.
 */
public class RefreshDataServiceMain extends BaseDataService {

    static Logger log = Logger.getLogger("RefreshDataServiceMain.class");

    private int refreshInterval = 60; // seconds

    private Map<Integer, RefreshExampleDTO> dataProviders = new HashMap<Integer, RefreshExampleDTO>();


    private Date lastRefresh;

    private final ScheduledExecutorService refreshThread = Executors.newSingleThreadScheduledExecutor();


    public void refresh() {
        long start = System.currentTimeMillis();

        dataProviderRefresh();

        super.refresh();
        lastRefresh = new Date();
        log.info("RefreshDataServiceMain.refresh et:"	+ (System.currentTimeMillis() - start));
    }

    public void init() {
        refresh();
        // start background refresh thread
        refreshThread.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Thread.currentThread().setName("RefreshDataServiceMain");
                try {
                    refresh();
                } catch (Exception e) {
                    log.error("RefreshDataServiceMain.refresh:", e);
                }

            }
        }, refreshInterval, refreshInterval, TimeUnit.SECONDS);

    }

    public int getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(int refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public void destroy() {
        refreshThread.shutdownNow();
    }

    public Date getLastRefresh() {
        return lastRefresh;
    }


    private void dataProviderRefresh() {
        log.info("PixelServerGlobal.dataProviderRefresh()");

		/*
		 * Method to lookup data providers from marketplace
		 */

        try {

            HashMap<Integer, RefreshExampleDTO> tmpDataProviders = new HashMap<Integer, RefreshExampleDTO>();

            List<RefreshExampleDTO> retval = new ArrayList<RefreshExampleDTO>();
            String DATA_PROVIDERS_SQL = "select id, name from refresh_example";
            List<Map<String, Object>> rows = this
                    .getResults(DATA_PROVIDERS_SQL);


            for (Map<String, Object> row : rows) {
                RefreshExampleDTO dp = new RefreshExampleDTO();
                dp.setId((Integer) row.get("id"));
                dp.setName((String) row.get("name"));
                retval.add(dp);
            }

            for (RefreshExampleDTO dp : retval) {
                tmpDataProviders.put(dp.getId(), dp);
            }

            log.debug("Valid rows: " + tmpDataProviders.keySet());
            this.dataProviders = tmpDataProviders;
        } catch (Exception e) {
            log.error("Unable to refresh table", e);
        }
    }

    public Map<Integer, RefreshExampleDTO> getDataProviders(){
        return dataProviders;
    }

}
