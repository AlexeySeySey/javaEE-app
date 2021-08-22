package service;

import contract.Closure;

public final class ThreadService {

	public void go(Closure closure) {
		new Thread(() -> {
			try {
				closure.run();
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		}).start();
	}
}
