package alantheknight.lab6.common.utils;

/**
 * Enum for console colors.
 *
 * @see <a href="https://en.wikipedia.org/wiki/ANSI_escape_code">ANSI escape codes</a>
 */
public enum ConsoleColors {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    WHITE("\u001B[37m");

    /**
     * The ANSI code for the color.
     */
    private final String ansiCode;

    /**
     * Constructor.
     *
     * @param ansiCode the ANSI code for the color
     */
    ConsoleColors(String ansiCode) {
        this.ansiCode = ansiCode;
    }

    /**
     * Colorize the text.
     *
     * @param text  the text
     * @param color the color
     * @return the colorized text
     */
    public static String colorize(String text, ConsoleColors color) {
        return color + text + RESET;
    }

    @Override
    public String toString() {
        return ansiCode;
    }
}
