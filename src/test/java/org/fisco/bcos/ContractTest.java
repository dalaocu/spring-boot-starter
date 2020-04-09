package org.fisco.bcos;

import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractTest extends BaseTest {

    @Autowired
    private Web3j web3j;
    @Autowired
    private Credentials credentials;

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        // deploy contract
        HelloWorld helloWorld = HelloWorld
                .deploy(web3j, credentials, new StaticGasProvider(GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT))
                .send();
        Assert.assertNotNull(helloWorld);
        // call set function
        TransactionReceipt tr = helloWorld.set("Hello, World!").send();
        Assert.assertEquals(tr.getMessage(), "0x0", tr.getStatus());
        // call get function
        String result = helloWorld.get().send();
        Assert.assertTrue("Hello, World!".equals(result));
    }
}
