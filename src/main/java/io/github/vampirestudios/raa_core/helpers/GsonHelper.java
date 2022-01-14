package io.github.vampirestudios.raa_core.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.minecraft.resources.ResourceLocation;

public class GsonHelper {
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .registerTypeAdapter(ResourceLocation.class, new TypeAdapter<ResourceLocation>() {
                    @Override
                    public void write(JsonWriter out, ResourceLocation value) throws IOException {
                        if (value == null)
                            out.nullValue();
                        else
                            out.value(value.toString());
                    }

                    @Override
                    public ResourceLocation read(JsonReader in) throws IOException {
                        JsonToken jsonToken = in.peek();
                        if (jsonToken == JsonToken.NULL) {
                            in.nextNull();
                            return null;
                        } else {
                            return new ResourceLocation(in.nextString());
                        }
                    }
                })
                .serializeNulls()
                .setPrettyPrinting()
                .create();
    }

    public static Gson getGson() {
        return GSON;
    }

    public static ResourceLocation idFromOldStyle(JsonObject jsonObject) {
        if (jsonObject.has("namespace"))
            return new ResourceLocation(net.minecraft.util.GsonHelper.getAsString(jsonObject, "namespace"), net.minecraft.util.GsonHelper.getAsString(jsonObject, "path"));
        return new ResourceLocation(net.minecraft.util.GsonHelper.getAsString(jsonObject, "field_13353"), net.minecraft.util.GsonHelper.getAsString(jsonObject, "field_13355"));
    }
}
