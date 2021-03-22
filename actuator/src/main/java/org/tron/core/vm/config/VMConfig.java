/*
 * Copyright (c) [2016] [ <ether.camp> ]
 * This file is part of the ethereumJ library.
 *
 * The ethereumJ library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The ethereumJ library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with the ethereumJ library. If not, see <http://www.gnu.org/licenses/>.
 */
package org.tron.core.vm.config;


import static org.tron.common.parameter.CommonParameter.ENERGY_LIMIT_HARD_FORK;

import lombok.Setter;

/**
 * For developer only
 */
public class VMConfig {

  //1000 WYZ
  //public static final int MAX_FEE_LIMIT = 1_000_000_000;

  private static boolean vmTraceCompressed = false;

  @Setter
  private static boolean vmTrace = false;

  private static boolean ALLOW_WVM_TRANSFER_TRC10 = false;

  private static boolean ALLOW_WVM_CONSTANTINOPLE = false;

  private static boolean ALLOW_MULTI_SIGN = false;

  private static boolean ALLOW_WVM_SOLIDITY_059 = false;

  private static boolean ALLOW_SHIELDED_TRC20_TRANSACTION = false;

  private static boolean ALLOW_WVM_ISTANBUL = false;

  private static boolean ALLOW_WVM_STAKE = false;

  private static boolean ALLOW_WVM_ASSET_ISSUE = false;

  private VMConfig() {
  }

  public static VMConfig getInstance() {
    return SystemPropertiesInstance.INSTANCE;
  }

  public static boolean vmTrace() {
    return vmTrace;
  }

  public static boolean vmTraceCompressed() {
    return vmTraceCompressed;
  }

  public static void initVmHardFork(boolean pass) {
    ENERGY_LIMIT_HARD_FORK = pass;
  }

  public static void initAllowMultiSign(long allow) {
    ALLOW_MULTI_SIGN = allow == 1;
  }

  public static void initAllowWvmTransferTrc10(long allow) {
    ALLOW_WVM_TRANSFER_TRC10 = allow == 1;
  }

  public static void initAllowWvmConstantinople(long allow) {
    ALLOW_WVM_CONSTANTINOPLE = allow == 1;
  }

  public static void initAllowWvmSolidity059(long allow) {
    ALLOW_WVM_SOLIDITY_059 = allow == 1;
  }

  public static void initAllowShieldedTRC20Transaction(long allow) {
    ALLOW_SHIELDED_TRC20_TRANSACTION = allow == 1;
  }

  public static void initAllowWvmIstanbul(long allow) {
    ALLOW_WVM_ISTANBUL = allow == 1;
  }

  public static void initAllowWvmStake(long allow) {
    ALLOW_WVM_STAKE = allow == 1;
  }

  public static void initAllowWvmAssetIssue(long allow) {
    ALLOW_WVM_ASSET_ISSUE = allow == 1;
  }

  public static boolean getEnergyLimitHardFork() {
    return ENERGY_LIMIT_HARD_FORK;
  }

  public static boolean allowWvmTransferTrc10() {
    return ALLOW_WVM_TRANSFER_TRC10;
  }

  public static boolean allowWvmConstantinople() {
    return ALLOW_WVM_CONSTANTINOPLE;
  }

  public static boolean allowMultiSign() {
    return ALLOW_MULTI_SIGN;
  }

  public static boolean allowWvmSolidity059() {
    return ALLOW_WVM_SOLIDITY_059;
  }

  public static boolean allowShieldedTRC20Transaction() {
    return ALLOW_SHIELDED_TRC20_TRANSACTION;
  }

  public static boolean allowWvmIstanbul() {return ALLOW_WVM_ISTANBUL; }

  public static boolean allowWvmStake() {
    return ALLOW_WVM_STAKE;
  }

  public static boolean allowWvmAssetIssue() {
    return ALLOW_WVM_ASSET_ISSUE;
  }

  private static class SystemPropertiesInstance {

    private static final VMConfig INSTANCE = new VMConfig();
  }
}
