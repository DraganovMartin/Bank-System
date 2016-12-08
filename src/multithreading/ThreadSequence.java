package multithreading;

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * An extension of {@link Thread} class that provides a sequential (synchronous)
 * execution of a series of threads. Each subsequent thread is executed only
 * after the previous has finished. The {@link ThreadSequence} has one
 * initializing thread that is executed first and cannot be interrupted, useful
 * for initial setup operations (can be NULL). The {@link ThreadSequence} has
 * one finalizing thread that is executed last and cannot be interrupted, useful
 * for final cleanup operations (can be NULL). The initializing and finalizing
 * threads are specified at the constructor. Other threads are added to the
 * sequence by calling the {@link #add(java.lang.Thread)} method and are
 * executed in a first in, first out (FIFO) order. New sequenced threads cannot
 * be added once all of the sequenced threads have been executed. Interruption
 * of the {@link ThreadSequence} execution results in interrupting the currently
 * running sequenced thread and executing the finalizing thread, but <u>WILL NOT
 * INTERRUPT ANY OF THE INITIALIZING OR FINALIZING THREADS</u>.
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ThreadSequence extends Thread {

    /**
     * the initializing thread. It is executed first and cannot be interrupted.
     * Useful for initial setup operations. Can be NULL when not required.
     * Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     */
    private final Thread initializer;
    /**
     * the finalizing thread. It is executed last and cannot be interrupted.
     * Useful for final cleanup operations. Can be NULL when not required.
     * Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     */
    private final Thread finalizer;

    /**
     * the list of sequenced threads. Interruption of the {@link ThreadSequence}
     * execution results in interrupting the currently running sequenced thread
     * and executing the finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE
     * INITIALIZING OR FINALIZING THREADS</u>.
     */
    private final LinkedList<Thread> sequence;

    /**
     * whether a thread can be added to the sequence. New sequenced threads
     * cannot be added once all of the sequenced threads have been executed.
     * Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     */
    private boolean canAddSequenced;

    /**
     * Adds a thread sequenced for execution to the last position in the list of
     * sequenced threads. New sequenced threads cannot be added once all of the
     * sequenced threads have been executed. Interruption of the
     * {@link ThreadSequence} execution results in interrupting the currently
     * running sequenced thread and executing the finalizing thread, but <u>WILL
     * NOT INTERRUPT ANY OF THE INITIALIZING OR FINALIZING THREADS</u>.
     *
     * @param sequencedThread the thread to be added.
     *
     * @return true if successful, false if failed.
     */
    public boolean add(Thread sequencedThread) {
        synchronized (this.sequence) {
            if (this.canAddSequenced) {
                return this.sequence.add(sequencedThread);
            } else {
                return false;
            }
        }
    }

    /**
     * Constructs a {@link ThreadSequence} according to the arguments provided.
     * Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     *
     * @param initializer the initializing thread. It is executed first and
     * cannot be interrupted. Useful for initial setup operations. Can be NULL
     * when not required. Interruption of the {@link ThreadSequence} execution
     * results in interrupting the currently running sequenced thread and
     * executing the finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE
     * INITIALIZING OR FINALIZING THREADS</u>.
     *
     * @param finalizer the finalizing thread. It is executed last and cannot be
     * interrupted. Useful for final cleanup operations. Can be NULL when not
     * required. Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     */
    public ThreadSequence(Thread initializer, Thread finalizer) {
        this.initializer = initializer;
        this.finalizer = finalizer;
        this.sequence = new LinkedList<>();
        this.canAddSequenced = true;
    }

    /**
     * Interruption of the {@link ThreadSequence} execution results in
     * interrupting the currently running sequenced thread and executing the
     * finalizing thread, but <u>WILL NOT INTERRUPT ANY OF THE INITIALIZING OR
     * FINALIZING THREADS</u>.
     */
    @Override
    public void run() {
        // executing the initializing thread:
        {
            if (this.initializer != null) {
                this.initializer.start();
                while (this.initializer.isAlive()) {
                    try {
                        this.initializer.join();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
        // executing the sequenced threads:
        {
            boolean keepRunning = true;
            while (keepRunning && !(this.isInterrupted())) {
                Thread sequenced;
                synchronized (this.sequence) {
                    try {
                        sequenced = this.sequence.poll();
                    } catch (NoSuchElementException ex) {
                        sequenced = null;
                        keepRunning = false;
                    }
                }
                if (keepRunning && (sequenced != null)) {
                    sequenced.start();
                    try {
                        sequenced.join();
                    } catch (InterruptedException ex) {
                        sequenced.interrupt();
                        keepRunning = false;
                    }
                }
                /**
                 * disabling the addition of new sequenced threads once all of
                 * the sequenced threads have been executed:
                 */
                synchronized (this.sequence) {
                    if (this.sequence.isEmpty()) {
                        this.canAddSequenced = false;
                        keepRunning = false;
                    }
                }
            }
        }
        // executing the finalizing thread:
        {
            if (this.finalizer != null) {
                this.finalizer.start();
                while (this.finalizer.isAlive()) {
                    try {
                        this.finalizer.join();
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }
    }
}
