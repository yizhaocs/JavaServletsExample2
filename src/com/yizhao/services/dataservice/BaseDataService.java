package com.yizhao.services.dataservice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yizhao.services.clogservice.Pair;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by yzhao on 10/17/16.
 */
public class BaseDataService extends MySQLDaoBase{

    static Logger log = Logger.getLogger("BaseDataService.class");

    protected int MAX_RETRIES = 10;
    protected Map<Integer, Pair<String, Set<String>>> targetSets;

    public Map<Integer, Pair<String, Set<String>>> getTargetSetValues(){
        return targetSets;
    }

    public String getTargetSetName(int id){
        try{
            return targetSets.get(id).getFirst();
        }
        catch(Exception ex){ return null;}
    }

    public Set<String> getTargetSetValues(int id){
        try{
            return targetSets.get(id).getSecond();
        }
        catch(Exception ex){ return new HashSet<String>();}
    }

    protected void refreshTargetSets(){
        try{
            Map<Integer, Pair<String, Set<String>>> targetSetsNew = new HashMap<Integer, Pair<String, Set<String>>>();
            String sql = "select ts.id, ts.name, tsv.value from target_sets ts join target_set_values tsv on ts.id = tsv.target_set_id order by ts.id, tsv.value";
            List<Map<String, Object>> rows = this.getResults(sql);
            for(Map<String, Object> row : rows){
                Integer id = (Integer)row.get("id");

                //bug 8071: convert value to lower case, for case-insensitive matching by placement param_targeting, and segment expressions
                String valueLowerCase = ((String)row.get("value")).trim().toLowerCase();

                if(!targetSetsNew.containsKey(id)){
                    Pair<String, Set<String>> pair = new Pair<String, Set<String>>((String)row.get("name"), new HashSet<String>());
                    pair.getSecond().add(valueLowerCase);
                    targetSetsNew.put(id, pair);
                }
                else{
                    targetSetsNew.get(id).getSecond().add(valueLowerCase);
                }
            }
            targetSets = targetSetsNew;
        }
        catch(Exception ex){
            log.error("refreshTargetSets", ex);
        }
    }

    protected void refresh(){
        refreshTargetSets();
    }

    protected String httpGet(String url) throws Exception{
        URLConnection connection = new URL(url).openConnection();
        connection.setRequestProperty("Accept-Charset", "UTF-8");
        connection.setDoOutput(true);
        connection.setReadTimeout(120000);

        InputStream response = connection.getInputStream();

        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(response, "UTF-8"));
            for (String line; (line = reader.readLine()) != null;) {
                sb.append(line).append("\n");
            }
        } finally {
            if (reader != null) try { reader.close(); } catch (IOException ioe) {}
        }

        return sb.toString();
    }

    protected String toJson(Object obj){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(obj);
        return json;
    }
}

