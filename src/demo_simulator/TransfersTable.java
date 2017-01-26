package demo_simulator;

import java.util.TreeMap;

/**
 *
 * @author iliyan
 */
class TransfersTable {

    TreeMap<String, Transfer> data;

    TransfersTable() {
        this.data = new TreeMap<>();
    }

    boolean add(Transfer transfer) {
        if (this.data.get(transfer.id) != null) {
            return false;
        } else {
            this.data.put(transfer.id, transfer);
            return true;
        }
    }

    Transfer get(String id) {
        return this.data.get(id);
    }
}
