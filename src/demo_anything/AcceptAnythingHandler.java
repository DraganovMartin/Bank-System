package demo_anything;

import networking.messageHandlers.MessageHandler;
import networking.messages.Message;

/**
 *
 * @author iliyan
 */
public class AcceptAnythingHandler implements MessageHandler {

    @Override
    public Message handle(Message message) {

        return RandomSuccessfulUpdateFactory.getRandomSuccessfulUpdate(message);
    }

}
