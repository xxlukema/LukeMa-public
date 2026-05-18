package com.learn.dp;


import lombok.extern.log4j.Log4j2;


@Log4j2
public class DpUtils {

    public static void print(final int[][] dp) {
        if (dp == null) {
            log.debug(() -> "dp is null.");
            return;
        } else if (dp.length == 0) {
            log.debug(() -> "dp size is zero.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("     ");
        for (int c = 0; c < dp[0].length; c++) {
            sb.append(String.format("%02d ", c));
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < dp.length; r++) {
            sb.append(String.format("%02d : ", r));
            for (int c = 0; c < dp[0].length; c++) {
                sb.append(" ").append(String.valueOf(dp[r][c])).append(" ");
            }
            if (r != dp.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        log.debug("dp: \n{}", () -> sb.toString());
    }

    public static void print(final char[][] dp) {
        if (dp == null) {
            log.debug(() -> "dp is null.");
            return;
        } else if (dp.length == 0) {
            log.debug(() -> "dp size is zero.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("     ");
        for (int c = 0; c < dp[0].length; c++) {
            sb.append(String.format("%02d ", c));
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < dp.length; r++) {
            sb.append(String.format("%02d : ", r));
            for (int c = 0; c < dp[0].length; c++) {
                sb.append(" ").append(dp[r][c]).append(" ");
            }
            if (r != dp.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        log.debug("dp: \n{}", () -> sb.toString());
    }

    public static void print(final boolean[][] dp) {
        if (dp == null) {
            log.debug(() -> "dp is null.");
            return;
        } else if (dp.length == 0) {
            log.debug(() -> "dp size is zero.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("     ");
        for (int c = 0; c < dp[0].length; c++) {
            sb.append(String.format("%02d ", c));
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < dp.length; r++) {
            sb.append(String.format("%02d : ", r));
            for (int c = 0; c < dp[0].length; c++) {
                sb.append(" ").append(dp[r][c] ? 'T' : 'F').append(" ");
            }
            if (r != dp.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        log.debug("dp: \n{}", () -> sb.toString());
    }

    public static void print(final Boolean[][] dp) {
        if (dp == null) {
            log.debug(() -> "dp is null.");
            return;
        } else if (dp.length == 0) {
            log.debug(() -> "dp size is zero.");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("     ");
        for (int c = 0; c < dp[0].length; c++) {
            sb.append(String.format("%02d ", c));
        }
        sb.append(System.lineSeparator());

        for (int r = 0; r < dp.length; r++) {
            sb.append(String.format("%02d : ", r));
            for (int c = 0; c < dp[0].length; c++) {
                if (dp[r][c] == null) {
                    sb.append(" ").append(' ').append(" ");
                } else {
                    sb.append(" ").append(dp[r][c] ? 'T' : 'F').append(" ");
                }
            }
            if (r != dp.length - 1) {
                sb.append(System.lineSeparator());
            }
        }

        log.debug("dp: \n{}", () -> sb.toString());
    }

}
