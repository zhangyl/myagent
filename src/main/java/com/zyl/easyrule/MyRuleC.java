package com.zyl.easyrule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Rule;
import org.jeasy.rules.api.Facts;

@Rule(name = "myRuleC", description = "my ruleC description", priority = 1)
public class MyRuleC {

    @Condition
    public boolean when(Facts fact) {
    	String name = this.getClass().getSimpleName();
//    	System.out.println(name + "执行when，返回true");
        return name.length()>1;
    }

    @Action(order = 1)
    public void then(Facts facts) throws Exception {
        System.out.println(this.getClass().getSimpleName() + "执行then zyl=" + facts.get("zyl"));
    }

}
