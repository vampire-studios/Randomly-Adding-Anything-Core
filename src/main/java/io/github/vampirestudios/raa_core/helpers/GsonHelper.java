package io.github.vampirestudios.raa_core.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.minecraft.util.Identifier;

public class GsonHelper {

	public static final Gson GSON = new GsonBuilder()
		.registerTypeAdapter(Identifier.class, new TypeAdapter<Identifier>() {
			@Override
			public void write(JsonWriter out, Identifier value) throws IOException {
				if (value == null)
					out.nullValue();
				else
					out.value(value.toString());
			}

			@Override
			public Identifier read(JsonReader in) throws IOException {
				JsonToken jsonToken = in.peek();
				if (jsonToken == JsonToken.NULL) {
					in.nextNull();
					return null;
				} else {
					return new Identifier(in.nextString());
				}
			}
		})
		.serializeNulls()
		.setPrettyPrinting()
		.create();

	public static Identifier idFromOldStyle(JsonObject jsonObject) {
		if (jsonObject.has("namespace"))
			return new Identifier(net.minecraft.util.JsonHelper.getString(jsonObject, "namespace"), net.minecraft.util.JsonHelper.getString(jsonObject, "path"));
		return new Identifier(net.minecraft.util.JsonHelper.getString(jsonObject, "field_13353"), net.minecraft.util.JsonHelper.getString(jsonObject, "field_13355"));
	}

}
