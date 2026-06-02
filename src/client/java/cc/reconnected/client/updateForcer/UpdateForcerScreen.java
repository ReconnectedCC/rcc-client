package cc.reconnected.client.updateForcer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
public class UpdateForcerScreen extends Screen {
    private static final Text RESTART_TEXT = Text.translatable("gui.rcc_client.update_required").formatted(Formatting.GREEN);
    private static final Text RESTART_BUTTON = Text.translatable("gui.rcc_client.update_button").formatted(Formatting.GREEN);

    public final Text message;
    private final GridWidget grid;


    public UpdateForcerScreen(Text message) {
        super(RESTART_TEXT);
        this.message = message;
        this.grid = new GridWidget();
    }

    public void init() {
        this.grid.getMainPositioner().alignHorizontalCenter().margin(10);
        GridWidget.Adder adder = this.grid.createAdder(1);
        adder.add(new TextWidget(this.title, this.textRenderer));
        adder.add(new MultilineTextWidget(this.message, this.textRenderer)).setMaxWidth(this.width - 50).setCentered(true);
        adder.add(
                ButtonWidget.builder(RESTART_BUTTON, (ignored) -> {
                    assert this.client != null;
                    this.client.stop();
                }).build()
        );
        this.grid.refreshPositions();
        this.grid.forEachChild(this::addDrawableChild);
        this.initTabNavigation();
    }

    protected void initTabNavigation() {
        SimplePositioningWidget.setPos(this.grid,this.getNavigationFocus());
    }

    public boolean shouldCloseOnEsc() {return false;} // You must restart to update

    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx,mouseX,mouseY,delta);
        super.render(ctx, mouseX, mouseY, delta);
    }
}
