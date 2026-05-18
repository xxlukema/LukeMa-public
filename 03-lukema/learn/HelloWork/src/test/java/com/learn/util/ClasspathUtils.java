package com.learn.util;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


public class ClasspathUtils {

    public static ClassLoader getClassLoader()
        throws IOException {
        ClassLoader classLoader = ClasspathUtils.class.getClassLoader();
        if (classLoader == null) {
            classLoader = ClassLoader.getSystemClassLoader();
            if (classLoader == null) {
                throw new IOException("ClassLoader is null");
            }
        }
        return classLoader;
    }

    public static URL getURL(String classpathFilename)
        throws IOException {
        return getClassLoader().getResource(classpathFilename);
    }

    public static String getPasthAsString(String classpathFilename)
        throws IOException {
        return getURL(classpathFilename).getPath();
    }

    public static URI getURI(String classpathFilename)
        throws URISyntaxException, IOException {
        return getURL(classpathFilename).toURI();
    }

    public static String readString(String classpathFilename)
        throws IOException, URISyntaxException {
        return Files.readString(Path.of(getURI(classpathFilename)));
    }

    public static byte[] readAllBytes(String classpathFilename)
        throws IOException, URISyntaxException {
        return Files.readAllBytes(Path.of(getURI(classpathFilename)));
    }

    public static List<String> readLaaLines(String classpathFilename)
        throws IOException, URISyntaxException {
        return Files.readAllLines(Path.of(getURI(classpathFilename)));
    }
}
