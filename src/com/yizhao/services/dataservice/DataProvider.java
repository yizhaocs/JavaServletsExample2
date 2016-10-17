package com.yizhao.services.dataservice;


import java.io.Serializable;

/**
 * A DataProvider provides User Data to Opinmind
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
