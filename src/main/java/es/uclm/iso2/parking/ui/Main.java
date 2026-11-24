package es.uclm.iso2.parking.ui;

/**
 * Entry point for the Parking fee calculator console application (ET.02.02).
 */
public class Main {

    /**
     * Utility entry-point class; not meant to be instantiated.
     */
    private Main() {
    }

    /**
     * Launches the interactive console interface using standard input and
     * standard output.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        new ParkingConsole(System.in, System.out).run();
    }
}
