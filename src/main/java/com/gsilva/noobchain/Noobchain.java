package com.gsilva.noobchain;

import java.security.Security;
import java.util.ArrayList;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.gsilva.noobchain.model.Block;
import com.gsilva.noobchain.model.Transaction;
import com.gsilva.noobchain.model.Wallet;
import com.gsilva.noobchain.util.StringUtil;

public class Noobchain {

	public static ArrayList<Block> blockchain = new ArrayList<Block>();
	public static int difficulty = 5;
	public static Wallet walletA;
	public static Wallet walletB;

	public static void main(String[] args) {
		Security.addProvider(new BouncyCastleProvider());
		walletA = new Wallet();
		walletB = new Wallet();
		
		System.out.println("Private and public keys:");
		System.out.println(StringUtil.getStringFromKey(walletA.privateKey));
		System.out.println(StringUtil.getStringFromKey(walletA.publicKey));

		Transaction transaction = new Transaction(walletA.publicKey, walletB.publicKey, 5, null);
		transaction.generateSignature(walletA.privateKey);
		
		System.out.println("Is signature verified");
		System.out.println(transaction.verifySignature());
	}

	public static Boolean isChainValid() {
		Block currentBlock;
		Block previousBlock;

		for (int i = 0; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i - 1);

			if (!currentBlock.hash.equals(currentBlock.calculateHash())) {
				System.out.println("Current hashes not equal");
				return false;
			}

			if (!previousBlock.hash.equals(currentBlock.previousHash)) {
				System.out.println("Previous hashes not equal");
				return false;
			}
		}
		return true;
	}
}
