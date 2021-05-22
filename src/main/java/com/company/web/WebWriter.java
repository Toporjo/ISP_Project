package com.company.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebWriter {

    public void write(String text, OutputStream output) throws IOException {
        try(OutputStream out = output) {
            byte[] buffer = text.getBytes(StandardCharsets.UTF_8);
            out.write(buffer,0,buffer.length);
        }
    }
}
