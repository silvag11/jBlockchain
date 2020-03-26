package com.gsilva.noobchain.model;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

import com.gsilva.noobchain.Noobchain;
import com.gsilva.noobchain.util.StringUtil;

public class Transaction {
	public String transactionId;
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
		return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value) + sequence);

	}

	public void generateSignature(PrivateKey key) {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
				+ Float.toString(value);
		signature = StringUtil.applyECDSASig(key, data);
	}
	
	public boolean verifiySignature() {
		String data = StringUtil.getStringFromKey(sender) + StringUtil.getStringFromKey(reciepient)
		+ Float.toString(value);
		return StringUtil.verifyECDSASig(sender, data, signature);
	}

	public boolean processTransation() {
		if (verifiySignature() == false) {
			System.out.println("#Transaction Signature failed to verify");
			return false;
		}
		for (TransactionInput i : inputs) {
			i.UTXO = Noobchain.UTXOs.get(i.transactionOutputId);

		}
		if (getInputsValue() < Noobchain.minimumTransaction) {
			System.out.println("#Transaction Inputs to small: " + getInputsValue());
			return false;
		}
		float leftOver = getInputsValue() - value;
		transactionId = calculateHash();
		outputs.add(new TransactionOutput(this.reciepient, value, transactionId));
		outputs.add(new TransactionOutput(this.sender, leftOver, transactionId));
		for (TransactionOutput o : outputs) {
			Noobchain.UTXOs.put(o.id, o);
		}
		for (TransactionInput i : inputs) {
			if(i.UTXO == null)
				continue;
			Noobchain.UTXOs.remove(i.UTXO.id);
		}
		return true;
	}

	public float getInputsValue() {
		float total = 0;
		for (TransactionInput i : inputs) {
			if (i.UTXO == null) 
				continue;
			Noobchain.UTXOs.remove(i.UTXO.id);
		}
		return total;
	}

	public float getOutputsValue() {
		float total = 0;
		for (TransactionOutput o : outputs) {
			total += o.value;
		}
		return total;
	}
}
