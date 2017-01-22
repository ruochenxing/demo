package com.juc;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueDemo {
	// 开始搜索
	public static void startIndexing(File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		FileFilter filter = new FileFilter() {
			public boolean accept(File file) {
				return true;
			}
		};
		for (File file : roots) {
			new Thread(new FileCrawler(queue, filter, file)).start();
		}
		for (int i = 0; i < 10; i++) {
			new Thread(new Indexer(queue)).start();
		}
	}
}

class Indexer implements Runnable {

	private final BlockingQueue<File> fileQueue;

	public Indexer(BlockingQueue<File> fileQueue) {
		super();
		this.fileQueue = fileQueue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				indexFile(fileQueue.take());
			}
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void indexFile(File file) {
		if (file == null) {
			return;
		} else {
			System.out.println(file.getAbsolutePath());
		}
	}

}

class FileCrawler implements Runnable {

	private final BlockingQueue<File> fileQueue;
	private final FileFilter filter;
	private final File root;

	public FileCrawler(BlockingQueue<File> fileQueue, FileFilter filter, File root) {
		this.fileQueue = fileQueue;
		this.filter = filter;
		this.root = root;
	}

	@Override
	public void run() {
		try {
			crawl(root);
		} catch (Exception e) {
			Thread.currentThread().interrupt();
		}
	}

	private void crawl(File root) throws InterruptedException {
		File[] files = root.listFiles(filter);
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					crawl(file);
				} else if (!alreadyIndexed(file)) {
					fileQueue.put(file);
				}
			}
		}
	}

	private boolean alreadyIndexed(File file) {
		return false;
	}

}
