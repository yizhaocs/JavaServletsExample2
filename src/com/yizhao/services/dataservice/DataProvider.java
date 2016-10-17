package com.yizhao.services.dataservice;


import java.io.Serializable;

/**
 * A DataProvider provides User Data to Opinmind
 */

/*
            CREATE TABLE `data_providers` (
            `id` int(11) NOT NULL AUTO_INCREMENT,
            `name` varchar(64) NOT NULL,
            `do_pixel` tinyint(4) NOT NULL DEFAULT '1',
            `has_offline_data` tinyint(4) DEFAULT NULL COMMENT '1 if dp provides offline data',
            `is_partner` tinyint(1) NOT NULL DEFAULT '0',
            `is_advertiser` tinyint(1) NOT NULL DEFAULT '0',
            `region_id` int(11) DEFAULT NULL,
            `timezone` varchar(60) NOT NULL,
            `auto_approved` tinyint(1) NOT NULL DEFAULT '0',
            `status` char(1) DEFAULT NULL,
            `category_id` int(11) DEFAULT NULL,
            `currency` varchar(3) DEFAULT NULL,
            `europe_approved` tinyint(1) NOT NULL DEFAULT '0',
            `mobile_partner` tinyint(1) NOT NULL DEFAULT '0',
            `sync_facebook` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'whether this data provider wants to sync data with facebook',
            `modification_ts` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            PRIMARY KEY (`id`),
            KEY `modification_ts` (`modification_ts`),
            KEY `timezone` (`timezone`),
            KEY `name` (`name`),
            CONSTRAINT `data_providers_ibfk_1` FOREIGN KEY (`timezone`) REFERENCES `timezones` (`name`)
            )
*/
public class DataProvider implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;
    private byte doPixel;
    private Boolean hasOfflineData;
    private Boolean syncFacebook;

    public static class DoPixel {
        public static final byte DISABLED = 0x00;
        public static final byte ENABLED = 0x01;
    }

    public DataProvider() {}

    public DataProvider(int id, String name, byte doPixel) {
        this.id = id;
        this.name = name;
        this.doPixel = doPixel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getDoPixel() {
        return doPixel;
    }

    public void setDoPixel(byte doPixel) {
        this.doPixel = doPixel;
    }

    public Boolean getHasOfflineData() {
        return hasOfflineData;
    }

    public void setHasOfflineData(Boolean hasOfflineData) {
        this.hasOfflineData = hasOfflineData;
    }

    public Boolean getSyncFacebook() {
        return syncFacebook;
    }

    public void setSyncFacebook(Boolean syncFacebook) {
        this.syncFacebook = syncFacebook;
    }

    public String toString() {
        return String.format("DataProvider[%s,%s,%s,%s,%s]", id, name, doPixel, hasOfflineData, syncFacebook );
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof DataProvider ){
            DataProvider other = (DataProvider) obj;
            return this.getId() == other.getId();
        }

        return false;
    }

}
