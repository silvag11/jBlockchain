package com.gsilva.noobchain.model;

import java.util.Date;

import com.gsilva.noobchain.util.StringUtil;

public class Block {
	public String hash;
	public String previousHash;
	private String data;
	private long timeStamp;
	private int nonce;

	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash();
	}

	public String calculateHash() {
		String caculatedHash = StringUtil.apllySha256(previousHash +
				Long.toString(timeStamp) +
				data);
		return calculateHash();
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!hash.substring(0, difficulty).equals(target)) {
			nonce++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! :" + hash);
	}
}
