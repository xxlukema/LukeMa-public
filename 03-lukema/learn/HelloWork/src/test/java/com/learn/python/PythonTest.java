package com.learn.python;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.Collectors;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.junit.jupiter.api.Test;
import org.python.util.PythonInterpreter;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class PythonTest {

    @Test
    public void testCallPython()
        throws IOException {

        log.info("Begin Test.", () -> "");

        ProcessBuilder processBuilder = new ProcessBuilder("py", "python/hello.py");
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        log.debug(() -> results);

        log.info(() -> "End Test.");

    }

    private List<String> readProcessOutput(InputStream inputStream) {
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.toList());
    }

    @Test
    public void testCallJython1()
        throws IOException, ScriptException {

        log.info("Begin Test.", () -> "");

        StringWriter writer = new StringWriter();
        ScriptContext context = new SimpleScriptContext();
        context.setWriter(writer);

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("python");

        engine.eval(new FileReader("python/hello.py"), context);

        String lines = writer.toString().trim();

        log.debug(() -> lines);

        log.info(() -> "End Test.");

    }

    @Test
    public void testCallJython2() {

        log.info("Begin Test.", () -> "");

        try (PythonInterpreter pyInterp = new PythonInterpreter()) {
            StringWriter output = new StringWriter();
            pyInterp.setOut(output);

            // pyInterp.exec("print('Hello Baeldung Readers!!')");
            pyInterp.execfile("python/hello.py");
            String lines = output.toString().trim();

            log.debug(() -> lines);
        }

        log.info(() -> "End Test.");

    }
}
