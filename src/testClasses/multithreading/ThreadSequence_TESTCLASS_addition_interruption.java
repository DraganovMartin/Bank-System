package testClasses.multithreading;

import multithreading.ThreadSequence;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class ThreadSequence_TESTCLASS_addition_interruption {

    public static void main(String[] args) {

        int MAX = 3;
        int TIME = 500;

        Thread initializing = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "INIT";
                System.out.print(" " + name + "_started...");
                {
                    boolean interrupted = false;
                    int i = MAX;
                    while (!interrupted && i > -1) {
                        interrupted = Thread.currentThread().isInterrupted();
                        if (!interrupted) {
                            try {
                                Thread.currentThread().sleep(TIME);
                                System.out.print(" " + name + "_" + i);
                                i--;
                            } catch (InterruptedException ex) {
                                interrupted = true;
                            }
                        }
                    }
                    if (interrupted) {
                        System.out.print(" " + name + "_interrupted!");
                    }
                }
                System.out.println(" " + name + "_finished.");
            }
        });

        Thread finalizing = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "EXIT";
                System.out.print(" " + name + "_started...");
                {
                    boolean interrupted = false;
                    int i = MAX;
                    while (!interrupted && i > -1) {
                        interrupted = Thread.currentThread().isInterrupted();
                        if (!interrupted) {
                            try {
                                Thread.currentThread().sleep(TIME);
                                System.out.print(" " + name + "_" + i);
                                i--;
                            } catch (InterruptedException ex) {
                                interrupted = true;
                            }
                        }
                    }
                    if (interrupted) {
                        System.out.print(" " + name + "_interrupted!");
                    }
                }
                System.out.println(" " + name + "_finished.");
            }
        });

        Thread s1 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "A";
                System.out.print(" " + name + "_started...");
                {
                    boolean interrupted = false;
                    int i = MAX;
                    while (!interrupted && i > -1) {
                        interrupted = Thread.currentThread().isInterrupted();
                        if (!interrupted) {
                            try {
                                Thread.currentThread().sleep(TIME);
                                System.out.print(" " + name + "_" + i);
                                i--;
                            } catch (InterruptedException ex) {
                                interrupted = true;
                            }
                        }
                    }
                    if (interrupted) {
                        System.out.print(" " + name + "_interrupted!");
                    }
                }
                System.out.println(" " + name + "_finished.");
            }
        });

        Thread s2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "B";
                System.out.print(" " + name + "_started...");
                {
                    boolean interrupted = false;
                    int i = MAX;
                    while (!interrupted && i > -1) {
                        interrupted = Thread.currentThread().isInterrupted();
                        if (!interrupted) {
                            try {
                                Thread.currentThread().sleep(TIME);
                                System.out.print(" " + name + "_" + i);
                                i--;
                            } catch (InterruptedException ex) {
                                interrupted = true;
                            }
                        }
                    }
                    if (interrupted) {
                        System.out.print(" " + name + "_interrupted!");
                    }
                }
                System.out.println(" " + name + "_finished.");
            }
        });

        Thread s3 = new Thread(new Runnable() {
            @Override
            public void run() {
                String name = "C";
                System.out.print(" " + name + "_started...");
                {
                    boolean interrupted = false;
                    int i = MAX;
                    while (!interrupted && i > -1) {
                        interrupted = Thread.currentThread().isInterrupted();
                        if (!interrupted) {
                            try {
                                Thread.currentThread().sleep(TIME);
                                System.out.print(" " + name + "_" + i);
                                i--;
                            } catch (InterruptedException ex) {
                                interrupted = true;
                            }
                        }
                    }
                    if (interrupted) {
                        System.out.print(" " + name + "_interrupted!");
                    }
                }
                System.out.println(" " + name + "_finished.");
            }
        });

        ThreadSequence ts = new ThreadSequence(initializing, finalizing);
        ts.add(s1);
        ts.start();
        ts.add(s2);
        try {
            Thread.currentThread().sleep(TIME * MAX * 3);
        } catch (InterruptedException ex) {
        }
        ts.interrupt();
        ts.add(s3);
        try {
            ts.join();
        } catch (InterruptedException ex) {
        }
    }
}
