package com.gsilva.noobchain.model;

public class TransactionInput {
	public String transactionOutpitId;
	public TransactionOutput UTXO;

	public TransactionInput(String transactionOutpitId) {
		this.transactionOutpitId = transactionOutpitId;
	}
}
