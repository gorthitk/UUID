package org.jet.uuid.util;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * Fetches the mac address of the current system.
 */
public final class MacAddressFetcher {
    public static String get() throws Exception {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

        while (interfaces.hasMoreElements()) {
            NetworkInterface networkInterface = interfaces.nextElement();
            // Filter interfaces.
            if (isDown(networkInterface) || isLoopBack(networkInterface) || isVirtualNetwork(networkInterface)) {
                continue;
            }

            // Returns the first Hardware Address found.
            return bytesToHex(networkInterface.getHardwareAddress());
        }

        throw new IllegalStateException("Could not get Mac Address");
    }

    /**
     * Coverts integer bytes to Hexadecimal String
     *
     * @param bytes mac address
     * @return String
     */
    private static String bytesToHex(byte[] bytes) {
        String[] hexa = new String[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            hexa[i] = String.format("%02X", bytes[i]);
        }
        return String.join("", hexa);
    }

    /**
     * Returns True is the Network Interface is down.
     *
     * @param networkInterface NetworkInterface
     * @return boolean
     * @throws SocketException for I/O errors.
     */
    private static boolean isDown(NetworkInterface networkInterface) throws SocketException {
        return !networkInterface.isUp();
    }

    /**
     * LoopBack interfaces are primarily used for internal communication within a system.
     *
     * @param networkInterface NetworkInterface
     * @return boolean
     * @throws SocketException for I/O errors.
     */
    private static boolean isLoopBack(NetworkInterface networkInterface) throws SocketException {
        return networkInterface.isLoopback();
    }

    /**
     * Returns True if the interface is virtual.
     *
     * @param networkInterface NetworkInterface
     * @return boolean
     * @throws SocketException for I/O errors.
     */
    private static boolean isVirtualNetwork(NetworkInterface networkInterface) throws SocketException {
        return networkInterface.isVirtual() || networkInterface.getHardwareAddress() == null;
    }
}
