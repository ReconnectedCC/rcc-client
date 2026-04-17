//package cc.reconnected.client;
//
//import com.google.gson.GsonBuilder;
//import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
//import dev.isxander.yacl3.config.v2.api.SerialEntry;
//import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
//import net.fabricmc.loader.api.FabricLoader;
//import net.minecraft.util.Identifier;
//

/*

This will be uncommented when more customizability is added.

 */

//public class RccClientConfig {
//    public static ConfigClassHandler<RccClientConfig> HANDLER = ConfigClassHandler.createBuilder(RccClientConfig.class)
//            .id(new Identifier("rcc-client","config"))
//            .serializer(config -> GsonConfigSerializerBuilder.create(config)
//                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("rcc-client.json5"))
//                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
//                    .setJson5(true)
//                    .build()
//            )
//            .build();
//    @SerialEntry(comment = "This controls the URL that RCC-Client pulls supporter bar data from, This is provided for development purposes.")
//    public String backendURL = "https://api.reconnected.cc/stripe/data/thismonth"; // change this default to the rcc api
//}
