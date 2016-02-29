package cn.huangchaosuper.serializer;

import cn.huangchaosuper.bean.Tz;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by I311579 on 10/23/2015.
 */
public class TzSerializer  implements JsonSerializer<Tz> {
    @Override
    public JsonElement serialize(Tz src, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject tz = src.get_source();
        if(tz == null){
            tz = new JsonObject();
        }
        tz.addProperty("_id",src.getId());
        tz.addProperty("_type",src.getType());
        tz.addProperty("_service",src.getService());
        tz.addProperty("@timestamp",src.getIso8601_timestamp());
        return tz;
    }
}
