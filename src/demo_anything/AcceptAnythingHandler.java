package demo_anything;

import networking.messageHandlers.MessageHandler;
import networking.messageHandlers.SynchronizedMappedMessageHandler;
import networking.messages.Message;

/**
 *
 * @author iliyan
 */
public class AcceptAnythingHandler extends SynchronizedMappedMessageHandler implements MessageHandler {

    @Override
    public Message handle(Message message) {

        return RandomSuccessfulUpdateFactory.getRandomSuccessfulUpdate(message);
    }

}
