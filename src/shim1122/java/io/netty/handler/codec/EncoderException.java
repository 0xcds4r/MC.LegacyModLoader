package io.netty.handler.codec;

public class EncoderException extends RuntimeException {
    public EncoderException(String message) {
        super(message);
    }

    public EncoderException(Throwable cause) {
        super(cause);
    }
}
