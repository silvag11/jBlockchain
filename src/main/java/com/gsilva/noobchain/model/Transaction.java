package com.gsilva.noobchain.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import com.gsilva.noobchain.util.StringUtil;

public class Transaction {
	public String transactionID;
	public PublicKey sender;
	public PublicKey reciepient;
	public float value;
	public byte[] signature;

	public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
	public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

	private static int sequence = 0;

	public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
		this.sender = to;
		this.reciepient = from;
		this.value = value;
		this.inputs = inputs;
	}

	private String calculateHash() {
		sequence++;
		return StringUtil.apllySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence);

	}

	public void generateSignature(PrivateKey key) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = StringUtil.applyECDSASig(key, data);
	}
	
	public boolean verifySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
		+ Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

}
