/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testClasses.networking.client_server_gui_test_NOTWORKING;

import networking.MessageHandler;
import networking.messages.ClientAuthenticationRequest;
import networking.messages.ClientAuthenticationResponse;
import networking.messages.Message;

/**
 *
 * @author iliyan-kostov <iliyan.kostov.gml@gmail.com>
 */
public class FakeServersideHandler implements MessageHandler {

    @Override
    public Message handle(Message message) {
        if (message.getType().compareTo(ClientAuthenticationRequest.CLIENTAUTHENTICATIONREQUEST) == 0) {
            return new ClientAuthenticationResponse(true, ((ClientAuthenticationRequest) message).getUsername());
        } else {
            return new FakeTextMessage(FakeTextMessage.FAKETEXTMESSAGE, "FAKE SERVERSIDE TEXT MESSAGE");
        }
    }
}
