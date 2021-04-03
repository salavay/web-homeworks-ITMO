package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import java.util.Base64;

public class Test {
    public static void main(String[] args) {
        byte[] captcha = ImageUtils.toPng(Integer.toString(100 + (int) (Math.random() * 999)));
        String decoded = Base64.getEncoder().encodeToString(captcha);

    }
}
