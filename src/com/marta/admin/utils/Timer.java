package com.marta.admin.utils;

public class Timer {
	long t;

	public Timer() {
		reset();
	}

	public void reset() {
		t = System.currentTimeMillis();
	}
	
	public long elapsed() {
		return System.currentTimeMillis() - t;
	}

	public void print(String s) {
		System.out.println(s + ": " + elapsed());
	}
}
