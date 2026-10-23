package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class ModelBakeEvent extends Event {
    private final Object modelRegistry;
    private final Object modelManager;

    public ModelBakeEvent(Object modelRegistry, Object modelManager) {
        this.modelRegistry = modelRegistry;
        this.modelManager = modelManager;
    }

    public Object getModelRegistry() {
        return modelRegistry;
    }

    public Object getModelManager() {
        return modelManager;
    }
}
