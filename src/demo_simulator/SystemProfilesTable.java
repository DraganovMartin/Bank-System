package demo_simulator;

import java.util.TreeMap;

/**
 *
 * @author iliyan
 */
class SystemProfilesTable {

    TreeMap<String, SystemProfile> data;

    SystemProfilesTable() {
        this.data = new TreeMap<>();
    }

    boolean add(SystemProfile systemProfile) {
        if (this.data.get(systemProfile.userName) != null) {
            return false;
        } else {
            this.data.put(systemProfile.userName, systemProfile);
            return true;
        }
    }

    SystemProfile get(String userName) {
        return this.data.get(userName);
    }
}
