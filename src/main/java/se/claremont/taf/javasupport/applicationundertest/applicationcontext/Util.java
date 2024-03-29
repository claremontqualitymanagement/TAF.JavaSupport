package se.claremont.taf.javasupport.applicationundertest.applicationcontext;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.lang.management.ManagementPermission;
import java.util.List;


public class Util {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    private static ManagementPermission monitorPermission =
            new ManagementPermission("monitor");
    private static ManagementPermission controlPermission =
            new ManagementPermission("control");

    private Util() {
    }  // there are no instances of this class

    static RuntimeException newException(Exception e) {
        throw new RuntimeException(e);
    }

    static String[] toStringArray(List<String> list) {
        return list.toArray(EMPTY_STRING_ARRAY);
    }

    public static ObjectName newObjectName(String domainAndType, String name) {
        return newObjectName(domainAndType + ",name=" + name);
    }

    public static ObjectName newObjectName(String name) {
        try {
            return ObjectName.getInstance(name);
        } catch (MalformedObjectNameException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Check that the current context is trusted to perform monitoring
     * or management.
     * <p>
     * If the check fails we throw a SecurityException, otherwise
     * we return normally.
     *
     * @throws SecurityException if a security manager exists and if
     *                           the caller does not have ManagementPermission("control").
     */
    static void checkAccess(ManagementPermission p)
            throws SecurityException {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(p);
        }
    }

    static void checkMonitorAccess() throws SecurityException {
        checkAccess(monitorPermission);
    }

    static void checkControlAccess() throws SecurityException {
        checkAccess(controlPermission);
    }
}