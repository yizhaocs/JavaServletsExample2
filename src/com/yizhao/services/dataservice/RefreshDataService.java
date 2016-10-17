package com.yizhao.services.dataservice;

import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yzhao on 10/17/16.
 */
public class RefreshDataService extends BaseDataService {

    static Logger log = Logger.getLogger("PixelDataService.class");

    private int refreshInterval = 60; // seconds

    private Map<Integer, DataProvider> dataProviders = new HashMap<Integer, DataProvider>();


    private Date lastRefresh;

    private final ScheduledExecutorService refreshThread = Executors.newSingleThreadScheduledExecutor();


    public void refresh() {
        long start = System.currentTimeMillis();

        dataProviderRefresh();

        super.refresh();
        lastRefresh = new Date();
        log.info("PixelDataService.refresh et:"	+ (System.currentTimeMillis() - start));
    }

    public void init() {
        refresh();
        // start background refresh thread
        refreshThread.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                Thread.currentThread().setName("PixelDataService");
                try {
                    refresh();
                } catch (Exception e) {
                    log.error("PixelDataService.refresh:", e);
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

            HashMap<Integer, DataProvider> tmpDataProviders = new HashMap<Integer, DataProvider>();

            List<DataProvider> retval = new ArrayList<DataProvider>();
            String DATA_PROVIDERS_SQL = "select id, name, do_pixel, ifnull(has_offline_data, 0) as has_offline_data, sync_facebook from data_providers";
            List<Map<String, Object>> rows = this
                    .getResults(DATA_PROVIDERS_SQL);
            for (Map<String, Object> row : rows) {
                DataProvider dp = new DataProvider();
                dp.setId((Integer) row.get("id"));
                dp.setName((String) row.get("name"));
                dp.setDoPixel(((Integer) row.get("do_pixel")).byteValue());
                dp.setHasOfflineData(((Long) row.get("has_offline_data")) == 0 ? false	: true);
                dp.setSyncFacebook((Boolean) row.get("sync_facebook"));
                retval.add(dp);
            }

            for (DataProvider dp : retval) {
                if (dp.getDoPixel() == DataProvider.DoPixel.ENABLED) {
                    tmpDataProviders.put(dp.getId(), dp);
                } else {
                    log.info(String.format("dataProvider %s is disabled",dp.getId()));
                }
            }
            log.debug("Valid data providers: " + tmpDataProviders.keySet());
            this.dataProviders = tmpDataProviders;
        } catch (Exception e) {
            log.error("Unable to refresh Data Providers", e);
        }
    }

    public Map<Integer, DataProvider> getDataProviders(){
        return dataProviders;
    }

}
