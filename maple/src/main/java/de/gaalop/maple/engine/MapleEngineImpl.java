package de.gaalop.maple.engine;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class wraps the maple engine so it can be used without linking errors.
 *
 * @author Sebastian
 */
class MapleEngineImpl implements MapleEngine {

    private Log log = LogFactory.getLog(MapleEngineImpl.class);

    /**
     * This class is an implementation of the OpenMaple EngineCallBack interface
     * based on the Java Reflection {@link Proxy} class. It forwards the text
     * and error callbacks to the outer class.
     *
     * @author Sebastian
     */
    private final class EngineCallback implements InvocationHandler {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws Throwable {
            String methodName = method.getName();

            if (methodName.equals("queryInterrupt")
                    || methodName.equals("redirectCallBack")) {
                return false;
            } else if (methodName.equals("errorCallBack")) {
                onError(args[2].toString(), (Integer) args[1]);
            } else if (methodName.equals("textCallBack")) {
                onText(args[2].toString(), (Integer) args[1]);
            }

            return null;
        }
    }

    private static final String MAPLE_CALLBACK_INTERFACE = "com.maplesoft.openmaple.EngineCallBacks";

    private static final String MAPLE_ENGINE_CLASS = "com.maplesoft.openmaple.Engine";

    private Class<?> mapleCallbackInterface;
    private Constructor<?> engineConstructor;
    private Method evaluateMethod;
    private List<String> errorMessages = new ArrayList<String>();

    private Object engine;

    /**
     * This field is used to buffer the output from Maple that is sent via the
     * {@link #onText(String, int)} callback.
     */
    private StringBuffer outputBuffer = new StringBuffer();

    /**
     * This callback is called when Maple indicates an error.
     *
     * @param text The text describing the error.
     * @param type
     */
    private void onError(String text, int type) {
        errorMessages.add(text);
    }

    /**
     * This callback is called when the Maple engine outputs text.
     *
     * @param text The text that was sent by Maple.
     * @param type
     */
    private void onText(String text, int type) {
        outputBuffer.append(text);
        outputBuffer.append('\n');
    }

    /**
     * Constructs a new Maple Engine. Please note that OpenMaple restrictions
     * mandate that there may only be ONE engine at a time. That is why this
     * class is managed as a singleton by {@link Maple}.
     *
     * @param mapleClassLoader This class loader will be queried for the important classes.
     * @throws IllegalArgumentException If the engine could not be initialized by using the given
     *                                  class loader.
     */
    MapleEngineImpl(MapleClassLoader mapleClassLoader)
            throws IllegalArgumentException {
        try {
            // Check that important classes are available
            Class<?> mapleEngineClass = mapleClassLoader
                    .loadClass(MAPLE_ENGINE_CLASS);
            mapleCallbackInterface = mapleClassLoader
                    .loadClass(MAPLE_CALLBACK_INTERFACE);

            // Find Engine constructor in Engine class
            engineConstructor = mapleEngineClass.getConstructor(String[].class,
                    mapleCallbackInterface, Object.class, Object.class);

            // Find evaluate method in Engine class
            evaluateMethod = mapleEngineClass.getDeclaredMethod("evaluate",
                    String.class);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Given class loader is unable to load a valid Maple engine.",
                    e);
        }

        Object callback = createEngineCallback();

        engine = createEngine(callback);
    }

    /**
     * Creates an object implementing the OpenMaple EngineCallBack interface
     * using Java Proxies using the inner class {@link EngineCallback}.
     *
     * @return A new object that implements the Maple EngineCallBack interface
     *         and forwards all calls to an {@link EngineCallback} instance.
     */
    private Object createEngineCallback() {
        Object callbackProxy = Proxy.newProxyInstance(mapleCallbackInterface
                .getClassLoader(), new Class<?>[]{mapleCallbackInterface},
                new EngineCallback());

        return callbackProxy;
    }

    /**
     * Creates an OpenMaple engine object and connects it to the given callback.
     *
     * @param callback
     */
    private Object createEngine(Object callback) {

        try {
            return engineConstructor.newInstance(new String[]{"java"},
                    callback, null, null);

        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate Maple engine.", e);
        }
    }

    @Override
    public String evaluate(String command) throws MapleEngineException {
        log.debug("Executing '" + command + "'");

        errorMessages.clear();
        outputBuffer.setLength(0);
        try {
            Object mapleResult = evaluateMethod.invoke(engine, command);
            if (mapleResult != null) {
                disposeMapleObject(mapleResult);
            }

            String result = outputBuffer.toString();
            outputBuffer.setLength(0);

            if (!errorMessages.isEmpty()) {
                String errorMessage = "";
                for (String message : errorMessages) {
                    errorMessage += message;
                }
                throw new MapleEngineException(errorMessage, command);
            }

            log.debug("Result: '" + result + "'");

            return result;
        } catch (Exception e) {
            throw new MapleEngineException("Unable to execute maple commands.", command, e);
        }
    }

    @Override
    public void reset() throws MapleEngineException {
        evaluate("restart:");
    }

    @Override
    public void loadModule(InputStream input) throws MapleEngineException {
        try {
            File tempFile = File.createTempFile("gaalop", ".m");

            try {
                FileOutputStream output = new FileOutputStream(tempFile);

                byte[] buffer = new byte[1024];
                int read;
                while ((read = input.read(buffer)) != -1) {
                    output.write(buffer, 0, read);
                }

                output.close();

                evaluate("read \"" + tempFile.getAbsolutePath() + "\";");
            } finally {
                tempFile.delete();
            }
        } catch (IOException e) {
            throw new MapleEngineException(
                    "Unable to load module in Maple.", e);
        }
    }

    /**
     * Calls the dispose method (if available) on the given Maple object. This
     * method fails silently if there is no such method.
     *
     * @param obj An object with a dispose method. Must not be null.
     */
    private void disposeMapleObject(Object obj) {
        try {
            Method dispose = obj.getClass().getMethod("dispose");
            dispose.invoke(obj);
        } catch (SecurityException e) {
            log.warn("Unable to get dispose method of object.", e);
        } catch (NoSuchMethodException e) {
            log.warn("Unable to get dispose method of object because it doesnt exist.", e);
        } catch (IllegalArgumentException e) {
            log.warn("Unable to call dispose method because of an arguments mismatch.", e);
        } catch (IllegalAccessException e) {
            log.warn("Unable to call dispose method because it is not accessible.", e);
        } catch (InvocationTargetException e) {
            log.warn("Unable to call dispose method.", e);
        }

    }
}
