package com.yizhao.services.refreshdataservice;


import java.io.Serializable;

/**
 * A DataProvider provides User Data to Opinmind
 */

/*
            CREATE TABLE `refresh_example` (
            `id` int(11) NOT NULL AUTO_INCREMENT,
            `name` varchar(64) NOT NULL,
            PRIMARY KEY (`id`)
            );
*/
public class RefreshExampleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public RefreshExampleDTO() {}

    public RefreshExampleDTO(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if( obj instanceof RefreshExampleDTO){
            RefreshExampleDTO other = (RefreshExampleDTO) obj;
            return this.getId() == other.getId();
        }

        return false;
    }

}
