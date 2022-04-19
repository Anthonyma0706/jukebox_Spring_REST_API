package anthonyma.springbootjukerestapi;

public class RequirementNotMatchedException extends RuntimeException {
    RequirementNotMatchedException() {
        super("ERROR: Setting Requirement Not Matched for any Juke \n");
    }
}