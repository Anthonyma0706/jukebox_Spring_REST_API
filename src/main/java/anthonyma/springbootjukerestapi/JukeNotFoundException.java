package anthonyma.springbootjukerestapi;

class JukeNotFoundException extends RuntimeException {
    JukeNotFoundException(String key) {
        super("ERROR: Could not find ID " + key + "\n");
    }
}
