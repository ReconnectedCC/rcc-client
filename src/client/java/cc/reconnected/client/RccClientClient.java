package cc.reconnected.client;

import cc.reconnected.client.supporter.SupporterBarHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.GameMenuScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RccClientClient implements ClientModInitializer {
    // too lazy
    public static SupporterData SUPPORTER_GOAL;
    private SupporterBarHud supporterBarHud;
    private static final HttpClient http = HttpClient.newHttpClient();
    public static Logger LOGGER = LoggerFactory.getLogger("rcc-client");


    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        RccClientConfig.HANDLER.load();
        ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
        timer.scheduleAtFixedRate(
            () -> {
            // Update SUPPORTER_GOAL with new data
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RccClientConfig.HANDLER.instance().backendURL))
                    .header("Accept","application/json")
                    .GET().build();
                try {
                    HttpResponse<String> response = http.send(request,HttpResponse.BodyHandlers.ofString());
                    if (response.statusCode() == 200 /* OK */) {
                        try {
                            SUPPORTER_GOAL = SupporterData.deserialize(response.body());
                        } catch (Exception e) {
                            LOGGER.error("Failed to serialize JSON to SupporterData:", e);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Failed to get Supporter Data: ",e);
                }

            },
            0,
            5,
            TimeUnit.MINUTES
        );

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            supporterBarHud = new SupporterBarHud(client);
        });

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof GameMenuScreen) {
                ScreenEvents.afterRender(screen)
                        .register((gameMenuScreen, drawContext, mouseX, mouseY, tickDelta) -> {
                            if (client.isConnectedToLocalServer()) {
                                //return;
                            }

                            supporterBarHud.render(drawContext);
                        });
            }

        });
    }
}