package com.gsilva.noobchain.model;

import java.security.PublicKey;

import com.gsilva.noobchain.util.StringUtil;

public class TransactionOutput {
	public String id;
	public PublicKey reciepient;
	public float value;
	public String parentTransactionId;

	public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
		this.reciepient = reciepient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = StringUtil
				.applySha256(StringUtil.getStringFromKey(reciepient) + Float.toString(value) + parentTransactionId);
	}

	public boolean isMine(PublicKey publicKey) {
		return (publicKey == reciepient);
	}
}
