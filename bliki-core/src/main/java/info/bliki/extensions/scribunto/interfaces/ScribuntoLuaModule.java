package info.bliki.extensions.scribunto.interfaces;

import info.bliki.extensions.scribunto.ScribuntoException;
import info.bliki.extensions.scribunto.engine.ScribuntoEngine;
import info.bliki.extensions.scribunto.engine.ScribuntoModuleBase;
import info.bliki.extensions.scribunto.template.Frame;
import org.luaj.vm2.LuaValue;

public class ScribuntoLuaModule extends ScribuntoModuleBase {
    private LuaValue initChunk;

    public ScribuntoLuaModule(ScribuntoEngine engine, String code, String chunkName) {
        super(engine, code, chunkName);
    }

    @Override public String invoke(String functionName, Frame frame) throws ScribuntoException {
        LuaValue function = loadExportTable().get(functionName);

        if (function.isnil()) {
            throw new ScribuntoException("no such function '"+functionName+"'");
        }

        return getEngine().executeFunctionChunk(function, frame);
    }

    @Override public Status validate() {
        try {
            getInitChunk();
            return Status.OK;
        } catch (ScribuntoException e) {
            return Status.ERROR;
        }
    }

    @Override protected ScribuntoLuaEngine getEngine() {
        return (ScribuntoLuaEngine) super.getEngine();
    }

    private LuaValue loadExportTable() throws ScribuntoException {
        return getInitChunk().checkfunction().call();
    }

    private LuaValue getInitChunk() throws ScribuntoException {
        if (initChunk == null) {
            initChunk = getEngine().load(getCode());
        }
        return initChunk;
    }
}
