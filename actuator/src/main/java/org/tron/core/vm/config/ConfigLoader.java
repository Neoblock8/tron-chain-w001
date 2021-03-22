package org.tron.core.vm.config;


import static org.tron.core.capsule.ReceiptCapsule.checkForEnergyLimit;

import lombok.extern.slf4j.Slf4j;
import org.tron.common.parameter.CommonParameter;
import org.tron.core.store.DynamicPropertiesStore;
import org.tron.core.store.StoreFactory;

@Slf4j(topic = "VMConfigLoader")
public class ConfigLoader {

  //only for unit test
  public static boolean disable = false;

  public static void load(StoreFactory storeFactory) {
    if (!disable) {
      DynamicPropertiesStore ds = storeFactory.getChainBaseManager().getDynamicPropertiesStore();
      VMConfig.setVmTrace(CommonParameter.getInstance().isVmTrace());
      if (ds != null) {
        VMConfig.initVmHardFork(checkForEnergyLimit(ds));
        VMConfig.initAllowMultiSign(ds.getAllowMultiSign());
        VMConfig.initAllowWvmTransferTrc10(ds.getAllowWvmTransferTrc10());
        VMConfig.initAllowWvmConstantinople(ds.getAllowWvmConstantinople());
        VMConfig.initAllowWvmSolidity059(ds.getAllowWvmSolidity059());
        VMConfig.initAllowShieldedTRC20Transaction(ds.getAllowShieldedTRC20Transaction());
        VMConfig.initAllowWvmIstanbul(ds.getAllowWvmIstanbul());
        VMConfig.initAllowWvmStake(ds.getAllowWvmStake());
        VMConfig.initAllowWvmAssetIssue(ds.getAllowWvmAssetIssue());
      }
    }
  }
}
