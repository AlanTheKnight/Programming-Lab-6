package alantheknight.lab6.common.utils;

public record Config(String dataPath, Integer port, String address, Integer bufferSize, ClientConfig clientConfig) {
    public record ClientConfig(Integer maxRecursionLevel, Integer responseTimeout) {
    }
}