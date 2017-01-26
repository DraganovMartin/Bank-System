package demo_simulator;

import networking.messages.request.LoginRequest;
import networking.messages.request.RegisterRequest;

/**
 *
 * @author iliyan
 */
class SystemProfile {

    String userName;
    String password;
    String firstName;
    String secondName;

    SystemProfile(
            String userName,
            String password,
            String firstName,
            String secondName) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    SystemProfile(RegisterRequest registerRequest) {
        this.userName = registerRequest.getRegisterUsername();
        this.password = registerRequest.getRegisterPassword();
        this.firstName = registerRequest.getFirstName();
        this.secondName = registerRequest.getLastName();
    }
}
