package com.gsilva.noobchain.model;

public class TransactionInput {
	public String transactionOutputId;
	public TransactionOutput UTXO;

	public TransactionInput(String transactionOutpitId) {
		this.transactionOutputId = transactionOutpitId;
	}
}
