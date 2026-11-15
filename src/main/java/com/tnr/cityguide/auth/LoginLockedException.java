package com.tnr.cityguide.auth;
public class LoginLockedException extends RuntimeException {private final int seconds;public LoginLockedException(int seconds){super("Too many attempts. Try again in "+seconds+"s.");this.seconds=seconds;}public int getSeconds(){return seconds;}}
