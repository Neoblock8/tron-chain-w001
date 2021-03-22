package org.tron.common.runtime;

import lombok.extern.slf4j.Slf4j;
import org.tron.core.capsule.ReceiptCapsule;

@Slf4j
public class WVMTestResult {

  private Runtime runtime;
  private ReceiptCapsule receipt;
  private byte[] contractAddress;

  public WVMTestResult(Runtime runtime, ReceiptCapsule receipt, byte[] contractAddress) {
    this.runtime = runtime;
    this.receipt = receipt;
    this.contractAddress = contractAddress;
  }

  public byte[] getContractAddress() {
    return contractAddress;
  }

  public WVMTestResult setContractAddress(byte[] contractAddress) {
    this.contractAddress = contractAddress;
    return this;
  }

  public Runtime getRuntime() {
    return runtime;
  }

  public WVMTestResult setRuntime(Runtime runtime) {
    this.runtime = runtime;
    return this;
  }

  public ReceiptCapsule getReceipt() {
    return receipt;
  }

  public WVMTestResult setReceipt(ReceiptCapsule receipt) {
    this.receipt = receipt;
    return this;
  }

}
