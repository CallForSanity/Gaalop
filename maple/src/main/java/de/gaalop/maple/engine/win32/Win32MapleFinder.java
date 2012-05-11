package de.gaalop.maple.engine.win32;

import com.sun.jna.Function;
import com.sun.jna.Memory;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;

import java.io.FileNotFoundException;

public final class Win32MapleFinder {

    /**
     * @throws FileNotFoundException
     */
    public String getMaplePathFromRegistry() throws FileNotFoundException {
        return "C:\\Program Files (x86)\\Maple 13";
    }
}
//
//        int errno;
//        Function regOpenKeyEx = Function.getFunction("advapi32", "RegOpenKeyExW", Function.ALT_CONVENTION);
//        Function regQueryValueEx = Function.getFunction("advapi32", "RegQueryValueExW", Function.ALT_CONVENTION);
//        Function regCloseKey = Function.getFunction("advapi32", "RegCloseKey", Function.ALT_CONVENTION);
//
//        IntByReference keyHandlePtr = new IntByReference();
//        Object[] args;
//
//        // Open key
//        args = new Object[5];
//        args[0] = 0x80000002; // HKEY_LOCAL_MACHINE
//        args[1] = new WString("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Maple 9.5");
//        args[3] = 0x20019; // SAM
//        args[4] = keyHandlePtr;
//        errno = regOpenKeyEx.invokeInt(args);
//
//        if (errno != 0) {
//            throw new FileNotFoundException("Unable to find Maple installation: Registry key missing.");
//        }
//
//        int keyHandle = keyHandlePtr.getValue();
//
//        // Query Value
//        IntByReference bufferSizePtr = new IntByReference();
//        IntByReference dataTypePtr = new IntByReference();
//
//        args = new Object[6];
//        args[0] = keyHandle; // __in HKEY hkey
//        args[1] = new WString("InstallLocation"); // __in_opt LPCTSTR lpValue
//        args[3] = dataTypePtr; // __out_opt LPDWORD pdwType
//        args[4] = null; // __out_opt PVOID pvData
//        args[5] = bufferSizePtr; // __inout_opt LPDWORD pcbData
//        errno = regQueryValueEx.invokeInt(args);
//
//        if (errno != 0 || dataTypePtr.getValue() != 1) {
//            throw new FileNotFoundException("Unable to find Maple installation: Registry key missing.");
//        }
//
//        // Allocate buffer
//        Memory keyData = new Memory(bufferSizePtr.getValue());
//        args[4] = keyData; // Repeat call with memory parameter
//        errno = regQueryValueEx.invokeInt(args);
//
//        if (errno != 0) {
//            throw new FileNotFoundException("Unable to find Maple installation: Registry key missing.");
//        }
//
//        String mapleBasePath = keyData.getString(0, true);
//
//        regCloseKey.invoke(new Object[]{keyHandle});
//
//        return mapleBasePath;
//    }


