package cc.reconnected.client.supporter;

import cc.reconnected.client.SupporterData;
import cc.reconnected.client.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static cc.reconnected.client.RccClientClient.SUPPORTER_GOAL;

public class SupporterBarHud {
    private static final Identifier BARS_TEXTURE = new Identifier("textures/gui/bars.png");
    private static final int WIDTH = 182;
    private static final int HEIGHT = 5;
    private static final int NOTCHED_BAR_OVERLAY_V = 80;
    private final MinecraftClient client;


    private final BossBar.Color color = BossBar.Color.GREEN;

    private final BossBar.Style style = BossBar.Style.NOTCHED_6;

    public SupporterBarHud(MinecraftClient client) {
        this.client = client;
    }



    public Text getFormattedValue(int value) {
        return Utils.renderCurrency(value);
    }

    public Text getTitle() {
        return Text.empty()
                .append(Text.literal("Monthly "))
                .append(Text.literal("Supporter")
                        .setStyle(Style.EMPTY.withColor(0x1abc9c))) // Supporter color on discord
                .append(Text.literal(" goal: "))

                .append(
                        Text.empty()
                                .append(getFormattedValue(SUPPORTER_GOAL.amount))
                                .append("/")
                                .append(getFormattedValue(SUPPORTER_GOAL.goal))
                                .append(Text.literal("€"))
                                .setStyle(Style.EMPTY.withColor(getTextColor(SUPPORTER_GOAL)))
                );
    }

    public Formatting getTextColor(SupporterData data) {
        if (data.amount <= 0) return Formatting.GRAY;
        if (data.amount >= data.goal) return Formatting.GREEN;

        return Formatting.YELLOW;
    }

    public float getPercentage(@NotNull SupporterData data) {
        if (data.goal == 0) return 0;

        return Math.min(1.0f, Math.max(0.0f, (float) data.amount / data.goal));
    }

    public void render(DrawContext context) {
        if (SUPPORTER_GOAL != null) {
            int windowWidth = context.getScaledWindowWidth();
            int barY = 12;
            int center = windowWidth / 2 - (WIDTH / 2);
            this.renderBossBar(context, center, barY);

            Text text = getTitle();
            int textWidth = this.client.textRenderer.getWidth(text);
            int textCenter = windowWidth / 2 - textWidth / 2;
            int textY = barY - 9;
            Objects.requireNonNull(this.client.textRenderer);
            context.drawTextWithShadow(this.client.textRenderer, text, textCenter, textY, 16777215);
        }
    }

    private void renderBossBar(DrawContext context, int x, int y) {
        this.renderBossBar(context, x, y, WIDTH, 0);
        int i = (int) (getPercentage(SUPPORTER_GOAL) * WIDTH + 1);
        if (i > 0) {
            this.renderBossBar(context, x, y, i, HEIGHT);
        }

    }

    private void renderBossBar(DrawContext context, int x, int y, int width, int height) {
        context.drawTexture(BARS_TEXTURE, x, y, 0, color.ordinal() * HEIGHT * 2 + height, width, 5);
        RenderSystem.enableBlend();
        context.drawTexture(BARS_TEXTURE, x, y, 0, NOTCHED_BAR_OVERLAY_V + (style.ordinal() - 1) * HEIGHT * 2 + height, width, HEIGHT);
        RenderSystem.disableBlend();

    }
}
