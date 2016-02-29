package cn.huangchaosuper.bean;

import cn.huangchaosuper.serializer.TzSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by I311579 on 10/23/2015.
 */
public class Tz {
    public static FastDateFormat fastDateFormat = DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT;
    private String id;
    private String type;
    private String service;
    private String iso8601_timestamp;
    private JsonObject _source;

    public Tz(String type){
        this(UUID.randomUUID().toString(),type,type,fastDateFormat.format(Calendar.getInstance()));
    }
    public Tz(String id,String type,String service,String iso8601_timestamp){
        this.id = id;
        this.type = type;
        this.service = service;
        this.iso8601_timestamp = iso8601_timestamp;
    }

    public JsonObject get_source() {
        return _source;
    }

    public void set_source(JsonObject _source) {
        this._source = _source;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getService() {
        return service;
    }

    public String getIso8601_timestamp() {
        return iso8601_timestamp;
    }

    @Override
    public String toString() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Tz.class, new TzSerializer());
        Gson gson = gsonBuilder.create();
        String response = gson.toJson(this);
        return response;
    }
}
